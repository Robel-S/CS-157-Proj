CREATE TABLE IF NOT EXISTS movie(
movieID INTEGER UNIQUE NOT NULL PRIMARY KEY,
title VARCHAR(50) CHECK(length(title)<= 50) NOT NULL,
genre VARCHAR(20) CHECK(length(genre)<= 20) NOT NULL,
stock INTEGER NOT NULL,
avgRating DECIMAL (1,1) NOT NULL);

CREATE TABLE IF NOT EXISTS customer(
username VARCHAR(10) CHECK(length(username)<= 10) UNIQUE NOT NULL PRIMARY KEY,
password VARCHAR(50) CHECK(length(password)<= 20) NOT NULL,
name VARCHAR(20)  CHECK(length(name)<= 20) NOT NULL,
age INTEGER NOT NULL);

CREATE TABLE IF NOT EXISTS rental(
username VARCHAR(10) CHECK(length(username)<= 10) NOT NULL,
movieID INTEGER NOT NULL, 
dueDate VARCHAR(20) CHECK(length(dueDate)<= 20) NOT NULL,
PRIMARY KEY (username, movieID),
FOREIGN KEY(username) REFERENCES customer(username),
FOREIGN KEY(movieID) REFERENCES movie(movieID));

CREATE TABLE IF NOT EXISTS rating(
username VARCHAR(10) CHECK(length(username)<= 10) NOT NULL,
movieID INTEGER NOT NULL,
rating DECIMAL(1,1) NOT NULL,
PRIMARY KEY (username, movieID),
FOREIGN KEY(username) REFERENCES customer(username),
FOREIGN KEY(movieID) REFERENCES movie(movieID));