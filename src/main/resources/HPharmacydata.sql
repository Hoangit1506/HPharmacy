INSERT INTO roles (role_id, role) VALUES
(1, 'OWNER'),
(2, 'ADMIN'),
(3, 'SALES'),
(4, 'GUEST')
ON DUPLICATE KEY UPDATE role = VALUES(role);

INSERT INTO product_types (type_name) VALUES
('Thuốc'),
('Thực phẩm chức năng'),
('Mỹ phẩm'),
('Sản phẩm chăm sóc trẻ em'),
('Thiết bị y tế');
ON DUPLICATE KEY UPDATE type_name = VALUES(type_name);
