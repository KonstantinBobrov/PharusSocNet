CREATE TABLE drivers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(64) UNIQUE NOT NULL,
  password VARCHAR(128) NOT NULL,
  full_name VARCHAR(64),
  city VARCHAR(64),
  birth_date DATE,
  register_date DATE,
  role VARCHAR(10)
);

CREATE TABLE friends (
  id_driver INT,
  id_friend INT,
  FOREIGN KEY (id_driver) REFERENCES drivers(id),
  FOREIGN KEY (id_friend) REFERENCES drivers(id),
);

CREATE TABLE cars(
  id INT AUTO_INCREMENT PRIMARY KEY,
  driver_id INT,
  model_id INT NOT NULL,
  car_year YEAR,
  car_number VARCHAR(10),
  FOREIGN KEY (driver_id) REFERENCES drivers(id),
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
  driver_id INT,
  title VARCHAR(255) NOT NULL,
  post TEXT,
  FOREIGN KEY (driver_id) REFERENCES drivers(id)
);

CREATE INDEX full_name ON drivers(full_name);