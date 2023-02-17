/*remote_usr ha i permessi solo su OpenMeet
DROP DATABASE IF EXISTS OpenMeet;
CREATE DATABASE OpenMeet;*/
USE OpenMeet;
/*SET sql_mode = "";
SET GLOBAL sql_mode = "";*/

/* Meeter Section */

Select * from Meeter;

CREATE TABLE Meeter (
 
    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `email` VARCHAR(320) UNIQUE NOT NULL, 
    `meeterName` VARCHAR(35) NOT NULL, 
    `meeterSurname` VARCHAR(35) NOT NULL, 
    `gender` CHAR(1),
    `searchingGender` CHAR(1),
    `city` VARCHAR(50),
    `pwd` CHAR(40) NOT NULL, /* SHA1(pwd) = 160 bits / 4 = 40 chars*/
	`biography` VARCHAR(255), 
    `birthdate` DATE NOT NULL,
    `publicKey` CHAR(40) /*Key Pair da 1024 bit*/
);

CREATE TABLE Rating (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
	`meeterRater` INT NOT NULL,
    `meeterRated` INT NOT NULL,
	`type` BOOL NOT NULL, 
    `creationDate` DATETIME NOT NULL, 
    
    FOREIGN KEY (`meeterRater`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (`meeterRated`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE Report (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
	`meeterReporter` INT NOT NULL,
    `meeterReported` INT NOT NULL,
	`reason` VARCHAR(50) NOT NULL, 
    `isArchived` BOOL NOT NULL DEFAULT FALSE,
    `creationDate` DATETIME NOT NULL, 
    
    FOREIGN KEY (`meeterReporter`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (`meeterReported`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE Interest (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `description` VARCHAR(25) NOT NULL 
); 

CREATE TABLE Meeter_Interest (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `interestId` INT, 
    `meeterId` INT, 

    FOREIGN KEY (`interestId`) REFERENCES Interest (`id`) ON UPDATE CASCADE ON DELETE NO ACTION, 
    FOREIGN KEY (`meeterId`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION 
); 


CREATE TABLE Image (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `path` VARCHAR(2048) NOT NULL, 
    `meeterid` INT NOT NULL, 

    FOREIGN KEY (`meeterid`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Message (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `text` TEXT NOT NULL, 
    `sentTime` DATETIME NOT NULL, 
    `deliveredTime` DATETIME, 
    `readTime` DATETIME, 
    `meeterSender` INT NOT NULL, 
    `meeterReceiver` INT NOT NULL, 

    FOREIGN KEY (`meeterSender`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION, 
    FOREIGN KEY (`meeterReceiver`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION
); 

/* Moderator Section */

CREATE TABLE Moderator (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `email` VARCHAR(320) UNIQUE NOT NULL, 
    `moderatorName` VARCHAR(35) NOT NULL, 
    `moderatorSurname` VARCHAR(35) NOT NULL, 
	`pwd` CHAR(40) NOT NULL, /* SHA1(pwd) = 160 bits / 4 = 40 chars*/
	`profilePic` VARCHAR(2048)
); 

CREATE TABLE Ban (

    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `moderatorId` INT NOT NULL, 
    `description` VARCHAR(255) NOT NULL,
    `startTime` DATETIME NOT NULL, 
    `endTime` DATETIME, 
    `meeterId` INT NOT NULL, 

    FOREIGN KEY (`moderatorId`) REFERENCES Moderator (`id`) ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (`meeterId`) REFERENCES Meeter (`id`) ON UPDATE CASCADE ON DELETE NO ACTION
); 
