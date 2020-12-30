-- customers

create table m_customers
(
    id                bigserial   not null
        constraint m_users_pk
            primary key,
    name              varchar(50) not null,
    email             varchar(50) not null,
    password          varchar(60) not null,
    registration_date timestamp   not null,
    email_verified    boolean,
    is_active         boolean
);

alter table m_customers
    owner to pobjhqpdbwyibp;


create
unique index m_users_id_uindex
    on m_customers (id);

--  roles

create table m_roles
(
    id        bigserial   not null
        constraint m_roles_pk
            primary key,
    role_name varchar(45) not null
);

alter table m_roles
    owner to pobjhqpdbwyibp;

create
unique index m_roles_id_uindex
    on m_roles (id);

--  customers_roles

create table l_customers_roles
(
    customer_id bigint not null
        constraint m_customers_roles_m_customers_id_fk
            references m_customers,
    role_id     bigint not null
        constraint m_customers_roles_m_roles_id_fk
            references m_roles
);

alter table l_customers_roles
    owner to pobjhqpdbwyibp;

--  customers_contacts

create table m_customers_contacts
(
    id          bigserial not null
        constraint m_contacts_pk
            primary key,
    phone       varchar(15),
    zip         integer,
    city        varchar(50),
    address     varchar(100),
    customer_id bigint    not null
        constraint m_customers_contacts_m_customers_id_fk
            references m_customers
            on delete cascade
);

alter table m_customers_contacts
    owner to pobjhqpdbwyibp;

create
unique index m_contacts_id_uindex
    on m_customers_contacts (id);

--  manufacturers

create table m_manufacturers
(
    id   bigserial   not null
        constraint m_manufacturers_pk
            primary key,
    name varchar(50) not null
);

alter table m_manufacturers
    owner to pobjhqpdbwyibp;

create
unique index m_manufacturers_id_uindex
    on m_manufacturers (id);

--  categories

create table m_categories
(
    id   bigserial    not null
        constraint m_categories_pk
            primary key,
    name varchar(100) not null
);

alter table m_categories
    owner to pobjhqpdbwyibp;

create
unique index m_categories_id_uindex
    on m_categories (id);

--  subcategories

create table m_subcategories
(
    id          bigserial not null
        constraint m_subcategories_pk
            primary key,
    name        varchar(100),
    category_id bigint
        constraint m_subcategories_m_categories_id_fk
            references m_categories
);

alter table m_subcategories
    owner to pobjhqpdbwyibp;

create
unique index m_subcategories_id_uindex
    on m_subcategories (id);

--  products

create table m_products
(
    id              bigserial      not null
        constraint m_products_pk
            primary key,
    name            varchar(50)    not null,
    price           numeric(10, 2) not null,
    picture_url     varchar(100),
    manufacturer_id bigint
        constraint m_products_m_manufacturers_id_fk
            references m_manufacturers,
    code            varchar(7),
    category_id     bigint
        constraint m_products_m_categories_id_fk
            references m_categories,
    subcategory_id  bigint
        constraint m_products_m_subcategories_id_fk
            references m_subcategories
);

alter table m_products
    owner to pobjhqpdbwyibp;

create
unique index m_products_id_uindex
    on m_products (id);

create
unique index m_products_code_uindex
    on m_products (code);

--  carts

create table m_carts
(
    id          bigserial not null
        constraint m_cart_pk
            primary key,
    customer_id bigint    not null
        constraint m_carts_m_customers_id_fk
            references m_customers,
    total_price numeric(10, 2) default 0
);

alter table m_carts
    owner to pobjhqpdbwyibp;

create
unique index m_cart_id_uindex
    on m_carts (id);

--  cart_items

create table m_cart_items
(
    id         bigserial not null
        constraint m_cart_item_pk
            primary key,
    cart_id    bigint
        constraint m_cart_items_m_carts_id_fk
            references m_carts,
    product_id bigint
        constraint m_cart_items_m_products_id_fk
            references m_products,
    amount     integer
);

alter table m_cart_items
    owner to pobjhqpdbwyibp;

create
unique index m_cart_item_id_uindex
    on m_cart_items (id);

--  orders

create table m_orders
(
    id          bigserial      not null
        constraint m_orders_pk
            primary key,
    customer_id bigint         not null
        constraint m_orders_m_customers_id_fk
            references m_customers,
    date        timestamp      not null,
    total_price numeric(10, 2) not null,
    status      varchar(20)    not null,
    number      varchar(10)    not null
);

alter table m_orders
    owner to pobjhqpdbwyibp;

create
unique index m_orders_id_uindex
    on m_orders (id);

create
unique index m_orders_number_uindex
    on m_orders (number);

--  ordered_products

create table m_ordered_products
(
    id         bigserial not null
        constraint m_ordered_products_pk
            primary key,
    order_id   bigint    not null
        constraint m_ordered_products_m_orders_id_fk
            references m_orders,
    product_id bigint    not null
        constraint m_ordered_products_m_products_id_fk
            references m_products,
    amount     bigint
);

alter table m_ordered_products
    owner to pobjhqpdbwyibp;

create
unique index m_ordered_products_id_uindex
    on m_ordered_products (id);

--  password_reset_token

create table m_password_reset_token
(
    id          bigserial    not null
        constraint m_password_reset_token_pk
            primary key,
    token       varchar(100) not null,
    expiration  timestamp    not null,
    customer_id bigint       not null
        constraint m_password_reset_token_m_customers_id_fk
            references m_customers
);

alter table m_password_reset_token
    owner to pobjhqpdbwyibp;

create
unique index m_password_reset_token_id_uindex
    on m_password_reset_token (id);

--  verification_email_token

create table m_verification_email_token
(
    id          bigserial    not null
        constraint table_name_pk
            primary key,
    token       varchar(100) not null,
    customer_id integer      not null
        constraint table_name_m_customers_id_fk
            references m_customers,
    expiration  timestamp    not null
);

alter table m_verification_email_token
    owner to pobjhqpdbwyibp;

create
unique index table_name_id_uindex
    on m_verification_email_token (id);