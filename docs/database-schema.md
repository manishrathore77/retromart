# RetroMart Database Schema 🗄️

## Overview
RetroMart uses MySQL 8.0+ with 4 main tables for managing users, products, images, and orders.

## Tables

### 👤 users
| Column | Type | Constraints |
|--------|------|-------------|
| user_id | INT | PK, AUTO_INCREMENT |
| name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(100) | UNIQUE, NOT NULL |
| phone | VARCHAR(15) | |
| password | VARCHAR(255) | NOT NULL |
| user_type | ENUM('buyer','seller') | NOT NULL |

### 📦 products
| Column | Type | Constraints |
|--------|------|-------------|
| product_id | INT | PK, AUTO_INCREMENT |
| seller_id | INT | FK -> users, NOT NULL |
| title | VARCHAR(200) | NOT NULL |
| description | TEXT | |
| price | DECIMAL(10,2) | NOT NULL |

### 🖼️ product_images
| Column | Type | Constraints |
|--------|------|-------------|
| image_id | INT | PK, AUTO_INCREMENT |
| product_id | INT | FK -> products, ON DELETE CASCADE |
| image_url | VARCHAR(500) | NOT NULL |

### 🛒 orders
| Column | Type | Constraints |
|--------|------|-------------|
| order_id | INT | PK, AUTO_INCREMENT |
| buyer_id | INT | FK -> users, NOT NULL |
| product_id | INT | FK -> products, NOT NULL |
| order_date | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| payment_status | VARCHAR(50) | DEFAULT 'pending' |
| delivery_status | VARCHAR(50) | DEFAULT 'processing' |
| shipping_address | TEXT | |
| contact_phone | VARCHAR(15) | |
| payment_mode | VARCHAR(50) | |
| transaction_id | VARCHAR(100) | |

## Relationships
```
users 1──────M products
users 1──────M orders
products 1───M product_images
products 1───M orders
```
