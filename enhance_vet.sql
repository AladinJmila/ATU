-- Return all the late payments based on some condition, like due date is less < 7 days
DELIMITER $$
CREATE PROCEDURE get_late_bills()
BEGIN
  SELECT * FROM bills WHERE due_date <= DATE_ADD(NOW(), INTERVAL 7 DAY);
END $$

DELIMITER ;

CALL get_late_bills();

-- ADD:
-- STORED PROCEDURE (WITH OR WITHOUT PARAMETERS)
-- USER VAIRABLES
-- FUNCTION
-- TRIGGERS
-- EVENTS
-- INDEXING
-- USERS

-- WATCH THE TRANSACTIONS AND CONCURRENCY SECTION

-- Create a function or an event that reduces the bill amount by the amount payed 
-- and increases the due date by 1 month. It should cater for 0 and - values

-- Create some views where you can see:
-- number of appointments each stuff memeber has for next week
-- Add apointment time so you can provide mulitple appointments per day
-- As you add an appointment you need to confirm that the staff is not already busy with another one
-- Give the staff member a 15 minutes break before every follwoing appointment and exclude lunch time
-- Return all appointments for current week, current month (return an appointments diary)
-- Return all appointments for today
-- Return all unpaied bills and late cancellations (review the tables design to take care of this)
-- Add a trigger to update the payment status

