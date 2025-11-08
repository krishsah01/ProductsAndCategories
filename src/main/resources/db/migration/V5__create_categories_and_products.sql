CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

INSERT INTO categories (name, description) VALUES
('Electronics', 'Electronic devices and gadgets'),
('Clothing', 'Apparel and fashion items'),
('Books', 'Physical and digital books'),
('Home & Garden', 'Home improvement and gardening supplies');

INSERT INTO products (name, description, price, category_id) VALUES
('Laptop', 'High-performance laptop with 16GB RAM', 1299.99, 1),
('Smartphone', 'Latest model smartphone with 5G', 899.99, 1),
('T-Shirt', 'Cotton t-shirt in various colors', 19.99, 2),
('Jeans', 'Classic blue denim jeans', 49.99, 2),
('Programming Book', 'Learn Java in 21 days', 39.99, 3),
('Garden Tools Set', 'Complete set of gardening tools', 79.99, 4);

