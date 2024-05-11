create database ecommerce;
use ecommerce;

create table users(
	user_id bigint primary key auto_increment,
    username varchar(100) not null unique,
    email varchar(255),
    fullname varchar(100) not null,
    status Bit(1),
    password varchar(255),
    avatar varchar(255),
    phone varchar(15) not null unique,
    address varchar(255) not null,
    created_at date default(CURRENT_DATE()),
    update_at date 
);
select*from users;
create table role(
	role_id bigint primary key auto_increment,
    role_name enum('ROLE_ADMIN','ROLE_USER')
);
select*from role;
create table user_role(
	user_id bigint,
	role_id bigint,
    primary key(user_id,role_id),
    foreign key(user_id) references users(user_id),
    foreign key(role_id) references role(role_id)
);
select*from user_role;
create table category(
	category_id bigint primary key auto_increment,
    category_name varchar(100) not null,
    description text,
    status Bit(1)
);

create table products(
	product_id bigint primary key auto_increment,
    sku varchar(100) unique,
    product_name varchar(100) not null unique,
    description text,
    unit_price decimal(10,2),
    stock_quantity int check(stock_quantity >=0),
    image varchar(255),
    category_id bigint,
    created_at date default(CURRENT_DATE()),
    update_at date,
    foreign key(category_id) references category(category_id)
);
select*from products;
create table orders(
	order_id bigint primary key auto_increment,
    serial_number varchar(100) ,
    user_id bigint not null,
    total_price decimal(10,2),
    status enum('WAITING','CONFIRM','DELIVERY','SUCCESS','CANCEL','DENIED'),
    note varchar(100),
    receive_name varchar(100),
    receive_address varchar(255),
    receive_phone varchar(15),
    created_at date default(CURRENT_DATE()),
    receive_at date default(DATE_ADD(CURRENT_DATE(),INTERVAL 4 DAY)),
    foreign key(user_id) references users(user_id)
);
select* from orders;
create table order_details(
	order_id bigint ,
    product_id bigint ,
    name varchar(100),
    unit_price decimal(10,2),
    order_quantity int check(order_quantity>0),
    primary key(order_id,product_id),
    foreign key(order_id) references orders(order_id),
    foreign key(product_id) references products(product_id)
);

create table shoping_cart(
	shoping_cart_id int primary key auto_increment,
    product_id bigint,
    user_id bigint,
    order_quantity int check(order_quantity>0),
    foreign key(product_id) references products(product_id),
    foreign key(user_id) references users(user_id)
);

create table address(
	address_id bigint primary key auto_increment,
    user_id bigint,
    full_address varchar(255),
    phone varchar(15),
    receive_name varchar(50),
    foreign key(user_id) references users(user_id)
);

create table wish_list(
	wish_list_id bigint primary key auto_increment,
    user_id bigint,
    product_id bigint,
    foreign key(user_id) references users(user_id),
    foreign key(product_id) references products(product_id)
);