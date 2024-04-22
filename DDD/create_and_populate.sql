-- CREATE ALL TABLES

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
  visit_date_time DATETIME NOT NULL,
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
    ON DELETE CASCADE,
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
    ON DELETE CASCADE
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
  remaining_amount DECIMAL(10, 2) NOT NULL,
  status ENUM('Paid', 'Late', 'Pending', 'Cancelled'),
  due_date DATE NOT NULL,
  payment_date DATE,
  payment_method ENUM('Credit Card', 'Debit Card', 'Bank Transfer', 'Cheque', 'Cash', 'Mobile Payment'),
  FOREIGN KEY (appointment_id)
    REFERENCES appointments (appointment_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);


-- POPULATE ALL TABLES

USE vet;

INSERT INTO owners (first_name, last_name, email, phone)
  VALUES
    ('John', 'Doe', 'johndoe@example.com', '123-456-7890'),
    ('Jane', 'Smith', 'janesmith@examle.com', '987-654-3210'),
    ('Michael', 'Johnson', 'michaeljohnson@example.com', '456-789-0123');

INSERT INTO animals (owner_id, species, breed, age, sex)
  VALUES
    (1, 'Dog', 'Labrador Retreiver', 3, 'Male'),
    (1, 'Cat', 'Siamese', 5, 'Female'),
    (2, 'Bird', 'Parrot', NULL, 'Unknown'),
    (3, 'Bird', 'Eagle', NULL, 'Unknown');

INSERT INTO symptoms (name, description) 
  VALUES
    ('Vaccination', ' related to the need for vaccination'),
    ('Allergies', ' indicating allergic reactions'),
    ('Running eyes', 'watery or runny eyes'),
    ('Limp', 'lameness or limping'),
    ('Fever', 'elevated body temperature'),
    ('Vomiting', 'nausea and vomiting'),
    ('Diarrhea', 'loose or watery stools'),
    ('Coughing', 'respiratory irritation or infection'),
    ('Skin rash', 'skin irritation or rash'),
    ('Loss of appetite', 'reduced desire to eat');

INSERT INTO appointments (animal_id, note, visit_date_time)
  VALUES 
    (1, 'custom notes that are not covered by the symptoms part', '2024-04-15 13:23:44'),
    (2, 'more custom notes here', '2024-04-21 15:45:21'),
    (3, NULL, '2024-04-21 11:12:01'),
    (4, 'dangeroso', '2024-04-21 12:45:21');

INSERT INTO appointments_symptoms
  VALUES
    (1, 2),
    (1, 3),
    (1, 5),
    (2, 2),
    (2, 7),
    (2, 9),
    (3, 1),
    (4, 1),
    (4, 3),
    (4, 5);

INSERT INTO staff (first_name, last_name, email, job_title, phone)
  VALUES
    ('Sarah', 'Brown', 'sarahbrown@example.com', 'Veterinarian', '111-222-3333'),
    ('Mark', 'Taylor', 'marktaylor@example.com', 'Veterinary Technicien', '444-555-6666'),
    ('Rohin', 'Smock', 'rohinsmock@example.com', 'Receptionist', '999-000-7777');

INSERT INTO staff_appointments (staff_id, appointment_id)
  VALUES 
    (1, 1),
    (1, 2), 
    (1, 3), 
    (1, 4), 
    (2, 1), 
    (2, 2),
    (2, 3),
    (2, 4);

INSERT INTO consultations (appointment_id, diagnosis, followup_date)
  VALUES
    (1, 'Suspected joint injury', '2024-04-18'),
    (2, 'Respiratory infection', '2024-04-17');


INSERT INTO medications (name, description)
  VALUES 
    ('Pain Relief', 'For joint pain'),
    ('Antibiotic', 'To treat infections'),
    ('Vitamin Supplement', 'For overall health');

INSERT INTO perscriptions (consultation_id, medication_id, dosage, dosage_unit, frequency, start_date, end_date)
  VALUES 
    (1, 1, 200, 'mg', 'Once daily', '2024-04-15', '2024-04-25'),
    (1, 2, 5, 'mg', 'Thrice daily', '2024-04-15', '2024-04-25'),
    (2, 2, 10, 'mg', 'Twice daily', '2024-04-15', '2024-04-25'),
    (2, 3, 100, 'mg', 'Once daily', '2024-04-16', '2024-04-20');

INSERT INTO bills (appointment_id, amount, remaining_amount, status, due_date, payment_date, payment_method)
  VALUES 
    (1, 75.00, 75.00, 'Pending', '2024-04-20', NULL, NULL),
    (2, 120.00, 120.00, 'Late', '2024-04-14', NULL, NULL),
    (3, 60.00, 0.00, 'Paid', '2024-04-17', '2024-04-10', 'Credit Card');

