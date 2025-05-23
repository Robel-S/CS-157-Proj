INSERT OR IGNORE INTO movie (movieID, title, genre, stock, avgRating)
VALUES (123456, 'The Dark Knight', 'Action', 3, -1.0),
(234567, 'Lord of the Rings', 'Fantasy', 2, -1.0),
(345678, 'La La Land', 'Romance', 3, -1.0),
(456789, '3 Idiots', 'Comedy', 0, -1.0),
(567891, 'Parasite', 'Drama', 3, -1.0),
(678912, 'Sinners', 'Horror', 5, -1.0),
(789123, 'Inception', 'Action', 3, -1.0),
(891234, 'Your Name', 'Romance', 2, -1.0),
(912345, 'Spirited Away', 'Fantasy', 3, -1.0),
(212345, 'Alien', 'Horror', 3, -1.0),
(223456, 'Goodfellas', 'Drama', 2, -1.0),
(234567, 'Gone with the Wind', 'Romance', 4, -1.0),
(245678, 'Ted', 'Comedy', 0, -1.0),
(256789, 'The Matrix', 'Action', 0, -1.0),
(267890, 'The Shining', 'Horror', 1, -1.0);

INSERT OR IGNORE INTO customer(username, password, name, age)
Values('user1', 'pass1', 'John', 19),
('user2', 'pass2', 'Jake', 35),
('user3', 'pass3', 'Candice', 21),
('user4', 'pass4', 'Bella', 40),
('user5', 'pass5', 'James', 70),
('user6', 'pass6', 'Blake', 18),
('user7', 'pass7', 'John', 43),
('user8', 'pass8', 'Natalie', 30),
('user9', 'pass9', 'Tiffany', 24),
('user10', 'pass10', 'Zach', 56),
('user11', 'pass11', 'Cody', 27),
('user12', 'pass12', 'Amelia', 65),
('user13', 'pass13', 'Mary', 99),
('user14', 'pass14', 'Vivian', 24),
('user15', 'pass15', 'Ethan', 46);

INSERT OR IGNORE INTO rental(username, movieID, dueDate)
VALUES('user1', 456789, '2025-05-10'),
('user1', 245678, '2025-05-13'),
('user1', 256789, '2025-05-11'),
('user1', 223456, '2025-05-11'),
('user1', 345678, '2025-05-12'),
('user2', 456789, '2025-05-10'),
('user2', 245678, '2025-05-10'),
('user2', 256789, '2025-05-13'),
('user2', 212345, '2025-05-13'),
('user2', 567891, '2025-05-13'),
('user3', 456789, '2025-05-11'),
('user3', 245678, '2025-05-11'),
('user3', 256789, '2025-05-11'),
('user3', 267890, '2025-05-11'),
('user3', 912345, '2025-05-11');

INSERT OR IGNORE INTO rating(username, movieID, rating)
VALUES('user1', 456789, 2.5),
('user1', 245678, 3.0),
('user1', 256789, 1.0),
('user1', 223456, 4.5),
('user1', 345678, 5.0),
('user2', 456789, 4.5),
('user2', 245678, 3.5),
('user2', 256789, 2.0),
('user2', 212345, 1.5),
('user2', 567891, 0.5),
('user3', 456789, 0.0),
('user3', 245678, 0.5),
('user3', 256789, 0.5),
('user3', 267890, 1.0),
('user3', 912345, 1.5);

