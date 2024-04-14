-- Return all the late payments based on some condition, like due date is less < 7 days
CREATE PROCEDURE get_late_bills()
BEGIN
  SELECT * FROM bills WHERE due_date <= DATE_ADD(NOW(), INTERVAL 7 DAY);
END;