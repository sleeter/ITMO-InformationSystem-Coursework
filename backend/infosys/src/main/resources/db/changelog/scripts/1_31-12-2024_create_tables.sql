CREATE TABLE IF NOT EXISTS roles -- админ, доставщик, сотрудник
(
    id bigserial primary key,
    name varchar(30) not null
);
CREATE TABLE IF NOT EXISTS status -- в пвз, выдан клиенту, вернут в пвз, вернут продавцу
(
    id bigserial primary key,
    name varchar(30) not null
);
CREATE TABLE IF NOT EXISTS pickup_points
(
    id bigserial primary key,
    address varchar(50) not null,
    size bigint default 0 -- add trigger
);
CREATE TABLE IF NOT EXISTS users (
    id bigserial primary key,
    login varchar(50) not null unique,
    password varchar(100) not null,
    role_id bigint references roles(id),
    name varchar(30) not null,
    pickup_points_id bigint references pickup_points(id),
    deleted boolean not null
);
CREATE TABLE IF NOT EXISTS users_updates (
    id bigint primary key,
    login varchar(50) not null unique,
    role_id bigint references roles(id),
    name varchar(30) not null,
    pickup_points_id bigint references pickup_points(id),
    approved varchar(30) not null
);
CREATE TABLE IF NOT EXISTS customers
(
    id bigserial primary key,
    name varchar(30) not null,
    email varchar(50) unique not null,
    age int not null,
    phone_number varchar(30) not null
);
CREATE TABLE IF NOT EXISTS category
(
    id bigserial primary key,
    name varchar(30) not null,
    description varchar(255) not null
);
CREATE TABLE IF NOT EXISTS payments
(
    id bigserial primary key,
    payments_method varchar(255) not null
);
CREATE TABLE IF NOT EXISTS orders
(
    id bigserial primary key,
    customers_id bigint references customers(id),
    size bigint not null,
    date timestamp not null,
    status_id bigint references status(id),
    pickup_points_id bigint references pickup_points(id),
    total_price decimal not null,
    payment_id bigint references payments(id)
);
CREATE TABLE IF NOT EXISTS products
(
    id bigserial primary key,
    name varchar(30) not null,
    size bigint not null,
    price bigint not null,
    description varchar(255) not null,
    category_id bigint references category(id)
);
CREATE TABLE IF NOT EXISTS ordered_products
(
    id bigserial primary key,
    order_id bigint references orders(id), -- b-tree index
    product_id bigint references products(id),
    count int not null,
    unique (order_id, product_id)
);

CREATE INDEX IF NOT EXISTS idx_ordered_products_orders_id ON ordered_products(order_id);
