CREATE DATABASE IF NOT EXISTS `petclinic`;

USE `petclinic`;

DROP TABLE IF EXISTS `visits`;
DROP TABLE IF EXISTS `vet_specialties`;
DROP TABLE IF EXISTS `consultation_tickets`;
DROP TABLE IF EXISTS `consultations`;
DROP TABLE IF EXISTS `entity_sequence`;
DROP TABLE IF EXISTS `vets`;
DROP TABLE IF EXISTS `pets`;
DROP TABLE IF EXISTS `owners`;
DROP TABLE IF EXISTS `clinics`;
DROP TABLE IF EXISTS `clinic_owners`;
DROP TABLE IF EXISTS `appusers`;
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `specialties`;
DROP TABLE IF EXISTS `types`;


CREATE TABLE `authorities` (
  `id` int NOT NULL,
  `authority` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `authorities` WRITE;
INSERT INTO `authorities` VALUES
(1,'ADMIN'),
(2,'CLINIC_OWNER'),
(3,'OWNER'),
(4,'VET');
UNLOCK TABLES;


CREATE TABLE `specialties` (
  `id` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `specialties` WRITE;
INSERT INTO `specialties` VALUES
(1,'radiology'),
(2,'surgery'),
(3,'dentistry');
UNLOCK TABLES;


CREATE TABLE `types` (
  `id` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `types` WRITE;
INSERT INTO `types` VALUES
(1,'cat'),
(2,'dog'),
(3,'lizard'),
(4,'snake'),
(5,'bird'),
(6,'hamster'),
(7,'turtle');
UNLOCK TABLES;


CREATE TABLE `appusers` (
  `authority` int NOT NULL,
  `id` int NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fxfryr8xvwd9tgo3e2gk58pxv` (`username`),
  KEY `FKgqebgjja4fdf7rww08icgsqmh` (`authority`),
  CONSTRAINT `FKgqebgjja4fdf7rww08icgsqmh` FOREIGN KEY (`authority`) REFERENCES `authorities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `appusers` WRITE;
INSERT INTO `appusers` VALUES
(1,1,'$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','admin1'),
(2,2,'$2a$10$t.I/C4cjUdUWzqlFlSddLeh9SbZ6d8wR7mdbeIRghT355/KRKZPAi','clinicOwner1'),
(2,3,'$2a$10$t.I/C4cjUdUWzqlFlSddLeh9SbZ6d8wR7mdbeIRghT355/KRKZPAi','clinicOwner2'),
(3,4,'$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e','owner1'),
(3,5,'$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e','owner2'),
(3,6,'$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e','owner3'),
(3,7,'$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e','owner4'),
(3,8,'$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e','owner5'),
(3,9,'$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e','owner6'),
(3,10,'$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e','owner7'),
(3,11,'$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e','owner8'),
(3,12,'$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e','owner9'),
(3,13,'$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e','owner10'),
(4,14,'$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.','vet1'),
(4,15,'$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.','vet2'),
(4,16,'$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.','vet3'),
(4,17,'$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.','vet4'),
(4,18,'$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.','vet5'),
(4,19,'$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.','vet6');
UNLOCK TABLES;



CREATE TABLE `clinic_owners` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_appbkgostmknve28hwva63h18` (`user_id`),
  CONSTRAINT `FKcbs34rs1txgav9j71q7lr14wr` FOREIGN KEY (`user_id`) REFERENCES `appusers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `clinic_owners` WRITE;
INSERT INTO `clinic_owners` VALUES
(1,2,'John','Doe'),
(2,3,'Jane','Doe');
UNLOCK TABLES;


CREATE TABLE `clinics` (
  `clinic_owner` int DEFAULT NULL,
  `id` int NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `plan` enum('BASIC','GOLD','PLATINUM') NOT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfo6i4owjm1gqfwal98nwgt7ne` (`clinic_owner`),
  CONSTRAINT `FKfo6i4owjm1gqfwal98nwgt7ne` FOREIGN KEY (`clinic_owner`) REFERENCES `clinic_owners` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `clinics` WRITE;
INSERT INTO `clinics` VALUES
(1,1,'Av. Palmera, 26','Clinic 1','PLATINUM','955684230'),
(2,2,'Av. Torneo, 52','Clinic 2','GOLD','955634232'),
(2,3,'Av. Reina Mercedes, 70','Clinic 3','BASIC','955382238');
UNLOCK TABLES;


CREATE TABLE `owners` (
  `clinic` int DEFAULT NULL,
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_f5l871r0yr9dyilb3ls5p48os` (`user_id`),
  KEY `FK3tegy5hpqm07u36bpi70uqvrl` (`clinic`),
  CONSTRAINT `FK3tegy5hpqm07u36bpi70uqvrl` FOREIGN KEY (`clinic`) REFERENCES `clinics` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKivtnuuio2xfaga7bxv7o4oj36` FOREIGN KEY (`user_id`) REFERENCES `appusers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `owners` WRITE;
INSERT INTO `owners` VALUES
(1,1,4,'110 W. Liberty St.','Sevilla','George','Franklin','608555103'),
(1,2,5,'638 Cardinal Ave.','Sevilla','Betty','Davis','608555174'),
(1,3,6,'2693 Commerce St.','Sevilla','Eduardo','Rodriquez','608558763'),
(2,4,7,'563 Friendly St.','Sevilla','Harold','Davis','608555319'),
(2,5,8,'2387 S. Fair Way','Sevilla','Peter','McTavish','608555765'),
(2,6,9,'105 N. Lake St.','Badajoz','Jean','Coleman','608555264'),
(3,7,10,'1450 Oak Blvd.','Badajoz','Jeff','Black','608555538'),
(3,8,11,'345 Maple St.','Badajoz','Maria','Escobito','608557683'),
(3,9,12,'2749 Blackhawk Trail','CÃ¡diz','David','Schroeder','685559435'),
(3,10,13,'2335 Independence La.','CÃ¡diz','Carlos','Estaban','685555487');
UNLOCK TABLES;



CREATE TABLE `pets` (
  `birth_date` date DEFAULT NULL,
  `id` int NOT NULL,
  `owner_id` int DEFAULT NULL,
  `type_id` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6teg4kcjcnjhduguft56wcfoa` (`owner_id`),
  KEY `FKtmmh1tq8pah5vxf8kuqqplo4p` (`type_id`),
  CONSTRAINT `FK6teg4kcjcnjhduguft56wcfoa` FOREIGN KEY (`owner_id`) REFERENCES `owners` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKtmmh1tq8pah5vxf8kuqqplo4p` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `pets` WRITE;
INSERT INTO `pets` VALUES
('2010-09-07',1,1,1,'Leo'),
('2012-08-06',2,2,6,'Basil'),
('2011-04-17',3,3,2,'Rosy'),
('2010-03-07',4,3,2,'Jewel'),
('2010-11-30',5,4,3,'Iggy'),
('2010-01-20',6,5,4,'George'),
('2012-09-04',7,6,1,'Samantha'),
('2012-09-04',8,6,1,'Max'),
('2011-08-06',9,7,5,'Lucky'),
('2007-02-24',10,8,2,'Mulligan'),
('2010-03-09',11,9,5,'Freddy'),
('2010-06-24',12,10,2,'Lucky'),
('2012-06-08',13,10,1,'Sly');
UNLOCK TABLES;



CREATE TABLE `vets` (
  `clinic` int DEFAULT NULL,
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_a08qu59euub9njcbl4pk0d3ox` (`user_id`),
  KEY `FK2p0tbc3ckxffr5b9in4umxfy8` (`clinic`),
  CONSTRAINT `FK2p0tbc3ckxffr5b9in4umxfy8` FOREIGN KEY (`clinic`) REFERENCES `clinics` (`id`),
  CONSTRAINT `FK5nwjcgpnlqwu01x2qm0ixk9n7` FOREIGN KEY (`user_id`) REFERENCES `appusers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `vets` WRITE;
INSERT INTO `vets` VALUES
(1,1,14,'Sevilla','James','Carter'),
(1,2,15,'Sevilla','Helen','Leary'),
(2,3,16,'Sevilla','Linda','Douglas'),
(2,4,17,'Badajoz','Rafael','Ortega'),
(3,5,18,'Badajoz','Henry','Stevens'),
(3,6,19,'Cádiz','Sharon','Jenkins');
UNLOCK TABLES;



CREATE TABLE `consultations` (
  `id` int NOT NULL,
  `is_clinic_comment` bit(1) NOT NULL,
  `owner_id` int NOT NULL,
  `pet_id` int DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `status` enum('ANSWERED','CLOSED','PENDING') NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3l5xlwv6xtgcgrcdm70g8hb37` (`owner_id`),
  KEY `FK1u4ifl4aogb549rkac3xqbm60` (`pet_id`),
  CONSTRAINT `FK1u4ifl4aogb549rkac3xqbm60` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`),
  CONSTRAINT `FK3l5xlwv6xtgcgrcdm70g8hb37` FOREIGN KEY (`owner_id`) REFERENCES `owners` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `consultations` WRITE;
INSERT INTO `consultations` VALUES
(1,_binary '\0',1,1,'2023-01-04 17:30:00.000000','ANSWERED','Consultation about vaccines'),
(2,_binary '\0',1,1,'2022-01-02 19:30:00.000000','PENDING','My dog gets really nervous'),
(3,_binary '\0',2,2,'2023-04-11 11:20:00.000000','PENDING','My cat does not eat'),
(4,_binary '\0',2,2,'2023-02-24 10:30:00.000000','CLOSED','My lovebird does not sing'),
(5,_binary '\0',10,12,'2023-04-11 11:20:00.000000','PENDING','My snake has layed eggs');
UNLOCK TABLES;



CREATE TABLE `consultation_tickets` (
  `consultation_id` int NOT NULL,
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt3l8ba6gpdfikmpsh5wx76e6h` (`consultation_id`),
  KEY `FKbp301erbrnmul0mep92t4nohg` (`user_id`),
  CONSTRAINT `FKbp301erbrnmul0mep92t4nohg` FOREIGN KEY (`user_id`) REFERENCES `appusers` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKt3l8ba6gpdfikmpsh5wx76e6h` FOREIGN KEY (`consultation_id`) REFERENCES `consultations` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `consultation_tickets` WRITE;
INSERT INTO `consultation_tickets` VALUES
(1,1,4,'2023-01-04 17:32:00.000000','What vaccine should my dog receive?'),
(1,2,14,'2023-01-04 17:36:00.000000','Rabies'' one.'),
(2,3,4,'2022-01-02 19:31:00.000000','My dog gets really nervous during football matches. What should I do?'),
(2,4,4,'2022-01-02 19:33:00.000000','It also happens with tennis matches.'),
(3,5,5,'2023-04-11 11:30:00.000000','My cat han''t been eating his fodder.'),
(3,6,15,'2023-04-11 15:20:00.000000','Try to give him some tuna to check if he eats that.'),
(4,7,5,'2023-02-24 12:30:00.000000','My lovebird doesn''t sing as my neighbour''s one.'),
(4,8,16,'2023-02-24 18:30:00.000000','Lovebirds do not sing.');
UNLOCK TABLES;



CREATE TABLE `entity_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `entity_sequence` WRITE;
INSERT INTO `entity_sequence` VALUES (100);
UNLOCK TABLES;



CREATE TABLE `vet_specialties` (
  `specialty_id` int NOT NULL,
  `vet_id` int NOT NULL,
  UNIQUE KEY `UK5vkcw8m2n1pifb4h4meuv8p3a` (`specialty_id`,`vet_id`),
  KEY `FKby1c0fbaa0byaifi63vt18sx9` (`vet_id`),
  CONSTRAINT `FK35uiboyrpfn1bndrr5jorcj0m` FOREIGN KEY (`specialty_id`) REFERENCES `specialties` (`id`),
  CONSTRAINT `FKby1c0fbaa0byaifi63vt18sx9` FOREIGN KEY (`vet_id`) REFERENCES `vets` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `vet_specialties` WRITE;
INSERT INTO `vet_specialties` VALUES 
(1,2),
(2,3),
(3,3),
(2,4),
(1,5);
UNLOCK TABLES;


CREATE TABLE `visits` (
  `id` int NOT NULL,
  `pet_id` int DEFAULT NULL,
  `vet_id` int NOT NULL,
  `visit_date_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6jcifhlqqlsfseu67utlouauy` (`pet_id`),
  KEY `FK8036qgt84d8h5cckxrj952qoe` (`vet_id`),
  CONSTRAINT `FK6jcifhlqqlsfseu67utlouauy` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK8036qgt84d8h5cckxrj952qoe` FOREIGN KEY (`vet_id`) REFERENCES `vets` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `visits` WRITE;
INSERT INTO `visits` VALUES
(1,7,4,'2013-01-01 13:00:00.000000','rabies shot'),
(2,8,5,'2013-01-02 15:30:00.000000','rabies shot'),
(3,8,5,'2013-01-03 09:45:00.000000','neutered'),
(4,7,4,'2013-01-04 17:30:00.000000','spayed'),
(5,1,1,'2013-01-01 13:00:00.000000','rabies shot'),
(6,1,1,'2020-01-02 15:30:00.000000','rabies shot'),
(7,1,1,'2020-01-02 15:30:00.000000','rabies shot'),
(8,2,2,'2013-01-03 09:45:00.000000','neutered'),
(9,3,3,'2013-01-04 17:30:00.000000','spayed');
UNLOCK TABLES;
