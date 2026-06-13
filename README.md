# RetroMart

RetroMart is a full-stack, retro-themed e-commerce marketplace where buyers and sellers trade vintage items. It pairs a Spring Boot REST API with a server-rendered storefront built from plain HTML, CSS, and JavaScript, and integrates Razorpay for UPI, card, and netbanking payments.

The entire application (API and frontend) is served by a single Spring Boot process on port 8080.

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Spring Boot 3.4 (Java 17), Spring MVC |
| Security | Spring Security + Google OAuth 2.0, HTTP session auth |
| Database | MySQL 8 accessed through raw JDBC DAOs |
| Payments | Razorpay Java SDK 1.4.4 |
| Frontend | Vanilla HTML / CSS / JavaScript (no framework, no `package.json`) |
| Build | Apache Maven |

There is no Node toolchain. All frontend assets are static files under `src/main/resources/static/` and are served directly by Spring Boot.

## Architecture

```
Browser (static HTML/CSS/JS)
        |  fetch() calls
        v
Spring MVC REST controllers  (/api/**)
        |
        v
DAO layer (plain JDBC)
        |
        v
MySQL (retrodb)
```

Uploaded product images are written to a local `uploads/` directory and served from the `/uploads/**` URL path via a custom resource handler.

## Features

- Storefront homepage with hero, deals, and featured products; shared header, category navigation, and footer across every page.
- Buyer and seller roles with email/password login and optional Google OAuth 2.0.
- Product management: sellers can add and delete their own products; everyone can browse.
- Browse by category (Electronics, Fashion, Home, Books, Sports) and case-insensitive keyword search.
- Image uploads accepting JPEG, PNG, GIF, WEBP, AVIF, and HEIC, up to 10 MB per file.
- Razorpay checkout with dedicated flows for UPI, cards, and netbanking/wallets.
- Downloadable receipt after successful payment.
- Five-step order tracker: Ordered, Confirmed, Packed, Shipped, Delivered.
- Profile page with personal details, order history, and a seller's product listings.
- Admin dashboard listing users, products, and orders.

## Prerequisites

- Java 17 or newer
- Maven 3.8 or newer
- MySQL 8.0 or newer
- A Razorpay account (test mode) if you want live payment flows

## Local Setup

### 1. Create the database

The JDBC credentials are hardcoded in `src/main/java/com/thinking/machines/retro/utility/RetroConnection.java` as user `retrouser` / password `retrouser` against database `retrodb`. Either match those values or update that file.

```sql
CREATE DATABASE retrodb;

CREATE USER 'retrouser'@'localhost' IDENTIFIED BY 'retrouser';
GRANT ALL PRIVILEGES ON retrodb.* TO 'retrouser'@'localhost';
FLUSH PRIVILEGES;

USE retrodb;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    password VARCHAR(255) NOT NULL,
    user_type ENUM('buyer', 'seller') NOT NULL
);

CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    seller_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(50) DEFAULT 'electronics',
    FOREIGN KEY (seller_id) REFERENCES users(user_id)
);

CREATE TABLE product_images (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    buyer_id INT NOT NULL,
    product_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(50) DEFAULT 'pending',
    delivery_status VARCHAR(50) DEFAULT 'processing',
    shipping_address TEXT,
    contact_phone VARCHAR(15),
    payment_mode VARCHAR(50),
    transaction_id VARCHAR(100),
    FOREIGN KEY (buyer_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```

### 2. Configure application properties

Edit `src/main/resources/application.properties`. The relevant keys are:

```properties
# File upload limits
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=60MB

# Razorpay
razorpay.demo-mode=false
razorpay.key.id=YOUR_RAZORPAY_KEY_ID
razorpay.key.secret=YOUR_RAZORPAY_KEY_SECRET

# Google OAuth (optional)
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
```

Do not commit real secrets. See the Security Notes section for how to externalize them.

### 3. Build and run

```bash
git clone https://github.com/manishrathore77/retromart.git
cd retromart

mvn clean install
mvn spring-boot:run
```

The application starts on http://localhost:8080/. The homepage is `index.html`.

### Test accounts

| Role | Email | Password |
|------|-------|----------|
| Buyer | buyer@retro.com | buyer123 |
| Seller | seller@retro.com | seller123 |

These exist only if you seed them; register new accounts at `/register.html` otherwise.

## Testing Razorpay Locally

Payments are handled by Razorpay Checkout on the frontend and by `RazorpayOrderController` on the backend. There are two ways to exercise the payment flow locally.

### Option A: Demo mode (no Razorpay account needed)

Set the following in `application.properties`:

```properties
razorpay.demo-mode=true
```

In demo mode the backend skips Razorpay entirely and returns a fake order id. The payment page shows a confirmation dialog instead of the Razorpay widget and then marks the order as paid. This is useful for testing the end-to-end order/receipt flow without any keys.

### Option B: Real Razorpay test mode

1. Create a free account at https://dashboard.razorpay.com/ and switch the dashboard to Test Mode.
2. Go to Settings, then API Keys, and generate a test key pair. Test keys are prefixed with `rzp_test_`.
3. Put them in `application.properties` and disable demo mode:

   ```properties
   razorpay.demo-mode=false
   razorpay.key.id=rzp_test_xxxxxxxxxxxx
   razorpay.key.secret=xxxxxxxxxxxxxxxxxxxxxxxx
   ```

4. To use the UPI option, enable UPI under Settings, then Payment Methods in the Razorpay dashboard. Card and netbanking are enabled by default in test mode.
5. Restart the app after changing keys, since they are read at startup.

Test credentials accepted by Razorpay test mode:

- Card: `4111 1111 1111 1111`, any future expiry, any CVV, any name. Use OTP `1234` if prompted.
- UPI success: `success@razorpay`
- UPI failure: `failure@razorpay`

How the flow works in code:

1. `payment.html` posts the amount to `POST /api/payment/razorpay-order`.
2. `RazorpayOrderController` creates a Razorpay order and returns the order id plus the public key.
3. The frontend opens Razorpay Checkout, restricting the visible methods based on the chosen button (UPI only, card only, or all methods) using Razorpay display blocks.
4. On success the handler calls `PUT /api/orders/{id}/confirm` with the transaction id, payment status, and payment mode.
5. The order is marked paid, the payment mode is stored, delivery status moves to processing, and the buyer is redirected to the receipt page.

## Project Structure

```
retromart/
  pom.xml                         Maven config and dependencies
  README.md
  uploads/                        Uploaded product images (served at /uploads/**)
  src/main/
    java/com/thinking/machines/retro/
      RetroApplication.java       Spring Boot entry point
      config/
        SecurityConfig.java       Spring Security + permitted static paths + OAuth
        WebConfig.java            Static resource handler for /uploads/**
      controller/
        UserController.java       Auth, session, restore-session, user CRUD
        ProductController.java    Product CRUD, search, category filter
        OrderController.java      Orders, payment confirm, status updates
        ImageUploadController.java Image upload and validation
        ProductImageController.java Product image CRUD
        RazorpayOrderController.java Razorpay order creation
        DashboardController.java  Admin dashboard data
      dao/                        Plain JDBC data access objects
        UserDAO.java
        ProductDAO.java
        OrderDAO.java
        ProductImageDAO.java
      modal/                      Domain models (User, Product, Order, ProductImage)
      utility/
        RetroConnection.java      JDBC connection factory
    resources/
      application.properties      Razorpay, OAuth, upload limits
      static/
        index.html                Storefront homepage
        login.html, register.html
        product-list.html         Grid with search and category filter
        product-details.html      Product view and Buy Now
        add-product.html          Seller add-product form
        place-order.html          Order form
        payment.html              UPI / card / netbanking checkout
        receipt.html              Downloadable receipt
        my-orders.html            Order history with tracker
        profile.html              Profile, orders, seller products
        admin-dashboard.html      Admin panel
        css/main.css              Shared design system
        js/common.js              Shared header/footer/search/tracker/auth helpers
```

## API Reference

### Users

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/users/login` | Log in and create a session |
| POST | `/api/users/logout` | Invalidate the session |
| GET | `/api/users/session` | Get the current session user |
| POST | `/api/users/restore-session` | Re-create a server session from client state |
| GET | `/api/users` | List all users |
| GET | `/api/users/{id}` | Get a user by id |
| PUT | `/api/users/{id}` | Update a user |
| DELETE | `/api/users/{id}` | Delete a user |

### Products

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/products` | Add a product (seller only) |
| GET | `/api/products` | List products, optionally `?category=` |
| GET | `/api/products/search?keyword=` | Keyword search |
| GET | `/api/products/seller/{sellerId}` | List a seller's products |
| GET | `/api/products/{id}` | Get a product by id |
| DELETE | `/api/products/{id}` | Delete own product (seller only) |

### Images

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/upload/{productId}` | Upload an image for a product |
| GET | `/api/images/product/{productId}` | List images for a product |
| DELETE | `/api/images/{id}` | Delete one image |
| DELETE | `/api/images/product/{productId}` | Delete all images for a product |

### Orders

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders` | Place a new order |
| GET | `/api/orders` | List all orders |
| GET | `/api/orders/{id}` | Get an order by id |
| GET | `/api/orders/buyer/{buyerId}` | List a buyer's orders |
| PUT | `/api/orders/{id}/confirm` | Confirm payment (txn id, status, payment mode) |
| PUT | `/api/orders/payment/{id}` | Update payment status |
| PUT | `/api/orders/delivery/{id}` | Update delivery status |

### Payment

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/payment/razorpay-order` | Create a Razorpay order and return id + public key |

## Deployment

The build produces a self-contained executable JAR. A typical production rollout looks like this.

### 1. Build the JAR

```bash
mvn clean package
```

The artifact is written to `target/retro-0.0.1-SNAPSHOT.jar`.

### 2. Externalize configuration

Avoid baking secrets and database credentials into the image. The database connection in `RetroConnection.java` reads from environment variables, falling back to the local defaults (`localhost:3306`, `retrodb`, `retrouser`) only when those variables are not set. The supported variables are:

| Variable | Purpose | Default |
|----------|---------|---------|
| `DB_HOST` or `MYSQLHOST` | Database host | `localhost` |
| `DB_PORT` or `MYSQLPORT` | Database port | `3306` |
| `DB_NAME` or `MYSQLDATABASE` | Database name | `retrodb` |
| `DB_USER` or `MYSQLUSER` | Database user | `retrouser` |
| `DB_PASSWORD` or `MYSQLPASSWORD` | Database password | `retrouser` |
| `JDBC_URL` | Full JDBC URL (overrides all of the above) | unset |

The `MYSQL*` names match the variables that Railway's MySQL plugin injects automatically, so a Railway deployment with a linked MySQL service needs no manual database configuration. Razorpay keys are still supplied as Spring properties:

```bash
java -jar target/retro-0.0.1-SNAPSHOT.jar \
  --razorpay.key.id="$RAZORPAY_KEY_ID" \
  --razorpay.key.secret="$RAZORPAY_KEY_SECRET" \
  --razorpay.demo-mode=false \
  --server.port=8080
```

### Deploying to Railway (backend)

1. Create a new Railway project and deploy this repository. Railway detects the Maven build and runs the resulting JAR.
2. Add a MySQL database to the same project. Railway injects `MYSQLHOST`, `MYSQLPORT`, `MYSQLDATABASE`, `MYSQLUSER`, and `MYSQLPASSWORD`, which the app reads automatically.
3. Create the schema (the tables under Local Setup) in the Railway MySQL instance, using Railway's database console or any MySQL client pointed at the public connection details.
4. Add `RAZORPAY_KEY_ID`, `RAZORPAY_KEY_SECRET`, and set `razorpay.demo-mode` as needed via service variables.
5. Redeploy. The API endpoints under `/api/**` should now return data instead of HTTP 500.

A 500 from every `/api/**` endpoint while the homepage loads is the classic symptom of the app failing to reach the database, usually because the MySQL service is missing, the schema was never created, or the connection variables are not set.

### 3. Run as a service

Run the JAR behind a process manager (systemd, Docker, or a platform such as Render, Railway, or a cloud VM). Ensure the working directory is writable so the `uploads/` folder can be created, or mount a persistent volume for it, since uploaded images are stored on disk rather than in the database.

A minimal Dockerfile:

```dockerfile
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/retro-0.0.1-SNAPSHOT.jar app.jar
VOLUME ["/app/uploads"]
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 4. Production checklist

- Point the application at a managed MySQL instance and create the schema shown above.
- Set strong, unique Razorpay live keys via environment variables, not in source control.
- Put the app behind HTTPS (a reverse proxy such as Nginx, or the platform's TLS termination). Razorpay live mode and OAuth redirect URIs require HTTPS.
- Update the Google OAuth redirect URI to your production domain.
- Mount or back up the `uploads/` directory.
- Replace plaintext password storage with hashing (for example BCrypt) before handling real users.

## Deploying the Frontend to Vercel

Vercel cannot run the Spring Boot backend. It is a serverless/static platform with no Java runtime, no always-on server, no bundled MySQL, and no persistent writable disk for uploads. The backend must therefore be hosted on a Java-friendly platform (Railway, Render, Fly.io, or any VM/container host) as described above.

What you can do on Vercel is host the static frontend and proxy API and image requests to your separately-hosted backend. The included `vercel.json` does exactly that, so the browser still sees a single origin and session cookies and relative paths keep working without any frontend code changes.

Steps:

1. Deploy the Spring Boot app somewhere with a public HTTPS URL (see Deployment above). Note that URL, for example `https://retromart-api.onrender.com`.
2. Edit `vercel.json` and replace `https://your-backend-host.example.com` in both rewrite rules with your backend URL.

   ```json
   {
     "outputDirectory": "src/main/resources/static",
     "rewrites": [
       { "source": "/api/:path*", "destination": "https://retromart-api.onrender.com/api/:path*" },
       { "source": "/uploads/:path*", "destination": "https://retromart-api.onrender.com/uploads/:path*" }
     ]
   }
   ```

3. Import the repository in the Vercel dashboard, or deploy with the CLI:

   ```bash
   npm i -g vercel
   vercel        # preview deploy
   vercel --prod # production deploy
   ```

   Vercel serves the files in `src/main/resources/static` directly. `/` resolves to `index.html`; all `/api/*` and `/uploads/*` requests are transparently proxied to the backend.

Notes and caveats:

- The backend must allow the Razorpay and OAuth flows over HTTPS, and its Google OAuth redirect URI must point at your Vercel domain if you use OAuth.
- Because requests are proxied through the same origin, no CORS configuration is needed. If you instead call the backend directly from the browser (without the proxy), you must enable CORS and configure cross-site session cookies (`SameSite=None; Secure`).
- Uploaded images are stored on the backend's disk, so persist that directory on the backend host; Vercel does not store them.

## Security Notes

- Passwords are currently stored and compared in plaintext. This is acceptable for a learning project but must be replaced with a password hashing scheme before any real deployment.
- CSRF protection is disabled in `SecurityConfig` to simplify the API. Re-enable and configure it if you expose this beyond a trusted environment.
- Do not commit Razorpay or Google OAuth secrets. Move them to environment variables or an untracked properties file, and rotate any key that has been committed.
- The JDBC credentials default to `retrouser`/`retrouser` against `localhost` when no environment variables are set. Always supply real credentials through the environment variables listed above in shared or production environments, and use a strong database password.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

Built by [Manish Rathore](https://github.com/manishrathore77).
