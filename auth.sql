CREATE DATABASE  IF NOT EXISTS `authorization` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `authorization`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: authorization
-- ------------------------------------------------------
-- Server version	5.5.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `question_map`
--

DROP TABLE IF EXISTS `question_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_map` (
  `questionMapId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `questionId` int(11) NOT NULL,
  `answer` varchar(45) NOT NULL,
  PRIMARY KEY (`questionMapId`),
  KEY `fk_question_key_idx` (`questionId`),
  KEY `fk_user_question_map_key_idx` (`userId`),
  CONSTRAINT `fk_question_map_key` FOREIGN KEY (`questionId`) REFERENCES `questions` (`questionId`),
  CONSTRAINT `fk_user_question_map_key` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_map`
--

LOCK TABLES `question_map` WRITE;
/*!40000 ALTER TABLE `question_map` DISABLE KEYS */;
INSERT INTO `question_map` VALUES (1,4,1,'Ajmer');
/*!40000 ALTER TABLE `question_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `questionId` int(11) NOT NULL AUTO_INCREMENT,
  `query` varchar(255) NOT NULL,
  `addedBy` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`questionId`),
  UNIQUE KEY `query_UNIQUE` (`query`),
  KEY `fk_user_custom_question_key_idx` (`addedBy`),
  CONSTRAINT `fk_user_custom_question_key` FOREIGN KEY (`addedBy`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='A set of security questions.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'where did your parents meet first time?',1,1);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `roleId` tinyint(2) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `alias` varchar(45) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'administrator','Administrator',1),(2,'user','Premium User',1),(3,'guest','Free User',1);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_map`
--

DROP TABLE IF EXISTS `service_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_map` (
  `serviceMapId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `serviceId` int(11) NOT NULL,
  PRIMARY KEY (`serviceMapId`),
  KEY `fk_service_key` (`serviceId`),
  KEY `fk_user_service_map_key` (`userId`),
  CONSTRAINT `fk_service_key` FOREIGN KEY (`serviceId`) REFERENCES `services` (`serviceId`),
  CONSTRAINT `fk_user_service_map_key` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_map`
--

LOCK TABLES `service_map` WRITE;
/*!40000 ALTER TABLE `service_map` DISABLE KEYS */;
INSERT INTO `service_map` VALUES (1,1,1),(2,1,2),(3,2,2),(4,3,1);
/*!40000 ALTER TABLE `service_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services` (
  `serviceId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `alias` varchar(45) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Status implies whether service is running or organization has been stopped this service.\nHere status 1 will indicate service is running.',
  PRIMARY KEY (`serviceId`),
  UNIQUE KEY `unique_service_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,'read','Content Reading',1),(2,'update','Updating Content',1),(3,'create','Creating New Content',1),(4,'delete','Deleting Anything',1);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessions` (
  `sessionId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `device` varchar(45) DEFAULT NULL COMMENT 'Device can be stored with an alias so this field can be used to insert device title/alias',
  `type` varchar(45) DEFAULT NULL COMMENT 'It is for category of device. Device can be a mobile device or a computer.',
  `location` varchar(45) DEFAULT NULL COMMENT 'It can be MAC address or the location of the device',
  `imei` varchar(20) DEFAULT NULL,
  `gcm` text,
  `token` text,
  `startedOn` datetime NOT NULL COMMENT 'session start timestamp',
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`sessionId`),
  KEY `fk_user_session_key` (`userId`),
  CONSTRAINT `fk_user_session_key` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES (1,2,'Apple Iphone 5c','mobile','new delhi, india','23412342343432',NULL,NULL,'2016-07-29 14:54:52',1);
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` tinyint(2) NOT NULL,
  `username` varchar(16) DEFAULT NULL,
  `firstname` varchar(45) NOT NULL,
  `middlename` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) NOT NULL,
  `image` varchar(2083) DEFAULT NULL,
  `email` varchar(80) NOT NULL,
  `password` text NOT NULL,
  `gender` bit(1) DEFAULT NULL COMMENT 'Gender 1 means male and 0 means female',
  `birthdate` datetime DEFAULT NULL COMMENT 'Date of Birth',
  `mobile` varchar(16) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `pincode` tinyint(6) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL COMMENT 'Description or short note for the user',
  `hash` text,
  `registeredOn` datetime DEFAULT NULL COMMENT 'Registration Date',
  `custom` varchar(45) DEFAULT NULL COMMENT 'Additional column to observe any immediate change in the  table',
  `status` tinyint(2) DEFAULT '0' COMMENT 'If user is not verified using confirmation mail or mobile OTP then status will be 0. If status is 1 then user is verified and active.',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `unique_user_email` (`email`),
  UNIQUE KEY `unique_user_username` (`username`),
  KEY `fk_user_role_key` (`roleId`),
  CONSTRAINT `fk_user_role_key` FOREIGN KEY (`roleId`) REFERENCES `roles` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'mohsinkhan','mohsin',NULL,'khan','http://www.photogallary.com/images/khan.jpg','khan.square@gmail.com','21232f297a57a5a743894a0e4a801fc3','',NULL,'9166071660',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-20 18:18:51',NULL,0),(2,2,'akshayarora','akshay',NULL,'arora','http://www.photogallary.com/images/akshay.jpg','akshay@gmail.com','ee11cbb19052e40b07aac0ca060c23ee','',NULL,'9828132710',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-20 18:20:56',NULL,0),(3,2,'preetamsingh','preetam',NULL,'signh','http://www.photogallary.com/images/preetam.jpg','preetam@gmail.com','ee11cbb19052e40b07aac0ca060c23ee','',NULL,'9214615644',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-20 18:22:30',NULL,0),(4,3,'vikassaraswat','vikas',NULL,'saraswat','http://www.photogallary.com/images/vikas.jpg','vikas@yahoo.com','084e0343a0486ff05530df6c705c8bb4','',NULL,'7737235548',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-20 18:22:33',NULL,0),(5,2,'AshishVerma','Ashish',NULL,'Verma',NULL,'ashish@gmail.com','user','',NULL,'9001444323',NULL,'Dari mohalla','nasirabad',NULL,'rajasthan','india',NULL,NULL,NULL,'2016-07-28 23:41:40',NULL,0),(6,2,'AshokMeena','Ashok',NULL,'Meena',NULL,'ashok.meena@gmail.com','user','',NULL,'9001444323',NULL,'Dari mohalla','nasirabad',NULL,'rajasthan','india',NULL,NULL,NULL,'2016-07-30 00:09:59',NULL,0),(7,2,'BajiraoSingham','Bajirao',NULL,'Singham',NULL,'bajirao@gmail.com','ee11cbb19052e40b07aac0ca060c23ee','',NULL,'1122334455',NULL,'blah blah','city name',NULL,'state name','country name',NULL,NULL,NULL,'2016-07-30 23:15:26',NULL,0),(19,2,'DigvijaySingh','Digvijay',NULL,'Singh',NULL,'khan_square@yahoo.com','ee11cbb19052e40b07aac0ca060c23ee','',NULL,'1122334455',NULL,'blah blah','city name',NULL,'state name','country name',NULL,NULL,NULL,'2016-07-31 15:45:10',NULL,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'authorization'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_authorization_insert_role` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_authorization_insert_role`(in _title varchar(45), in _alias varchar(45))
BEGIN
	insert into roles (roles.`title`, roles.`alias`) values (_title, _alias);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_authorization_insert_service` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_authorization_insert_service`(in _name varchar(45), in _alias varchar(45))
BEGIN
	insert into services (services.`name`, services.`alias`) values (_name, _alias);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-01  1:49:14
