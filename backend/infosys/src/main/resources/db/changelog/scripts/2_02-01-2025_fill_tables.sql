INSERT INTO roles(id, name)
VALUES (0, 'admin'),
       (1, 'employee');

INSERT INTO pickup_points(id, address, capacity, size)
VALUES (0, 'Kronverkskiy 49', 100, 0);

INSERT INTO users(id, login, password, role_id, name, pickup_points_id, deleted)
VALUES (0, 'sleeter', '$2a$10$ukZf2ri8NNcTyN0F6LtDp./iXPgrxvcyasKdrhfg0pxPaM4YYrL.e', 0, 'Timbet', null, false)