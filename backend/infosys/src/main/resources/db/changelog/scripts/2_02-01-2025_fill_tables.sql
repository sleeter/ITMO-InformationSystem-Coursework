INSERT INTO roles(id, name)
VALUES (0, 'admin'),
       (1, 'employee');

INSERT INTO pickup_points(id, address, capacity, size)
VALUES (0, 'Kronverkskiy 49', 100, 0);

INSERT INTO users(id, login, password, role_id, name, pickup_points_id, deleted)
VALUES (0, 'sleeter', '$2a$10$ukZf2ri8NNcTyN0F6LtDp./iXPgrxvcyasKdrhfg0pxPaM4YYrL.e', 0, 'Timbet', null, false);

INSERT INTO category(id, name, description)
VALUES (0, 'Товары для взрослых', 'Какое-то описание'),
       (1, 'Какая-то категория', 'Какое-то описание');

INSERT INTO status(id, name)
VALUES (0, 'В ПВЗ'),
       (1, 'Выдан кленту'),
       (2, 'Вернут в ПВЗ'),
       (3, 'Вернут продавцу');

INSERT INTO customers(id, name, email, age, phone_number)
VALUES (0, 'Timur', 'sleeter@gmail.com', 21, '+7123456789');

INSERT INTO payments(id, payments_method)
VALUES (0, 'cash'),
       (1, 'by card');

INSERT INTO products(id, name, size, description, category_id)
VALUES (0, 'Какой-то товар', 5, 'Какое-то описание', 1);