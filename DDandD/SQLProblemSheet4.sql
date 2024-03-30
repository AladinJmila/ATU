-- QUESTION 1
-- List the names and jobs for all of the employees having the same job as JONES.

SELECT ENAME, JOB FROM EMP 
WHERE JOB = (
  SELECT JOB FROM EMP 
  WHERE ENAME = "JONES"
  );

-- QUESTION 2
-- List the name and salary of each employee who earns more than the average of all of
-- the employees salaries.

SELECT ENAME, SAL FROM EMP 
WHERE SAL > (
  SELECT AVG(SAL) FROM EMP
  );

-- QUESTION 3
-- Using a single command, create a table called promotion with fields called ename,
-- job, salary, and comm, then copy the corresponding data from the fields in the emp
-- table into the promotion table for all those employee whose commission is more
-- than one quarter of their salary.

CREATE TABLE PROMOTION AS 
SELECT ENAME, JOB, SAL, COMM 
FROM EMP 
WHERE COMM > SAL * 0.25;

-- QUESTION 4
-- Create a view called emp10 with the empno, ename, and job data for department 10.

CREATE VIEW EMP10 AS 
SELECT EMPNO, ENAME, JOB 
FROM EMP 
WHERE DEPTNO = 10;

-- QUESTION 5
-- Insert the following information into the EMP10 view
-- 20 BAILEY MANAGER
-- ANY PROBLEMS?

INSERT INTO EMP10 VALUES (20, "BAILEY", "MANAGER");
-- Cannot add or update a child row: a foreign key constraint fails

-- QUESTION 6
-- List all of the data in view emp10.

SELECT * FROM EMP10;

-- QUESTION 7
-- Create the Qual table demonstrated in the video

CREATE TABLE QUAL (
  QUALID SMALLINT(3) NOT NULL,
  QUALNAME CHAR(14),
  QUALFIELD CHAR(13),
  PRIMARY KEY (QUALID)
);

INSERT INTO QUAL VALUES (1, 'BSC', 'SCIENCE');
INSERT INTO QUAL VALUES (2, 'BCOMM', 'BUSINESS');
INSERT INTO QUAL VALUES (3, 'BENG', 'ENGINEERING');
INSERT INTO QUAL VALUES (4, 'BA', 'HUMANITIES');

-- QUESTION 8
-- Create a table called proj with the following fields :
-- projno numeric 3 long not null
-- pname character 5 long
-- budget numeric 7 long with 2 decimal places

CREATE TABLE PROJ (
  PROJNO SMALLINT(3) NOT NULL,
  PNAME CHAR(5),
  BUDGET INT(7),
  PRIMARY KEY (PROJNO)
);

-- QUESTION 9
-- Insert into proj the following data:
-- 101 ALPHA 96000
-- 102 BETA 82000
-- 103 GAMMA 1 5000

INSERT INTO PROJ VALUES (101, 'ALPHA', 96000);
INSERT INTO PROJ VALUES (102, 'BETA', 82000);
INSERT INTO PROJ VALUES (103, 'GAMMA', 15000);

-- QUESTION 10
-- View the data in proj.

SELECT * FROM PROJ;

-- QUESTION 11
-- Give the emp table a column called projno and describe the table. The proj field
-- should have the same type and size as in the proj table.

ALTER TABLE EMP 
ADD PROJ SMALLINT(3);

-- QUESTION 12
-- Assign everyone in department 20 and every salesman to project 101 and view the
-- emp table.

UPDATE EMP SET PROJ = 101 
WHERE DEPTNO = 20 OR JOB = 'SALESMAN';

-- QUESTION 13
-- Assign everyone else to project 102 and view the emp table.

UPDATE EMP SET PROJ = 102 
WHERE PROJ IS NULL;

-- QUESTION 14
-- List the employee numbers, jobs, department numbers and project name's.

SELECT EMP.EMPNO, EMP.JOB, EMP.DEPTNO, PROJ.PNAME FROM EMP
JOIN PROJ ON PROJ.PROJNO = EMP.PROJ;

-- QUESTION 15
-- Alter the width of the project budget field to 8 places including 2 decimal places.

ALTER TABLE PROJ
MODIFY COLUMN BUDGET FLOAT(8, 2);

-- QUSETION 16
-- Change the budget for project 103 to 105000

UPDATE PROJ SET BUDGET = 105000 
WHERE PROJNO = 103;

-- QUESTION 17
-- View the employee, number, name, department number, department location,
-- project name and project budget

SELECT EMP.EMPNO, EMP.ENAME, EMP.DEPTNO, DEPT.LOC, PROJ.PNAME, PROJ.BUDGET FROM EMP
JOIN DEPT ON DEPT.DEPTNO = EMP.DEPTNO
JOIN PROJ ON PROJ.PROJNO = EMP.PROJ;

-- QUESTION 18
-- Create a view called PERSONNEL which contains employee names, jobs and
-- project names.

CREATE VIEW PERSONNEL AS
SELECT EMP.ENAME, EMP.JOB, PROJ.PNAME FROM EMP
JOIN PROJ ON PROJ.PROJNO = EMP.PROJ;

-- QUESTION 19
-- Using the PERSONNEL view, select the employee names, jobs and project names
-- for all employees who are managers.

SELECT ENAME, JOB, PNAME FROM PERSONNEL
WHERE JOB = 'MANAGER';

-- QUESTION 20
-- Delete the PERSONNEL view

DROP VIEW PERSONNEL;