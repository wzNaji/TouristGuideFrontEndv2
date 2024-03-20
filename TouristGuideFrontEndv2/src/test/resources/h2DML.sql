-- Insert predefined cities
INSERT INTO Cities (city_name) VALUES
('Copenhagen'),
('Odense'),
('Aarhus'),
('Haderslev');

-- Insert predefined tags
INSERT INTO Tags (tag_name) VALUES
('Family friendly'),
('Free'),
('Outdoor'),
('Historic');

-- Insert sample attractions
INSERT INTO Attractions (name, description, city_id) VALUES
('The Little Mermaid', 'Iconic statue depicting a mermaid, based on the fairy tale by Hans Christian Andersen.', 1),
('Odense Zoo', 'A diverse zoo with a wide variety of species from around the world.', 2),
('Aarhus Art Museum', 'Contemporary art museum with a vast collection of modern art pieces.', 3),
('Haderslev Cathedral', 'Historic cathedral dating back to the 13th century, a masterpiece of Gothic architecture.', 4);

-- Insert associations between attractions and tags
INSERT INTO AttractionTags (attraction_id, tag_id) VALUES
(1, 4), -- The Little Mermaid is Historic
(2, 1), -- Odense Zoo is Family friendly
(2, 3), -- Odense Zoo is also Outdoor
(3, 4), -- Aarhus Art Museum is Historic
(4, 4); -- Haderslev Cathedral is Historic
