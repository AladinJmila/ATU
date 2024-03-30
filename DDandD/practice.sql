-- Specify both the comlun names and the values to be inserted
INSERT INTO table_name (column_1, column_2, column_3) VALUES (value_1, value_2, value_3);

-- If you are adding values for all the columns of the table, you do not need to sepcify the column names. However, make sure the order of the values is in the same order as the columns
INSERT INTO table_name VALUES (value_1, value_2, value_3);

INSERT INTO customers (customerName, city, country) VALUES ('Cardinal', 'Stavanger', 'Norway'), ('Gardinal', 'Gtavanger', 'Gorway')

-- Copy all columns from one table to another table
INSERT INTO table_2 SELECT * FROM table_1 WHERE condition;

-- Copy only some columns from one table to another table:
INSERT INTO table_2 (column_1, column_2, column_3) SELECT column_1, column_2, column_3 FROM table_2 WHERE condition;

-- The update statement is used to modify the existing records in a table
UPDATE table_name SET column_1 = value_1, column_2 = value_2 WHERE condition;

UPDATE customers SET contactName = 'some name', city = 'some city' WHERE customerId = 1;

UPDATE customers SET contactName = 'Juan' WHERE city = 'Mexico';

-- Be careful when updating records. If you omit the WHERE clause, ALL records will be updated!

-- The DELETE statement is used to delete existing records in a table

DELETE FROM table_name WHERE condition;

-- It is possible ot delete all rows in a table without deleting the table. This means that the table structure, attributes, and idexes will be intact:
DELETE FROM table_name;

INSERT INTO products VALUES ( ProductCode: 'P1234', ProductName: 'New Product', ProductLine: 'Cars', ProductScale: '1:24', ProductVendor: 'ABC Company', ProductDescription: 'Description of the new product', QuantityInStock: 100, BuyPrice: 50.00, MSRP: 75.00);

INSERT INTO porducts (ProductCode, ProductName, ProductLine, ProductScale, ProductVendor, ProductDescription, QuantityInStock, BuyPrice, MSRP) VALUES ('P1234', 'New Product', 'Cars', '1:24', 'ABC Company', 100, 50.00, 75.00)

INSERT INTO customers VALUES (CustomerNumber: 1001, CustomerName: 'New Customer', ContactLastName: 'Smith', ContactFirstName: 'John', Phone: '123-456-7890', AddressLine1: '123 Main St', City: 'Anytown', State: 'CA', PostalCode: '12345', Country: 'USA', SalesRepEmployeeNumber: 1370, CreditLimit: 5000.00);

UPDATE products SET quantityInStock = 150 WHERE productLine = 'planes'; 

UPDATE customers SET creditLimit = creditLimit + 1000 WHERE creditLimit < 2000;

DELETE * FROM orders WHERE customerNumber IN (SELECT * FROM customers WHERE creditLimit < 500)

DELETE * FROM products WHERE quantityInStock = 0;