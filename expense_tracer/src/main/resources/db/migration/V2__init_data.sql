
INSERT INTO t_user (username, email, password, role) VALUES
('admin', 'admin@gmail.com', 'admin123', 'ADMIN'),
('zhan', 'zhan@gmail.com', 'password', 'USER');


INSERT INTO t_category (name, user_id) VALUES
('Food', 1),
('Transport', 1),
('Entertainment', 2),
('Utilities', 2);


INSERT INTO t_expense (amount, description, date, category_id, user_id) VALUES
(15.50, 'Lunch', '2025-12-16 12:30:00', 1, 1),
(2.75, 'Bus ticket', '2025-12-16 08:00:00', 2, 1),
(50.00, 'Concert ticket', '2025-12-15 19:00:00', 3, 2),
(100.00, 'Electricity bill', '2025-12-01 10:00:00', 4, 2);