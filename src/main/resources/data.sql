-- For distance calculations
CREATE ALIAS IF NOT EXISTS H2GIS_SPATIAL FOR "org.h2gis.functions.factory.H2GISFunctions.load";
CALL H2GIS_SPATIAL();

---- Insert Users
INSERT INTO user_data (user_name, api_key) VALUES
('john_doe', 'abc12345'),
('jane_smith', 'xyz98765'),
('sam_jones', 'qwerty1234'),
('maria_williams', 'password123'),
('david_brown', 'key987654');
--
--
-- Insert data with latitude and longitude as separate columns
INSERT INTO mood_data (user_id, latitude, longitude, mood, time_stamp)
VALUES
(1, 40.712776, -74.005974, 'happy', '2025-02-06 10:00:00'),  -- New York, User 1
(1, 34.052235, -118.243683, 'sad', '2025-02-06 10:15:00'),    -- Los Angeles, User 2
(2, 51.507351, -0.127758, 'neutral', '2025-02-06 10:30:00'), -- London, User 3
(2, 48.856613, 2.352222, 'happy', '2025-02-06 10:45:00'),    -- Paris, User 4
(3, 40.730610, -73.935242, 'sad', '2025-02-06 11:00:00'),    -- New York, User 5
(3, 34.052235, -118.243683, 'neutral', '2025-02-06 11:15:00'), -- Los Angeles, User 6
(4, 41.902782, 12.496366, 'happy', '2025-02-06 11:30:00'),    -- Rome, User 7
(4, 52.367573, 4.904138, 'sad', '2025-02-06 11:45:00'),      -- Amsterdam, User 8
(5, 40.712776, -74.005974, 'neutral', '2025-02-06 12:00:00'), -- New York, User 9
(5, 34.052235, -118.243683, 'happy', '2025-02-06 12:15:00');  -- Los Angeles, User 10


--create indexes required
--CREATE SPATIAL INDEX hotel_location_idx ON mood_data(location); (h2 doesn't support can be used on other dbs like psql)
--Above index improves the performance of the application

