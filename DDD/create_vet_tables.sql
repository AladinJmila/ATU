CREATE DATABASE IF NOT EXISTS vet;
USE vet;

DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS perscriptions;
DROP TABLE IF EXISTS medications;
DROP TABLE IF EXISTS consultations;
DROP TABLE IF EXISTS staff_appointments;
DROP TABLE IF EXISTS staff;
DROP TABLE IF EXISTS appointments_symptoms;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS symptoms;
DROP TABLE IF EXISTS animals;
DROP TABLE IF EXISTS owners;

CREATE TABLE IF NOT EXISTS owners (
  owner_id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  phone VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS animals (
  animal_id INT PRIMARY KEY AUTO_INCREMENT,
  owner_id INT NOT NULL,
  species VARCHAR(255) NOT NULL,
  breed VARCHAR(255) NOT NULL,
  age TINYINT UNSIGNED,
  sex ENUM('Male', 'Female', 'Unknown'),
  FOREIGN KEY (owner_id)
    REFERENCES owners (owner_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS symptoms (
  symptom_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(55) NOT NULL,
  description TEXT
);

CREATE TABLE IF NOT EXISTS appointments (
  appointment_id INT PRIMARY KEY AUTO_INCREMENT,
  animal_id INT NOT NULL,
  note TEXT,
  visit_date DATE NOT NULL,
  FOREIGN KEY (animal_id)
    REFERENCES animals (animal_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS appointments_symptoms (
  appointment_id INT NOT NULL,
  symptom_id INT NOT NULL,
  PRIMARY KEY (appointment_id, symptom_id),
  FOREIGN KEY (appointment_id)
    REFERENCES appointments (appointment_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (symptom_id)
    REFERENCES symptoms (symptom_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS staff (
  staff_id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  job_title VARCHAR(50) NOT NULL,
  phone VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS staff_appointments (
  staff_id INT,
  appointment_id INT,
  PRIMARY KEY (staff_id, appointment_id),
  FOREIGN KEY (staff_id)
    REFERENCES staff (staff_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (appointment_id)
    REFERENCES appointments (appointment_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS consultations (
  consultation_id INT PRIMARY KEY AUTO_INCREMENT,
  appointment_id INT NOT NULL,
  diagnosis TEXT NOT NULL,
  followup_date DATE,
  FOREIGN KEY (appointment_id)
    REFERENCES appointments (appointment_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS medications (
  medication_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT
);

CREATE TABLE IF NOT EXISTS perscriptions (
  perscription_id INT PRIMARY KEY AUTO_INCREMENT,
  consultation_id INT NOT NULL,
  medication_id INT NOT NULL,
  dosage DECIMAL(8, 2) NOT NULL,
  dosage_unit VARCHAR(25) NOT NULL,
  frequency VARCHAR(25) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE,
  FOREIGN KEY (consultation_id)
    REFERENCES consultations (consultation_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (medication_id)
    REFERENCES medications (medication_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS bills (
  bill_id INT PRIMARY KEY AUTO_INCREMENT,
  appointment_id INT NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  status ENUM('Paid', 'Late', 'Pending', 'Cancelled'),
  due_date DATE NOT NULL,
  payment_date DATE,
  payment_method ENUM('Credit Card', 'Debit Card', 'Bank Transfer', 'Cheque', 'Cash', 'Mobile Payment'),
  FOREIGN KEY (appointment_id)
    REFERENCES appointments (appointment_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);
