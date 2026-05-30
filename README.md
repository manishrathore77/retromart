<div align="center">

# 📼 RetroMart 🕹️

### *The Radical Marketplace for All Things Retro!*

> *"Where every pixel has a story and every product has a vibe"* 🌈✨

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Razorpay](https://img.shields.io/badge/Razorpay-Integrated-3395FF?style=for-the-badge&logo=razorpay&logoColor=white)](https://razorpay.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)
[![Made with Love](https://img.shields.io/badge/Made_with-❤️-red?style=for-the-badge)](https://github.com/manishrathore77)

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

**RetroMart** is a full-stack retro-themed online marketplace where **buyers** and **sellers** come together to trade vintage treasures! 📼💾🎮 Think of it as a time machine for your shopping cart — a place where cassette tapes, vinyl records, vintage arcade machines, and neon signs find their forever homes.

Built with **Spring Boot** + **MySQL** and sprinkled with *maximum nostalgia*, this isn't just a project — it's a **love letter to the rad decades** that gave us everything cool. 🕺💃

---

## 🕹️ Features That Slap

| Feature | Description | Vibe Check |
|---------|-------------|------------|
| 🔐 **Dual Auth System** | Email/password login + Google OAuth 2.0 | 🔥🔥🔥 |
| 🛍️ **Buyer & Seller Roles** | Register as buyer or seller with role-based access | 💯 |
| 📦 **Product Management** | Full CRUD — add, view, edit, delete products | 🎯 |
| 🖼️ **Multi-Image Gallery** | Upload up to 5 images per product with carousel view | 📸✨ |
| 💳 **Razorpay Payments** | Secure online payments with real payment gateway | 💰🚀 |
| 📊 **Admin Dashboard** | View all users, products, and orders at a glance | 🧠 |
| 🛒 **Order Tracking** | Track payment status & delivery status in real-time | 📡 |
| 🔍 **Product Search** | Case-insensitive keyword search across all listings | 🔎 |
| 📱 **Responsive Design** | Clean UI that works on desktop and mobile | 📲 |
| 🔒 **Session Management** | Secure HTTP sessions with Spring Security | 🛡️ |

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
| 🗄️ **Database** | MySQL | 8.0+ |
| 🔐 **Auth** | Spring Security + OAuth 2.0 | Latest |
| 💳 **Payments** | Razorpay Java SDK | 1.4.4 |
| 🎨 **Frontend** | Vanilla HTML/CSS/JS | - |
| 🏗️ **Build** | Apache Maven | 3.8+ |
| ☁️ **Runtime** | Java | 17 |

---

## 🚀 Getting Started

### Prerequisites

Make sure you've got these bad boys installed:

```
✅ Java 17 (or higher)
✅ Maven 3.8+
✅ MySQL 8.0+
✅ Git
✅ A love for retro aesthetics 📼
```

### 🗃️ Database Setup

```sql
-- Create the database
CREATE DATABASE retrodb;

-- Create the user
CREATE USER 'retrouser'@'localhost' IDENTIFIED BY 'retrouser';
GRANT ALL PRIVILEGES ON retrodb.* TO 'retrouser'@'localhost';
FLUSH PRIVILEGES;

-- Switch to the database
USE retrodb;

-- 👤 Users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    password VARCHAR(255) NOT NULL,
    user_type ENUM('buyer', 'seller') NOT NULL
);

-- 📦 Products table
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    seller_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES users(user_id)
);

-- 🖼️ Product images table
CREATE TABLE product_images (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- 🛒 Orders table
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

### 🏃‍♂️ Run Locally

```bash
# 1. Clone this radical repo
git clone https://github.com/manishrathore77/retromart.git
cd retromart

# 2. Build the project
mvn clean install

# 3. Run the application
mvn spring-boot:run

# 4. Open your browser and VIBE
# 🌐 http://localhost:8080/login.html
```

### 🔑 Test Accounts

| Role | Email | Password |
|------|-------|----------|
| 🛒 Buyer | `buyer@retro.com` | `buyer123` |
| 🏪 Seller | `seller@retro.com` | `seller123` |

---

## 📁 Project Structure

```
retromart/
├── 📄 pom.xml                          # Maven config + dependencies
├── 📄 README.md                        # You are here! 📍
├── 📄 CONTRIBUTING.md                  # How to contribute
├── 📄 LICENSE                          # MIT License
│
├── 📂 .github/
│   ├── 📂 workflows/
│   │   └── 📄 ci.yml                  # GitHub Actions CI
│   ├── 📂 ISSUE_TEMPLATE/
│   │   ├── 📄 bug_report.md           # Bug report template
│   │   └── 📄 feature_request.md      # Feature request template
│   └── 📄 PULL_REQUEST_TEMPLATE.md    # PR template
│
├── 📂 src/
│   ├── 📂 main/
│   │   ├── 📂 java/com/thinking/machines/retro/
│   │   │   ├── 📄 RetroApplication.java        # 🚀 Main entry point
│   │   │   ├── 📂 config/
│   │   │   │   ├── 📄 SecurityConfig.java      # 🔐 Spring Security
│   │   │   │   └── 📄 WebConfig.java           # 🌐 Static resources
│   │   │   ├── 📂 controller/
│   │   │   │   ├── 📄 UserController.java      # 👤 User endpoints
│   │   │   │   ├── 📄 ProductController.java   # 📦 Product CRUD
│   │   │   │   ├── 📄 OrderController.java     # 🛒 Order management
│   │   │   │   ├── 📄 ImageUploadController.java  # 🖼️ Image uploads
│   │   │   │   ├── 📄 ProductImageController.java # 📸 Image CRUD
│   │   │   │   ├── 📄 RazorpayOrderController.java # 💳 Payments
│   │   │   │   └── 📄 DashboardController.java # 📊 Admin dashboard
│   │   │   ├── 📂 dao/
│   │   │   │   ├── 📄 UserDAO.java             # 👤 User queries
│   │   │   │   ├── 📄 ProductDAO.java          # 📦 Product queries
│   │   │   │   ├── 📄 OrderDAO.java            # 🛒 Order queries
│   │   │   │   └── 📄 ProductImageDAO.java     # 🖼️ Image queries
│   │   │   ├── 📂 modal/
│   │   │   │   ├── 📄 User.java                # 👤 User model
│   │   │   │   ├── 📄 Product.java             # 📦 Product model
│   │   │   │   ├── 📄 Order.java               # 🛒 Order model
│   │   │   │   └── 📄 ProductImage.java        # 🖼️ Image model
│   │   │   └── 📂 utility/
│   │   │       └── 📄 RetroConnection.java     # 🗄️ DB connection
│   │   └── 📂 resources/
│   │       ├── 📄 application.properties       # ⚙️ App config
│   │       └── 📂 static/
│   │           ├── 📄 login.html               # 🔑 Login page
│   │           ├── 📄 register.html            # 📝 Registration
│   │           ├── 📄 product-list.html        # 🛍️ Product grid
│   │           ├── 📄 product-details.html     # 🔍 Product view
│   │           ├── 📄 add-product.html         # ➕ Add product
│   │           ├── 📄 place-order.html         # 🛒 Order form
│   │           ├── 📄 payment.html             # 💳 Razorpay checkout
│   │           ├── 📄 my-orders.html           # 📋 Order history
│   │           └── 📄 admin-dashboard.html     # 📊 Admin panel
│   └── 📂 test/
│       └── 📂 java/.../retro/
│           └── 📄 RetroApplicationTests.java   # 🧪 Tests
│
└── 📂 uploads/                          # 🖼️ Uploaded product images
    └── 📄 .gitkeep
```

---

## 📸 Pages Preview

| Page | Description |
|------|-------------|
| 🔐 **Login** | Clean login form + Google OAuth sign-in button |
| 📝 **Register** | Sign up as buyer or seller with role selection |
| 🛍️ **Product List** | Beautiful grid of product cards with images and prices |
| 🔍 **Product Details** | Full product view with image carousel + Buy Now |
| ➕ **Add Product** | Sellers can add products with exactly 5 images |
| 🛒 **Place Order** | Order summary with address and phone input |
| 💳 **Payment** | Razorpay checkout integration |
| 📋 **My Orders** | Buyers can track all their orders |
| 📊 **Admin Dashboard** | View users, products, and orders count + tables |

---

## 🗺️ API Endpoints

### 👤 Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/users/register` | Register new user |
| `POST` | `/api/users/login` | Login with email/password |
| `POST` | `/api/users/logout` | Logout (invalidate session) |
| `GET` | `/api/users/session` | Get current session user |
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/{id}` | Get user by ID |
| `PUT` | `/api/users/{id}` | Update user |
| `DELETE` | `/api/users/{id}` | Delete user |

### 📦 Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/products` | Add new product (seller only) |
| `GET` | `/api/products` | Get all products |
| `GET` | `/api/products/{id}` | Get product by ID |
| `DELETE` | `/api/products/{id}` | Delete product |

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
| `GET` | `/api/orders/buyer/{buyerId}` | Get orders by buyer |
| `PUT` | `/api/orders/{id}/confirm` | Confirm payment |
| `PUT` | `/api/orders/payment/{id}` | Update payment status |
| `PUT` | `/api/orders/delivery/{id}` | Update delivery status |

### 💳 Payment
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/payment/razorpay-order` | Create Razorpay order |

---

## 📅 The Journey — Development Timeline

> *Every great product has a story. Here's ours.* 📖

```
📅 February 2026 — "The Genesis" 🌱
├── 💡 Project ideation & brainstorming
├── 🏗️ Spring Boot project initialization
├── 📝 Database schema design
├── 🔧 Maven configuration & dependencies
└── 🗄️ MySQL connection utility

📅 March 2026 — "Building the Foundation" 🧱
├── 👤 User model + DAO + registration/login
├── 📦 Product model + DAO + CRUD endpoints
├── 🎨 Frontend: login.html, register.html
├── 🛍️ Product listing page with dynamic grid
├── 🔍 Product details with image gallery
└── 📸 Multi-image upload system

📅 April 2026 — "Feature Frenzy" ⚡
├── 🛒 Order management system
├── 💳 Razorpay payment integration
├── 📊 Admin dashboard with stats
├── 🔐 Google OAuth 2.0 integration
├── 🛡️ Spring Security configuration
└── 📋 My Orders page for buyers

📅 May 2026 — "Polish & Perfect" ✨
├── 🎨 UI improvements across all pages
├── 🐛 Bug fixes (session handling, image paths)
├── 🔒 Security hardening & CSRF config
├── 📱 Responsive design tweaks
├── 🧪 Testing & validation
└── 📝 Documentation & README

📅 June 2026 — "Launch Ready" 🚀
├── 🧹 Final code cleanup
├── 📄 GitHub templates & CI workflow
├── 📋 Contributing guidelines
├── ✅ Final testing & review
└── 🎉 Ready for deployment!
```

---

## 🤝 Contributing

We welcome contributions from fellow retro enthusiasts! Check out our [Contributing Guide](CONTRIBUTING.md) to get started.

---

## 📜 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">

### 🕹️ Built with ❤️ and excessive nostalgia by

**[Manish Rathore](https://github.com/manishrathore77)** 🚀

---

```
╔══════════════════════════════════════════════════╗
║                                                  ║
║   🌈 Thanks for checking out RetroMart! 🌈      ║
║                                                  ║
║   If you enjoyed this project, smash that ⭐     ║
║   button and spread the retro love! 📼🕺        ║
║                                                  ║
║   Remember: Everything was better in the 80s.    ║
║   Except the internet. And smartphones.          ║
║   And... okay, maybe just the aesthetics. 😅     ║
║                                                  ║
╚══════════════════════════════════════════════════╝
```

*Made with mass nostalgia overload in 2026* 💾✨

</div>
