-- Drop tables if they exist to prevent errors on subsequent runs
DROP TABLE IF EXISTS AttractionTags;
DROP TABLE IF EXISTS Attractions;
DROP TABLE IF EXISTS Cities;
DROP TABLE IF EXISTS Tags;

-- Create Cities table
CREATE TABLE Cities (
city_id INT AUTO_INCREMENT PRIMARY KEY,
city_name VARCHAR(50) UNIQUE NOT NULL
);

-- Create Attractions table with a reference to Cities
CREATE TABLE Attractions (
attraction_id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
description TEXT NOT NULL,
city_id INT NOT NULL,
FOREIGN KEY (city_id) REFERENCES Cities(city_id)
);

-- Create Tags table
CREATE TABLE Tags (
tag_id INT AUTO_INCREMENT PRIMARY KEY,
tag_name VARCHAR(50) UNIQUE NOT NULL
);

-- Create AttractionsTags junction table to handle the many-to-many relationship
CREATE TABLE AttractionTags (
attraction_id INT NOT NULL,
tag_id INT NOT NULL,
PRIMARY KEY (attraction_id, tag_id),
FOREIGN KEY (attraction_id) REFERENCES Attractions(attraction_id) ON DELETE CASCADE,
FOREIGN KEY (tag_id) REFERENCES Tags(tag_id) ON DELETE CASCADE
);
