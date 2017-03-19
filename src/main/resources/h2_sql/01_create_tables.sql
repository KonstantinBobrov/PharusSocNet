CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(64) UNIQUE NOT NULL,
  password VARCHAR(128) NOT NULL,
  full_name VARCHAR(64),
  birth_date DATE,
  register_date DATETIME,
  role VARCHAR(10)
);

CREATE TABLE friends (
  'id' INT AUTO_INCREMENT PRIMARY KEY,
  id_user INT,
  id_friend INT,
  FOREIGN KEY (id_user) REFERENCES users (id),
  FOREIGN KEY (id_friend) REFERENCES users (id),
);

CREATE TABLE messages (
  id INT AUTO_INCREMENT PRIMARY KEY,
  from_user_id   INT NOT NULL DEFAULT '0',
  to_user_id   INT NOT NULL DEFAULT '0',
  message TEXT,
  post_time DATETIME,
);

CREATE TABLE cars(
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  model_id INT NOT NULL,
  car_year YEAR,
  car_number VARCHAR(10),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (model_id) REFERENCES models(id)
);

CREATE TABLE vendors(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(32)
);

CREATE TABLE models(
  id INT AUTO_INCREMENT PRIMARY KEY,
  vendor_id INT NOT NULL,
  name VARCHAR(32),
  FOREIGN KEY (vendor_id) REFERENCES vendors(id)
);

CREATE TABLE posts(
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  title VARCHAR(255) NOT NULL,
  post TEXT,
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX full_name ON users (full_name);