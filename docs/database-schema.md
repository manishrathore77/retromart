# RetroMart Database Schema

## Tables

### users
- user_id (PK, AUTO_INCREMENT)
- name
- email (UNIQUE)
- phone
- password
- user_type (buyer/seller)

### products  
- product_id (PK, AUTO_INCREMENT)
- seller_id (FK -> users)
- title
- description
- price

### product_images
- image_id (PK, AUTO_INCREMENT)
- product_id (FK -> products)
- image_url

### orders
- order_id (PK, AUTO_INCREMENT)
- buyer_id (FK -> users)
- product_id (FK -> products)
- order_date
- payment_status
- delivery_status
- shipping_address
- contact_phone
- payment_mode
- transaction_id
