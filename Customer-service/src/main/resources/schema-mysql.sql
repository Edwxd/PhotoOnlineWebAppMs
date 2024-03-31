USE `customer-db`;

------Customer Table ------

create table if not exists customers (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50),
    street_address VARCHAR (50),
    city VARCHAR (50),
    state VARCHAR (50),
    country VARCHAR (50),
    postal_code VARCHAR (9)
    );


create table if not exists customer_phonenumbers (
    customer_id INTEGER,
    type VARCHAR(50),
    number VARCHAR(50)
    );

