-- Autori: Francesco Granozio, Angelo Nazzaro ©

DROP DATABASE IF EXISTS OpenMeet;
CREATE DATABASE OpenMeet;
USE OpenMeet;
/*SET sql_mode = "";
SET GLOBAL sql_mode = "";*/

CREATE TABLE Meeter (
 
	`email` VARCHAR(320) PRIMARY KEY,
    `name` VARCHAR(35) NOT NULL, 
    `surname` VARCHAR(35) NOT NULL, 
    `password` CHAR(64) NOT NULL, 
	`biography` VARCHAR(255), 
    `birthDate` DATE NOT NULL 
	
);

CREATE TABLE Valutazione (

	`meeterRater` VARCHAR(320),
    `meeterRated` VARCHAR(320),
	`type` BOOL NOT NULL, 
    `creationDate` DATETIME NOT NULL, 
    
    FOREIGN KEY (`meeterRater`) REFERENCES Meeter (`email`) ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (`meeterRated`) REFERENCES Meeter (`email`) ON UPDATE CASCADE ON DELETE NO ACTION,
    PRIMARY KEY (`meeterRater`, `meeterRated`)
);

CREATE TABLE Segnalazione (

	`meeterReporter` VARCHAR(320),
    `meeterReported` VARCHAR(320),
	`reason` VARCHAR(50) NOT NULL, 
    `creationDate` DATETIME NOT NULL, 
    
    FOREIGN KEY (`meeterReporter`) REFERENCES Meeter (`email`) ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (`meeterReported`) REFERENCES Meeter (`email`) ON UPDATE CASCADE ON DELETE NO ACTION,
    PRIMARY KEY (`meeterReporter`, `meeterReported`)
);