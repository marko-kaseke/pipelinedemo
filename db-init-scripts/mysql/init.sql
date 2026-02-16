-- Create sourceafs database if it doesn't exist
CREATE DATABASE IF NOT EXISTS sourceafs;

-- Use the sourceafs database
USE sourceafs;

-- Create sourceafs user if it doesn't exist
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY 'Psdt@2025';

-- Grant all privileges on 'sourceafs' database to 'root'
GRANT ALL PRIVILEGES ON sourceafs.* TO 'root'@'%';

-- Ensure all privileges are applied
FLUSH PRIVILEGES;

-- Create 'person' table
CREATE TABLE IF NOT EXISTS person (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    fingerprintTemplate longblob,
);

