SELECT COUNT(*) FROM products WHERE buyPrice > 30.00 AND buyPrice < 40.00;

SELECT customers.country, payments.amount FROM customers JOIN payments ON payments.customerNumber = customers.customerNumber WHERE payments.amount = (SELECT MAX(amount) FROM payments);

SELECT COUNT(*) FROM employees WHERE lastName NOT LIKE 'F%';

SELECT SUM(priceEach * quantityOrdered) FROM orderdetails WHERE orderNumber = 10100;

SELECT city, territory FROM offices ORDER BY territory;

SELECT COUNT(*) FROM products WHERE buyPrice > (SELECT AVG(buyPrice) FROM products);

SELECT SUM(payments.amount) FROM customers JOIN payments ON payments.customerNumber = customers.customerNumber WHERE customers.customerName = 'Online Diecast Creations Co.';  

SELECT COUNT(*) FROM orders WHERE status = 'Shipped'

SELECT DISTINCT (status) FROM orders ORDER BY status DESC;

SELECT customerName, city FROM customers;

SELECT customers.customerName FROM customers JOIN orders ON orders.customerNumber = customers.customerNumber JOIN orderdetails ON orderdetails.orderNumber = orders.orderNumber WHERE (orderdetails.quantityOrdered * orderdetails.priceEach) < 500;

SELECT COUNT(*) FROM customers;

SELECT customerName, country, state FROM customers WHERE country = 'USA' AND state = 'CA';

SELECT COUNT(*) FROM customers WHERE customerNumber NOT IN (SELECT customerNumber FROM orders);

SELECT productName, buyPrice FROM products WHERE buyPrice = (SELECT MAX(buyPrice) FROM products);

SELECT * FROM offices WHERE country != 'USA' AND country != 'France';

SELECT * FROM customers WHERE creditLimit = (SELECT MAX(creditLimit) FROM customers)

SELECT COUNT(*) FROM customers WHERE country != 'USA' AND country != 'France';

SELECT * FROM customers WHERE salesRepEmployeeNumber IS NULL;

SELECT * FROM employees WHERE firstName LIKE '%rr%';

SELECT COUNT(*) FROM customers WHERE customerName LIKE '%Ltd%';

SELECT SUM(amount) FROM payments WHERE paymentDate LIKE '%2003%';

SELECT COUNT(*) FROM customers WHERE country != 'USA';

SELECT * FROM employees WHERE reportsTo IS NULL;