-- Drop table Orders if exists
DROP TABLE orders;

-- Creates table Orderes
CREATE TABLE orders (
id INT PRIMARY KEY NOT NULL,
name VARCHAR(20) NOT NULL,
description VARCHAR(60) NOT NULL);

-- SQL script to load data in the tables
LOAD DATA LOCAL INFILE '/home/jeff/orderdata.csv'
INTO TABLE hadoop.orders
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
('id', `name`, `description`);
