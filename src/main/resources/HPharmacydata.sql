-- Insert into Users table
INSERT INTO users (full_name, email, phone, username, password, active) VALUES 
('John Doe', 'johndoe@gmail.com', '0912345678', 'johndoe', 'password123', 1),
('Jane Smith', 'janesmith@gmail.com', '0912345679', 'janesmith', 'password123', 1),
('Chris Evans', 'chrisevans@gmail.com', '0912345680', 'chrisevans', 'password123', 1),
('Emily Davis', 'emilydavis@gmail.com', '0912345681', 'emilydavis', 'password123', 1),
('Michael Brown', 'michaelbrown@gmail.com', '0912345682', 'michaelbrown', 'password123', 1),
('Sarah Johnson', 'sarahjohnson@gmail.com', '0912345683', 'sarahjohnson', 'password123', 1),
('David Wilson', 'davidwilson@gmail.com', '0912345684', 'davidwilson', 'password123', 1),
('Sophia Lee', 'sophialee@gmail.com', '0912345685', 'sophialee', 'password123', 1),
('Daniel Moore', 'danielmoore@gmail.com', '0912345686', 'danielmoore', 'password123', 1),
('Olivia White', 'oliviawhite@gmail.com', '0912345687', 'oliviawhite', 'password123', 1);

-- Insert into Roles table
INSERT INTO roles (role_id, role) VALUES 
(2, 'ADMIN'), 
(4, 'GUEST'), 
(1, 'OWNER'), 
(3, 'SALES') 
ON DUPLICATE KEY UPDATE role = VALUES(role);

-- Insert into User_Role table
INSERT INTO user_role (user_id, role_id) VALUES 
(1, 2), (2, 1), (3, 3), (4, 4), (5, 2), 
(6, 1), (7, 3), (8, 4), (9, 2), (10, 1);

-- Insert into Addresses table
INSERT INTO addresses (address_detail, receiver, address_phone, user_id) VALUES 
('123 Street, City', 'John Doe', '0912345678', 1),
('456 Avenue, City', 'Jane Smith', '0912345679', 2),
('789 Road, City', 'Chris Evans', '0912345680', 3),
('321 Blvd, City', 'Emily Davis', '0912345681', 4),
('654 Lane, City', 'Michael Brown', '0912345682', 5),
('987 Square, City', 'Sarah Johnson', '0912345683', 6),
('123 Circle, City', 'David Wilson', '0912345684', 7),
('456 Terrace, City', 'Sophia Lee', '0912345685', 8),
('789 Drive, City', 'Daniel Moore', '0912345686', 9),
('321 Park, City', 'Olivia White', '0912345687', 10);

-- Insert into ProductType table
INSERT INTO product_types (type_name) VALUES 
('Medicine'),
('Supplement'),
('Beauty Products'),
('Baby Care'),
('Fitness');

-- Insert into Products table
INSERT INTO products (product_name, description, product_quantity, price, product_type_id) VALUES 
('Pain Reliever', 'Effective pain reliever', 100, 10.50, 1),
('Vitamin C', 'Boosts immune system', 200, 15.00, 2),
('Face Cream', 'Moisturizing face cream', 50, 25.75, 3),
('Baby Powder', 'Gentle baby powder', 80, 8.99, 4),
('Protein Powder', 'For muscle growth', 120, 35.99, 5),
('Cough Syrup', 'Soothes throat', 60, 12.50, 1),
('Omega 3', 'Supports heart health', 90, 20.00, 2),
('Sunscreen', 'SPF 50 sunscreen', 75, 18.99, 3),
('Diaper', 'Soft and absorbent', 100, 22.50, 4),
('Dumbbells', 'Pair of 10kg dumbbells', 30, 45.00, 5);


-- Insert into Cart table
INSERT INTO cart (user_id) VALUES 
(1), (2), (3), (4), (5), 
(6), (7), (8), (9), (10);

-- Insert into CartItems table
INSERT INTO cart_items (cart_id, product_id, item_quantity) VALUES 
(1, 1, 2), (1, 2, 1), (2, 3, 1), (3, 4, 3), 
(4, 5, 2), (5, 6, 1), (6, 7, 2), (7, 8, 1),
(8, 9, 1), (9, 10, 2), (10, 1, 1);


-- Insert into Orders table
INSERT INTO orders (order_date, user_id, address_id) VALUES 
('2024-10-01', 1, 1),
('2024-10-02', 2, 2),
('2024-10-03', 3, 3),
('2024-10-04', 4, 4),
('2024-10-05', 5, 5),
('2024-10-06', 6, 6),
('2024-10-07', 7, 7),
('2024-10-08', 8, 8),
('2024-10-09', 9, 9),
('2024-10-10', 10, 10);

-- Insert into OrderItems table
INSERT INTO order_items (order_id, product_id, item_quantity) VALUES 
(1, 1, 2), (1, 2, 1), (2, 3, 2), (3, 4, 1),
(4, 5, 2), (5, 6, 1), (6, 7, 1), (7, 8, 2),
(8, 9, 3), (9, 10, 1), (10, 1, 1);
