---- Insert Users
INSERT INTO user_data (user_name, api_key) VALUES
('john_doe', 'abc12345'),
('jane_smith', 'xyz98765'),
('sam_jones', 'qwerty1234'),
('maria_williams', 'password123'),
('david_brown', 'key987654');
--
--
---- Insert MoodData
INSERT INTO mood_data (user_id, lat, long, mood, time_stamp, image) VALUES
(1, '40.7128', '-74.0060', 'happy', '2025-02-05', 'https://s3.amazonaws.com/images/john_mood1.jpg'),
(1, '40.7129', '-74.0059', 'sad', '2025-02-06', 'https://s3.amazonaws.com/images/john_mood2.jpg'),
(2, '34.0522', '-118.2437', 'excited', '2025-02-05', 'https://s3.amazonaws.com/images/jane_mood1.jpg'),
(2, '34.0523', '-118.2436', 'relaxed', '2025-02-06', 'https://s3.amazonaws.com/images/jane_mood2.jpg'),
(3, '51.5074', '-0.1278', 'angry', '2025-02-05', 'https://s3.amazonaws.com/images/sam_mood1.jpg'),
(3, '51.5075', '-0.1279', 'anxious', '2025-02-06', 'https://s3.amazonaws.com/images/sam_mood2.jpg'),
(4, '48.8566', '2.3522', 'joyful', '2025-02-05', 'https://s3.amazonaws.com/images/maria_mood1.jpg'),
(4, '48.8567', '2.3523', 'neutral', '2025-02-06', 'https://s3.amazonaws.com/images/maria_mood2.jpg'),
(5, '35.6895', '139.6917', 'surprised', '2025-02-05', 'https://s3.amazonaws.com/images/david_mood1.jpg'),
(5, '35.6896', '139.6918', 'content', '2025-02-06', 'https://s3.amazonaws.com/images/david_mood2.jpg');

CREATE TABLE bedrock_kb_no_chunking (id uuid PRIMARY KEY, chunks text, metadata json);
