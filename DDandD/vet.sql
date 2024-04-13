-- create a db
-- CREATE DATABASE IF NOT EXISTS vet;
-- drop a db
-- DROP DATABASE IF EXISTS vet;

CREATE DATABASE IF NOT EXISTS vet;
USE vet;

DROP TABLE IF EXISTS owners;
DROP TABLE IF EXISTS animals;
DROP TABLE IF EXISTS staff_appointments;
DROP TABLE IF EXISTS staff;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS consultations;
DROP TABLE IF EXISTS perscriptions_medications;
DROP TABLE IF EXISTS medications;
DROP TABLE IF EXISTS perscriptions;
DROP TABLE IF EXISTS bills;

CREATE TABLE IF NOT EXISTS medications 
(
  medication_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT,
)

CREATE TABLE IF NOT EXISTS perscriptions
(
  perscription_id INT PRIMARY KEY AUTO_INCREMENT,
  dosage DECIMAL(8, 2) NOT NULL,
  dosage_unit VARCHAR(25) NOT NULL,
  frequency VARCHAR(25) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE,
);

CREATE TABLE IF NOT EXISTS perscriptions_medications
(
  perscription_id INT,
  medication_id INT,
  PRIMARY KEY (perscription_id, medication_id),
  FOREIGN KEY (perscription_id)
    REFERENCES perscriptions (perscription_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
  FOREIGN KEY (medication_id)
    REFERENCES medications (medication_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS owners
(
  owner_id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  phone VARCHAR(25) NOT NULL,
);

CREATE TABLE IF NOT EXISTS animals
(
  animal_id INT FOREIGN KEY AUTO_INCREMENT,
  owner_id INT NOT NULL,
  species VARCHAR(255) NOT NULL,
  breed VARCHAR(255) NOT NULL,
  age TINYINT UNSIGNED,
  sex ENUM('Make', 'Female', 'Unknown')
  FOREIGN KEY (owner_id)
    REFERENCES owners (owner_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS appointments (
  appointment_id INT PRIMARY KEY AUTO_INCREMENT,
  animal_id INT NOT NULL,
  symptoms TEXT NOT NULL,
  visit_date DATE NOT NULL,
  FOREIGN KEY (animal_id)
    REFERENCES animals (animal_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS staff
(
  staff_id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  job_title VARCHAR(50) NOT NULL,
  phone VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS staff_appointments
(
  staff_id INT,
  appointment_id INT,
  PRIMARY KEY (staff_id, appointment_id),
  FOREIGN KEY (staff_id)
    REFERENCES staff (staff_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
  FOREIGN KEY (appointment_id)
    REFERENCES appointments (appointment_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS consultations
(
  consultation_id INT PRIMARY KEY AUTO_INCREMENT,
  appointment_id INT NOT NULL,
  perscription_id INT,
  diagnosis TEXT NOT NULL,
  followup_date DATE,
  FOREIGN KEY (appointment_id)
    REFERENCES appointments (appointment_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
  FOREIGN KEY (perscription_id)
    REFERENCES perscriptions (perscription_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS bills
(
  bill_id INT PRIMARY KEY AUTO_INCREMENT,
  appointment_id INT NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  `status` ENUM('Payed', 'Late', 'Pending', 'Cancelled'),
  due_date DATE NOT NULL,
  payment_date DATE,
  payment_method ENUM('Credit card', 'Debit Card', 'Bank Transfer', 'Cheque', 'Cash', 'Mobile Payment'),
  FOREIGN KEY (appointment_id)
   REFERENCES appointments (appointment_id)
   ON UPDATE CASCADE
   ON DELETE NO ACTION
);


-- poits INT NOT NULL DEFAULT 0,

-- If you want to rerun the CREATE TABLE command, you will not to drop it first
DROP TABLE IF EXISTS staff;
-- To avoid getting an error, you use a second approach
CREATE TABLE IF NOT EXISTS staff ();

-- To alter an existing table and add a new column or change a datatype
ALTER TABLE staff 
  ADD new_col_name VARCHAR(50) NOT NULL AFTER existing_col_name,
  ADD another_col_name VARCHAR(255),
  MODIFY existing_col_name VARCHAR(25) DEFAULT '',
  DROP other_existing_col;
-- Never alter a table in a procution environment