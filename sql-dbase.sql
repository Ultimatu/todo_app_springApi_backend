--code to create database and user
-- 

CREATE USER 'springapi'@'localhost' IDENTIFIED BY 'cnoYB)ZaFpd9p/@L';

GRANT CREATE, SELECT, UPDATE, DELETE ON database_name.* TO 'springapi'@'localhost';
