DROP DATABASE IF EXISTS OpenMeet;
CREATE DATABASE OpenMeet;
USE OpenMeet;
/*SET sql_mode = "";
SET GLOBAL sql_mode = "";*/

/* Meeter Section */

CREATE TABLE Meeter (
 
    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `email` VARCHAR(320) UNIQUE, 
    `name` VARCHAR(35) NOT NULL, 
    `surname` VARCHAR(35) NOT NULL, 
    `password` CHAR(64) NOT NULL, 
	`biography` VARCHAR(255), 
    `birthDate` DATE NOT NULL 
);

CREATE TABLE Rating (

   `id` INT PRIMARY KEY AUTO_INCREMENT, 
	`meeterRater` INT,
    `meeterRated` INT,
	`type` BOOL NOT NULL, 
    `creationDate` DATETIME NOT NULL, 
    
    FOREIGN KEY (`meeterRater`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (`meeterRated`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE Report (

   `id` INT PRIMARY KEY AUTO_INCREMENT, 
	`meeterReporter` INT,
    `meeterReported` INT,
	`reason` VARCHAR(50) NOT NULL, 
    `creationDate` DATETIME NOT NULL, 
    
    FOREIGN KEY (`meeterReporter`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (`meeterReported`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE Interest (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `description` VARCHAR(12) NOT NULL 
); 

CREATE TABLE Meeter_Interest (

    `interestId` INT, 
    `meeterId` INT, 

    FOREIGN KEY (`interestId`) REFERENCES Interest (`id`) ON UPDATE CASCADE ON DELETE NO ACTION, 
    FOREIGN KEY (`meeterId`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION, 
    PRIMARY KEY (`interestId`, `meeterId`)
); 


CREATE TABLE Image (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `path` VARCHAR(2048) NOT NULL, 
    `meeterid` INT, 

    FOREIGN KEY (`meeterid`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Message (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `text` TEXT NOT NULL, 
    `sentTime` DATETIME NOT NULL, 
    `deliveredTime` DATETIME, 
    `readTime` DATETIME, 
    `meeterSender` INT, 
    `meeterReceiver` INT, 

    FOREIGN KEY (`meeterSender`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION, 
    FOREIGN KEY (`meeterReceiver`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION
); 

/* Moderator Section */

CREATE TABLE Moderator (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `email` VARCHAR(320) UNIQUE, 
    `name` VARCHAR(35) NOT NULL, 
    `surname` VARCHAR(35) NOT NULL, 
    `password` CHAR(64) NOT NULL, 
	`profilePic` VARCHAR(2048)
); 

CREATE TABLE Ban (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `moderatorId` INT, 
    `description` VARCHAR(255) NOT NULL,
    `startTime` DATETIME NOT NULL, 
    `endTime` DATETIME, 
    `meeterId` INT, 

    FOREIGN KEY (`moderatorId`) REFERENCES Moderator (`id`) ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (`meeterId`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION
); 