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
    (2, 'Bird', 'Parrot', NULL, 'Unknown');

INSERT INTO appointments (animal_id, symptoms, visit_date)
  VALUES 
    (1, 'Limbing and loss of apetite', '2024-04-15'),
    (2, 'Coughing and sneezing', '2024-04-16'),
    (3, 'Routine check-up', '2024-04-17');

INSERT INTO staff (first_name, last_name, email, job_title, phone)
  VALUES
    ('Sarah', 'Brown', 'sarahbrown@example.com', 'Veterinarian', '111-222-3333'),
    ('Mark', 'Taylor', 'marktaylor@example.com', 'Veterinary Technicien', '444-555-6666'),
    ('Rohin', 'Smock', 'rohinsmock@example.com', 'Receptionist', '999-000-7777');

INSERT INTO staff_appointments
  VALUES (1, 1), (1, 2), (2, 1), (2, 2);

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

INSERT INTO bills (appointment_id, amount, status, due_date, payment_date, payment_method)
  VALUES 
    (1, 75.00, 'Pending', '2024-04-20', NULL, NULL),
    (2, 120.00, 'Late', '2024-04-14', NULL, NULL),
    (3, 60.00, 'Paid', '2024-04-17', '2024-04-10', 'Credit Card');









