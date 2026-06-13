<div align="center">

# 📼 RetroMart 🕹️

### *The Radical Marketplace for All Things Retro!*

> *"Where every pixel has a story and every product has a vibe"* 🌈✨

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Razorpay](https://img.shields.io/badge/Razorpay-UPI_&_Cards-3395FF?style=for-the-badge&logo=razorpay&logoColor=white)](https://razorpay.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)

---

```
 ╔══════════════════════════════════════════════════════╗
 ║  ██████╗ ███████╗████████╗██████╗  ██████╗          ║
 ║  ██╔══██╗██╔════╝╚══██╔══╝██╔══██╗██╔═══██╗        ║
 ║  ██████╔╝█████╗     ██║   ██████╔╝██║   ██║        ║
 ║  ██╔══██╗██╔══╝     ██║   ██╔══██╗██║   ██║        ║
 ║  ██║  ██║███████╗   ██║   ██║  ██║╚██████╔╝        ║
 ║  ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝        ║
 ║                                                      ║
 ║  ███╗   ███╗ █████╗ ██████╗ ████████╗               ║
 ║  ████╗ ████║██╔══██╗██╔══██╗╚══██╔══╝               ║
 ║  ██╔████╔██║███████║██████╔╝   ██║                  ║
 ║  ██║╚██╔╝██║██╔══██║██╔══██╗   ██║                  ║
 ║  ██║ ╚═╝ ██║██║  ██║██║  ██║   ██║                  ║
 ║  ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝                  ║
 ╚══════════════════════════════════════════════════════╝
```

*🎵 Insert your favorite 80s synth track here 🎵*

</div>

---

## 🌟 What is RetroMart?

**RetroMart** is a full-stack retro-themed online marketplace where **buyers** and **sellers** come together to trade vintage treasures! 📼💾🎮 Browse a modern, Flipkart/Amazon-style storefront, search by keyword or category, place an order, and pay securely via **UPI**, **Cards**, or **Netbanking** through Razorpay.

Built with **Spring Boot** + **MySQL** on the backend and a hand-rolled **vanilla HTML/CSS/JS** frontend (no framework, no `package.json`), all served from a single Spring Boot app on **port 8080**.

---

## 🕹️ Features

| Feature | Description |
|---------|-------------|
| 🏬 **Modern Storefront** | Homepage with hero, deals, and featured products + sticky header, category nav, and footer on every page |
| 🔐 **Dual Auth System** | Email/password login + Google OAuth 2.0, backed by HTTP sessions |
| 🛍️ **Buyer & Seller Roles** | Register as a buyer or seller with role-based access |
| 📦 **Product Management** | Sellers can add, view, and delete their own products |
| 🗂️ **Categories** | Browse by Electronics, Fashion, Home, Books, Sports |
| 🔍 **Keyword Search** | Case-insensitive search across all listings |
| 🖼️ **Image Uploads** | Upload product images (JPEG/PNG/WEBP/AVIF/HEIC, up to 10MB) |
| 💳 **UPI / Card / Netbanking** | Razorpay checkout with dedicated UPI, Card, and All-methods flows |
| 🧾 **Receipts** | Downloadable order receipt after successful payment |
| 📡 **Order Tracking** | 5-step tracker: Ordered → Confirmed → Packed → Shipped → Delivered |
| 👤 **Profile Page** | Personal details, editable info, order history, and seller's products |
| 📊 **Admin Dashboard** | View all users, products, and orders at a glance |

---

## 🛠️ Tech Stack

<div align="center">

```
┌─────────────────────────────────────────────┐
│           🏗️ ARCHITECTURE                   │
├─────────────────────────────────────────────┤
│                                             │
│   ┌───────────┐    ┌──────────────┐        │
│   │  Frontend  │◄──►│   REST API   │        │
│   │  (HTML/JS) │    │ (Spring MVC) │        │
│   └───────────┘    └──────┬───────┘        │
│                           │                 │
│                    ┌──────▼───────┐         │
│                    │   DAO Layer  │         │
│                    │  (JDBC/SQL)  │         │
│                    └──────┬───────┘         │
│                           │                 │
│                    ┌──────▼───────┐         │
│                    │    MySQL     │         │
│                    │   Database   │         │
│                    └─────────────┘         │
│                                             │
└─────────────────────────────────────────────┘
```

</div>

| Layer | Technology | Version |
|-------|-----------|---------|
| ☕ **Backend** | Spring Boot | 3.4.x |
| 🗄️ **Database** | MySQL (raw JDBC DAOs) | 8.0+ |
| 🔐 **Auth** | Spring Security + OAuth 2.0 | Latest |
| 💳 **Payments** | Razorpay Java SDK | 1.4.4 |
| 🎨 **Frontend** | Vanilla HTML/CSS/JS (shared `main.css` + `common.js`) | - |
| 🏗️ **Build** | Apache Maven | 3.8+ |
| ☁️ **Runtime** | Java | 17 |

---

## 🚀 Getting Started

### Prerequisites

```
✅ Java 17 (or higher)
✅ Maven 3.8+
✅ MySQL 8.0+
✅ Git
```

### 🗃️ Database Setup

```sql
-- Create the database
CREATE DATABASE retrodb;

-- Create the user (credentials match utility/RetroConnection.java)
CREATE USER 'retrouser'@'localhost' IDENTIFIED BY 'retrouser';
GRANT ALL PRIVILEGES ON retrodb.* TO 'retrouser'@'localhost';
FLUSH PRIVILEGES;

USE retrodb;

-- 👤 Users
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    password VARCHAR(255) NOT NULL,
    user_type ENUM('buyer', 'seller') NOT NULL
);

-- 📦 Products (note the category column)
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    seller_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(50) DEFAULT 'electronics',
    FOREIGN KEY (seller_id) REFERENCES users(user_id)
);

-- 🖼️ Product images
CREATE TABLE product_images (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- 🛒 Orders (payment + delivery + Razorpay fields)
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

### ⚙️ Configure Payments & OAuth

Edit `src/main/resources/application.properties` and add your own keys:

```properties
# Razorpay — get test keys from https://dashboard.razorpay.com/
razorpay.demo-mode=false
razorpay.key.id=YOUR_RAZORPAY_KEY_ID
razorpay.key.secret=YOUR_RAZORPAY_KEY_SECRET

# Google OAuth (optional)
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
```

> ⚠️ **Security note:** Do not commit real secrets to a public repo. Set `razorpay.demo-mode=true` to test the checkout flow without real keys. To enable UPI, make sure UPI is enabled on your Razorpay dashboard.

### 🏃‍♂️ Run Locally

```bash
# 1. Clone this radical repo
git clone https://github.com/manishrathore77/retromart.git
cd retromart

# 2. Build the project
mvn clean install

# 3. Run the application (serves both the API and the frontend)
mvn spring-boot:run

# 4. Open your browser and VIBE
# 🌐 http://localhost:8080/
```

The app serves the static frontend and REST API together on **port 8080**. The homepage is `index.html`; uploaded images are served from the `./uploads/` directory.

### 🔑 Test Accounts

| Role | Email | Password |
|------|-------|----------|
| 🛒 Buyer | `buyer@retro.com` | `buyer123` |
| 🏪 Seller | `seller@retro.com` | `seller123` |

**Razorpay test card:** `4111 1111 1111 1111` · **Test UPI ID:** `success@razorpay`

---

## 📁 Project Structure

```
retromart/
├── 📄 pom.xml                          # Maven config + dependencies
├── 📄 README.md                        # You are here! 📍
│
├── 📂 src/main/
│   ├── 📂 java/com/thinking/machines/retro/
│   │   ├── 📄 RetroApplication.java            # 🚀 Main entry point
│   │   ├── 📂 config/
│   │   │   ├── 📄 SecurityConfig.java          # 🔐 Spring Security + permitted static assets
│   │   │   └── 📄 WebConfig.java               # 🌐 Static resource / upload handler
│   │   ├── 📂 controller/
│   │   │   ├── 📄 UserController.java           # 👤 Auth, session, restore-session
│   │   │   ├── 📄 ProductController.java        # 📦 Product CRUD + search + category
│   │   │   ├── 📄 OrderController.java          # 🛒 Orders, payment confirm, tracking
│   │   │   ├── 📄 ImageUploadController.java    # 🖼️ Image uploads (incl. AVIF/HEIC)
│   │   │   ├── 📄 ProductImageController.java   # 📸 Image CRUD
│   │   │   ├── 📄 RazorpayOrderController.java  # 💳 Razorpay order creation
│   │   │   └── 📄 DashboardController.java      # 📊 Admin dashboard data
│   │   ├── 📂 dao/
│   │   │   ├── 📄 UserDAO.java                  # 👤 User queries
│   │   │   ├── 📄 ProductDAO.java               # 📦 Product + search + category queries
│   │   │   ├── 📄 OrderDAO.java                 # 🛒 Order + payment_mode queries
│   │   │   └── 📄 ProductImageDAO.java          # 🖼️ Image queries
│   │   ├── 📂 modal/
│   │   │   ├── 📄 User.java
│   │   │   ├── 📄 Product.java                  # includes category
│   │   │   ├── 📄 Order.java                    # includes paymentMode
│   │   │   └── 📄 ProductImage.java
│   │   └── 📂 utility/
│   │       └── 📄 RetroConnection.java          # 🗄️ JDBC connection factory
│   └── 📂 resources/
│       ├── 📄 application.properties            # ⚙️ Razorpay, OAuth, upload limits
│       └── 📂 static/
│           ├── 📄 index.html                    # 🏬 Homepage / storefront
│           ├── 📄 login.html                    # 🔑 Login
│           ├── 📄 register.html                 # 📝 Registration
│           ├── 📄 product-list.html             # 🛍️ Product grid + search/category
│           ├── 📄 product-details.html          # 🔍 Product view + Buy Now
│           ├── 📄 add-product.html              # ➕ Add product (seller)
│           ├── 📄 place-order.html              # 🛒 Order form
│           ├── 📄 payment.html                  # 💳 UPI / Card / Netbanking checkout
│           ├── 📄 receipt.html                  # 🧾 Downloadable receipt
│           ├── 📄 my-orders.html                # 📋 Order history + tracker
│           ├── 📄 profile.html                  # 👤 Profile, orders, seller products
│           ├── 📄 admin-dashboard.html          # 📊 Admin panel
│           ├── 📂 css/  └── main.css            # 🎨 Shared design system
│           └── 📂 js/   └── common.js           # 🧩 Header/footer/search/tracker/auth
│
└── 📂 uploads/                          # 🖼️ Uploaded product images (served at /uploads/**)
```

---

## 🗺️ API Endpoints

### 👤 Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/users/register` | Register new user |
| `POST` | `/api/users/login` | Login (stores user in session) |
| `POST` | `/api/users/logout` | Logout (invalidate session) |
| `GET` | `/api/users/session` | Get current session user |
| `POST` | `/api/users/restore-session` | Re-hydrate server session from localStorage |
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/{id}` | Get user by ID |
| `PUT` | `/api/users/{id}` | Update user |
| `DELETE` | `/api/users/{id}` | Delete user |

### 📦 Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/products` | Add product (seller only) |
| `GET` | `/api/products` | Get all products (or `?category=` to filter) |
| `GET` | `/api/products/search?keyword=` | Keyword search |
| `GET` | `/api/products/seller/{sellerId}` | Get products by seller |
| `GET` | `/api/products/{id}` | Get product by ID |
| `DELETE` | `/api/products/{id}` | Delete own product (seller only) |

### 🖼️ Images
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/upload/{productId}` | Upload image for product |
| `GET` | `/api/images/product/{productId}` | Get images by product |
| `DELETE` | `/api/images/{id}` | Delete specific image |
| `DELETE` | `/api/images/product/{productId}` | Delete all product images |

### 🛒 Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/orders` | Place new order |
| `GET` | `/api/orders` | Get all orders |
| `GET` | `/api/orders/{id}` | Get order by ID |
| `GET` | `/api/orders/buyer/{buyerId}` | Get orders by buyer |
| `PUT` | `/api/orders/{id}/confirm` | Confirm payment (txn id, status, payment mode) |
| `PUT` | `/api/orders/payment/{id}` | Update payment status |
| `PUT` | `/api/orders/delivery/{id}` | Update delivery status |

### 💳 Payment
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/payment/razorpay-order` | Create a Razorpay order (returns order id + key) |

---

## 💳 Payment Flow

1. Buyer clicks **Buy Now** → **Place Order** → redirected to **Payment**.
2. Buyer picks a method: **Pay with UPI**, **Credit/Debit Card**, or **Netbanking/Wallets**.
3. Frontend calls `/api/payment/razorpay-order`, then opens Razorpay Checkout with the matching method block.
4. On success, `/api/orders/{id}/confirm` saves the transaction id, sets payment status to **paid**, records the **payment mode**, and moves delivery status to **processing**.
5. Buyer lands on the **receipt** page and can download it.

---

## 🤝 Contributing

Contributions from fellow retro enthusiasts are welcome! Fork the repo, create a feature branch, and open a pull request.

---

## 📜 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">

### 🕹️ Built with ❤️ and excessive nostalgia by

**[Manish Rathore](https://github.com/manishrathore77)** 🚀

*Made with mass nostalgia overload in 2026* 💾✨

</div>
