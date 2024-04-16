DROP PROCEDURE IF EXISTS get_late_bills;
DROP PROCEDURE IF EXISTS make_payment;

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
  DECLARE remaining DECIMAL(10, 2);
  SELECT remaining_amount INTO remaining FROM bills WHERE bill_id = p_bill_id;

  IF remaining > 0 THEN
    SET remaining = remaining - p_payment_amount;
    -- Ensure remaining amount doesn't got below 0
    IF remaining <= 0 THEN
      SET remaining = 0;
      UPDATE bills SET status = 'Paid', payment_date = NOW() WHERE bill_id = p_bill_id; 
    ELSE
      -- Extend the due date by 1 month if small payment is made
      UPDATE bills SET due_date = DATE_ADD(due_date, INTERVAL 1 MONTH) WHERE bill_id = p_bill_id;
    END IF;

  UPDATE bills SET remaining_amount = remaining WHERE bill_id = p_bill_id;

  END IF;
END //

DELIMITER //

