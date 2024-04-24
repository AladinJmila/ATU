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
CREATE DATABASE IF NOT EXISTS wild_atlantic_way_feedback_hub;
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

