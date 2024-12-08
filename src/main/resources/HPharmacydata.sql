INSERT INTO roles (role_id, role) VALUES
(1, 'OWNER'),
(2, 'ADMIN'),
(3, 'SALES'),
(4, 'GUEST')
ON DUPLICATE KEY UPDATE role = VALUES(role);

INSERT INTO product_types (type_id, type_name) VALUES
(1, 'Thuốc'),
(2, 'Thực phẩm chức năng'),
(3, 'Mỹ phẩm'),
(4, 'Sản phẩm chăm sóc trẻ em'),
(5, 'Thiết bị y tế');
