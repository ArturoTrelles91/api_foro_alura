CREATE DATABASE forohub CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE USER 'forouser'@'localhost' IDENTIFIED BY 'foropass';
GRANT ALL PRIVILEGES ON forohub.* TO 'forouser'@'localhost';

FLUSH PRIVILEGES;