USE `delivery-db`;

create table if not exists deliveries(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    delivery_id VARCHAR(50),
    company VARCHAR(50),
    delivery_Instructions VARCHAR(75),
    date VARCHAR(50) NOT NULL,
    time VARCHAR(30) NOT NULL
);


