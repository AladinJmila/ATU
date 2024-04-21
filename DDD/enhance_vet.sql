-- You may need to save all these enhancments in separate folders with separate names grouped by feature
-- this will enable others to access them and alter them, or enchance if needed with no risk of breaking other stuff

-- PROCEDURES AND FUNCTIONS
DROP PROCEDURE IF EXISTS get_late_bills;
DROP PROCEDURE IF EXISTS make_payment;
DROP PROCEDURE IF EXISTS create_appointment;

-- Return all the late payments based on some condition, like due date is less < 7 days
DELIMITER //
CREATE PROCEDURE get_late_bills()
BEGIN
  SELECT * FROM bills WHERE due_date <= DATE_ADD(NOW(), INTERVAL 7 DAY) AND status != 'Paid';
END //

CREATE PROCEDURE make_payment(
  IN p_bill_id INT,
  IN p_payment_amount DECIMAL(10, 2)
)
BEGIN
  DECLARE v_remaining DECIMAL(10, 2);
  SELECT remaining_amount INTO v_remaining FROM bills WHERE bill_id = p_bill_id;

  IF v_remaining > 0 THEN
    SET v_remaining = v_remaining - p_payment_amount;
    -- Ensure remaining amount doesn't got below 0
    IF v_remaining <= 0 THEN
      SET v_remaining = 0;
      UPDATE bills SET status = 'Paid', payment_date = NOW() WHERE bill_id = p_bill_id; 
    ELSE
      -- Extend the due date by 1 month if small payment is made
      UPDATE bills SET due_date = DATE_ADD(due_date, INTERVAL 1 MONTH) WHERE bill_id = p_bill_id;
    END IF;

  UPDATE bills SET remaining_amount = v_remaining WHERE bill_id = p_bill_id;

  END IF;
END //

CREATE PROCEDURE create_appointment (
  IN p_animal_id INT,
  IN p_note TEXT,
  IN p_visit_date_time DATETIME,
  IN p_staff_ids VARCHAR(50),
  IN p_symptoms_ids VARCHAR(50)
)
BEGIN
  START TRANSACTION;
  DECLARE v_appointment_id INT;

  INSERT INTO appointments (animal_id, note, visit_date_time)
  VALUES (p_animal_id, p_note, p_visit_date_time);

  SET v_appointment_id = LAST_INSERT_ID();

  IF p_staff_ids IS NOT NULL THEN
    INSERT INTO staff_appointments (staff_id, appointment_id)
    SELECT staff_id, v_appointment_id
    FROM staff
    WHERE FIND_IN_SET(staff_id, p_staff_ids) > 0;
  ELSE
    ROLLBACK;
  END IF;

  IF p_symptoms_ids IS NOT NULL THEN
    INSERT INTO appointments_symptoms (appointment_id, symptom_id)
    SELECT v_appointment_id, symptom_id
    FROM symptoms
    WHERE FIND_IN_SET(symptom_id, p_symptoms_ids) > 0;
  ELSE
    ROLLBACK;
  END IF;

  COMMIT;
END //

DELIMITER ;

-- TESTS
CALL create_appointment('4', 'Testing from procedure', '2024-04-21 10:00:00', '1,2', '5,6,7')

-- VIEWS
-- Views provide and abstraction over our databse tables, and this abstraction reduces the impact of changes

-- CREATE OF REPLACE VIEW view_name AS
-- WITH CHECK OPTION will prevent removing updated or deleted rows by throwing an error
-- Animal history
-- Onwer history

-- First create the query, once it succeeds add at the beginning CREATE VIEW view_name AS...

CREATE OR REPLACE VIEW appointment_staff AS
SELECT 
  ap.appointment_id,
  CONCAT(st.first_name, ' ', st.last_name) AS staff_full_name,

  st.job_title,
  st.phone
FROM staff st
JOIN staff_appointments sa ON sa.staff_id = st.staff_id
JOIN appointments ap ON ap.appointment_id = sa.appointment_id;

CREATE OR REPLACE VIEW appointment_symptoms AS
SELECT 
  ap.appointment_id,
  sy.name,
  sy.description
FROM symptoms sy
JOIN appointments_symptoms aps ON aps.symptom_id = sy.symptom_id
JOIN appointments ap ON ap.appointment_id = aps.appointment_id;

CREATE OR REPLACE VIEW owner_animals AS
SELECT 
  o.owner_id,
  CONCAT(o.first_name, ' ', o.last_name) AS owner_full_name,
  o.phone,
  o.email,
  a.animal_id,
  CONCAT(a.species, ' | ',   a.breed) AS patient
FROM owners o
JOIN animals a ON a.owner_id = o.owner_id;


-- CREATE OR REPLACE VIEW todays_appointments AS
-- SELECT 
--   ap.visit_date_time, 
--   oa.owner_full_name,
--   oa.phone,
--   oa.patient,
--   aps.name,
--   ast.staff_full_name,
--   ast.job_title,
--   ast.phone
--   FROM appointments ap 
--   JOIN owner_animals oa ON oa.animal_id = ap.animal_id
--   JOIN appointment_symptoms aps ON aps.appointment_id = ap.appointment_id
--   JOIN appointment_staff ast ON ast.appointment_id = ap.appointment_id 
--   WHERE DATE(ap.visit_date_time) = CURDATE();

CREATE OR REPLACE VIEW todays_appointments AS
SELECT 
  ap.visit_date_time, 
  oa.owner_full_name,
  oa.phone,
  oa.patient,
  GROUP_CONCAT(DISTINCT aps.name SEPARATOR ', ') as symptoms,
  GROUP_CONCAT(DISTINCT '* ', ast.staff_full_name, ' - ', ast.job_title, ' - ', ast.phone SEPARATOR '\n') AS staff_list
  FROM appointments ap 
  JOIN owner_animals oa ON oa.animal_id = ap.animal_id
  JOIN appointment_symptoms aps ON aps.appointment_id = ap.appointment_id
  LEFT JOIN appointment_staff ast ON ast.appointment_id = ap.appointment_id 
  WHERE DATE(ap.visit_date_time) = CURDATE()
  GROUP BY ap.appointment_id
  ORDER BY ap.visit_date_time;

