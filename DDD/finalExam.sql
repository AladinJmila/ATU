-- 1 - 30 mintues: Database design
-- 2 - 30 minutes: build and data population
-- 3 - 30 minutes: iterate to perfect design
-- 4 - 30 minutes: Select, Insert, Update, Delete basic
-- 5 - 30 minutes: Views, Procedures, Indexes, Users
-- 6 - 15 minutes: Codds Rules
-- 7 - 15 minutes: Optimize


-- 1 - 30 mintues: Database design
-- Collecting and analyzing tourist reviews!
-- Helps stakeholders tailor serivces and attractions more efficiently
-- Detailed reviews
-- Rating scores
-- Personal anecdotes

-- Review: Feedback, what is working well and what might need imporvment
-- Tourist: Name, Age, Country of Origin, Travel Preferences | segment feedback to understand depgraphics needs and desires -> targeted marketting and service adjustments | travel preference allows suggesting personalized future visits based on enjoyment patterns
-- Attraction: Name, Type, Location, Description, Avr rating (possible trigger here)

-- 2 - 40 minutes: build and data population

-- 3 - 30 minutes: iterate to perfect design
-- CREATE DATABASE IF NOT EXISTS wild_atlantic_way_feedback_hub;
USE wild_atlantic_way_feedback_hub;

DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS visits;
DROP TABLE IF EXISTS tourists;
DROP TABLE IF EXISTS attractions;

START TRANSACTION;

CREATE TABLE IF NOT EXISTS tourists (
  tourist_id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  travel_preferences TEXT NOT NULL,
  origin_country ENUM('Country 1', 'Country 2', 'Country 3') NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  age_group ENUM('< 20', '20-30', '30-40', '40-50', '50-60', '60-70', '70-80', '> 80' ),
  gender ENUM('Male', 'Female', 'Other'),
  phone VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS visits (
  visit_id INT PRIMARY KEY AUTO_INCREMENT,
  tourist_id INT NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  start_location VARCHAR(50) NOT NULL,
  end_location VARCHAR(50) NOT NULL,
  FOREIGN KEY (tourist_id)
    REFERENCES tourists (tourist_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS attractions (
  attraction_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  type VARCHAR(50) NOT NULL,
  location VARCHAR(50) NOT NULL,
  description TEXT NOT NULL,
  average_rating DECIMAL(2, 1) NOT NULL,
  total_reviews INT NOT NULL DEFAULT 0,
  opening_hours VARCHAR(50),
  image_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS reviews (
  review_id INT PRIMARY KEY AUTO_INCREMENT,
  tourist_id INT NOT NULL,
  attraction_id INT NOT NULL,
  feedback TEXT NOT NULL,
  rating TINYINT NOT NULL CHECK (rating BETWEEN 1 AND 5),
  posted_date DATE NOT NULL,
  FOREIGN KEY (tourist_id)
    REFERENCES tourists (tourist_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (attraction_id)
    REFERENCES attractions (attraction_id)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

INSERT INTO tourists (first_name, last_name, travel_preferences, origin_country, email, age_group, gender, phone)
VALUES
  ('John', 'Doe', 'Beach destinations, Cultural experiences', 'Country 1', 'johndoe@example.com', '30-40', 'Male', '1234567890'),
  ('Jane', 'Smith', 'Mountain hiking, Historical sites', 'Country 2', 'janesmith@example.com', '40-50', 'Female', '1987654321');

INSERT INTO visits (tourist_id, start_date, end_date, start_location, end_location)
VALUES
  (1, '2024-04-01', '2024-04-05', 'Galway', 'Cliffs of Moher'),
  (2, '2024-03-15', '2024-03-20', 'Killarney', 'Ring of Kerry');

INSERT INTO attractions (name, type, location, description, average_rating, total_reviews, opening_hours, image_url)
VALUES
  ('Cliffs of Moher', 'Natural Landmark', 'County Clare', 'Iconic sea cliffs offering breathtaking views of the Atlantic Ocean.', 4.5, 1, '9:00 AM - 7:00 PM', 'https://images.ireland.com/media/Images/Clare/49d106fda62a4ab4b41bbc260ddbb29e.jpg'),
  ('Ring of Kerry', 'Scenic Drive', 'County Kerry', 'Picturesque route known for stunning coastal and mountain scenery.', 4.2, 1, 'Open all day', 'https://killarneytour.com/wp-content/uploads/2023/06/ring-of-kerry-history.jpg');

INSERT INTO reviews (tourist_id, attraction_id, feedback, rating, posted_date)
VALUES
  (1, 1, 'Absolutely stunning views! A must-visit.', 5, '2024-04-06'),
  (2, 2, 'Breathtaking landscapes along the Ring of Kerry.', 4, '2024-03-21');

COMMIT;


-- 4 - 30 minutes: Select, Insert, Update, Delete basic
START TRANSACTION;

CREATE OR REPLACE VIEW top_rated_vists AS
SELECT v.visit_id, v.tourist_id, v.start_date, v.end_date, v.start_location, v.end_location,
       r.attraction_id, a.name AS attraction_name, a.location AS attraction_location,
       r.feedback, r.rating, r.posted_date
FROM visits v
JOIN reviews r ON v.tourist_id = r.tourist_id
JOIN attractions a ON r.attraction_id = a.attraction_id
WHERE r.rating >= 4;

CREATE OR REPLACE VIEW low_rated_attraction_tourists AS
SELECT a.location AS attraction_location,
       t.origin_country, t.age_group,
       r.feedback, r.rating, r.posted_date
FROM reviews r
JOIN attractions a ON r.attraction_id = a.attraction_id
JOIN tourists t ON r.tourist_id = t.tourist_id
WHERE r.rating <= 3;

DELIMITER //
-- DROP TRIGGER IF EXISTS attraction_after_review_insert;
CREATE TRIGGER attraction_after_review_insert 
  AFTER INSERT ON reviews
  FOR EACH ROW
BEGIN
  
  UPDATE attractions 
  SET 
    average_rating = (average_rating * (SELECT COUNT(*) FROM reviews WHERE attraction_id = NEW.attraction_id) + New.rating) / (average_rating * (SELECT COUNT(*) FROM reviews WHERE attraction_id = NEW.attraction_id) + New.rating) + 1,
    total_reviews = total_reviews + 1
    WHERE attraction_id = NEW.attraction_id;
END //

-- DROP TRIGGER IF EXISTS attraction_after_review_delete;
CREATE TRIGGER attraction_after_review_delete 
  AFTER DELETE ON reviews
  FOR EACH ROW
BEGIN
  UPDATE attractions 
  SET 
    average_rating = (average_rating * (SELECT COUNT(*) FROM reviews WHERE attraction_id = OLD.attraction_id) - OLD.rating) / (average_rating * (SELECT COUNT(*) FROM reviews WHERE attraction_id = OLD.attraction_id) + OLD.rating) - 1,
    total_reviews = total_reviews - 1
    WHERE attraction_id = OLD.attraction_id;
END //

DELIMITER ;

SELECT average_rating, total_reviews FROM attractions WHERE name = 'Cliffs of Moher';

INSERT INTO reviews (tourist_id, attraction_id, feedback, rating, posted_date)
  VALUES
  (1, 1, 'Just leaving a low rating', 1, '2024-04-09');

DELETE FROM reviews WHERE review_id = 3;

UPDATE reviews
SET feedback = 'New feedback',
    rating = 4,
    posted_date = '2024-04-22'
WHERE review_id = 1;

COMMIT;


U
-- 5 - 30 minutes: Views, Procedures, Indexes, Users
-- 6 - 15 minutes: Codds Rules
-- 7 - 15 minutes: Optimize

-- Relational schema:

-- Test Data 2,rows for each table

-- Queries:
-- Select
-- Insert
-- Update
-- Delete

-- Codds rules:

-- RULE 2: The Guaranteed Access Rule
SELECT age_group, origin_country FROM tourists WHERE tourist_id = 1;

-- RULE 5: The Comprehensive Data Sub Language
CREATE USER joan@'admin.wawfeedbackhub.ie' IDENTIFIED BY '1234';

GRANT ALL
ON wild_atlantic_way_feedback_hub.*
TO rohin@'admin.bestvets.ie';

-- RULE 7: CRUD Operation modifying more than one row at a time
DELETE FROM reviews WHERE rating = 1;

