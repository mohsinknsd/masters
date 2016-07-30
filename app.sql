-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: dictionary
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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `service_id` (`service_id`),
  CONSTRAINT `service_id` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Idioms And Phrases',1),(2,'Phrasal Verbs',1),(3,'Prepositions',1),(4,'One Words',1),(5,'Synonyms And Antonyms',1);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_map`
--

DROP TABLE IF EXISTS `category_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_map` (
  `item_id` int(11) NOT NULL,
  `cat_id` int(11) NOT NULL,
  KEY `category_map_ibfk_1` (`item_id`),
  KEY `category_map_ibfk_2` (`cat_id`),
  CONSTRAINT `category_map_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `words` (`id`),
  CONSTRAINT `category_map_ibfk_2` FOREIGN KEY (`cat_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_map`
--

LOCK TABLES `category_map` WRITE;
/*!40000 ALTER TABLE `category_map` DISABLE KEYS */;
INSERT INTO `category_map` VALUES (1,1),(2,1),(3,4),(4,4);
/*!40000 ALTER TABLE `category_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_map`
--

DROP TABLE IF EXISTS `service_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_map` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_key_idx` (`user_id`),
  KEY `service_key_idx` (`service_id`),
  CONSTRAINT `service_key` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_key` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_map`
--

LOCK TABLES `service_map` WRITE;
/*!40000 ALTER TABLE `service_map` DISABLE KEYS */;
INSERT INTO `service_map` VALUES (1,1,1),(2,1,3);
/*!40000 ALTER TABLE `service_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `status` tinyint(4) DEFAULT '1' COMMENT 'Status implies service status whether service is running or organization has been stopped this service.\nHere status 1 will indicate service is running.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,'vocabulary',1),(2,'grammer',1),(3,'tense',1);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` text NOT NULL,
  `gender` bit(1) DEFAULT NULL COMMENT 'Gender 1 means male and 0 means female',
  `mobile` varchar(13) NOT NULL,
  `address` varchar(254) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL COMMENT 'Registration Date',
  `status` tinyint(4) DEFAULT '0' COMMENT 'If user is not verified using confirmation mail or mobile OTP then status will be 0. If status is 1 then user is verified and active.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Mohsin','Khan','khan.square@gmail.com','21232f297a57a5a743894a0e4a801fc3','','9166071660','H.n. 06, Gandhi Chowk','Nasirabad','Rajasthan','India','2016-06-20 00:00:42',1),(2,'Manish','Kumawat','manish.kumawat@gmail.com','3d416fedcb3f99b104c56c074ea0bfb7','','9828123312','Near Kanta Choraha','Jhotwara','Rajasthan','India','2016-06-20 00:30:42',1),(3,'Yuvraj','Singh','yuvi.singh@gmail.com','84f9bd6d9aa4b91abdfd0a8b0204c9fc','','9887122334','Palsaniya Road','Nasirabad','Rajasthan','India','2016-06-23 00:04:19',1),(4,'Raj','Singh','parasme.raj@gmail.com','5a50046c3bae82d7215f77ddf30b7928','','9887122334','Joshi Marg','Jhotwara','Rajasthan','India','2016-06-23 00:11:43',1),(5,'Mark','Elva','elva1224@gmail.com','aeaa7c88e664774e49259228ef12b154','','5343354095','Main Street','Banglore','Karnataka','India','2016-06-23 00:18:47',1),(7,'Rahul','Kumawat','rahul.kammu@gmail.com','85daa16ec2242484370a7f50fb7e7b31','','9828121212','MAchi Market','Palsana','Rajasthan','India','2016-06-27 16:44:10',0),(25,'firoz','khan','firoz@gmail.com','21232f297a57a5a743894a0e4a801fc3','','987648935','sfasfasfasfasf','cccccc','sssssss','ccccccc','2016-07-02 00:07:55',0),(26,'monika','meenA','monika@gmail.com','21232f297a57a5a743894a0e4a801fc3','','987648935','sfasfasfasfasf','cccccc','sssssss','ccccccc','2016-07-02 00:11:05',1),(27,'Rahulya','Kumawat','parasme.rahul@gmail.com','21232f297a57a5a743894a0e4a801fc3','','1122334455','Kabristan wali gali','Sannata','sssssss','ccccccc','2016-07-12 00:20:42',0),(32,'Firoz','Khan','khan_square@yahoo.com','21232f297a57a5a743894a0e4a801fc3','','9166071222','Gandhi Chowk','Nasirabad','Rajasthan','India','2016-07-12 01:11:08',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `words`
--

DROP TABLE IF EXISTS `words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `words` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` text,
  `translation` text CHARACTER SET utf8,
  `pronunciation` text CHARACTER SET utf8,
  `key` varchar(254) DEFAULT NULL,
  `method` text,
  `synonyms` varchar(254) DEFAULT NULL,
  `antonyms` varchar(254) DEFAULT NULL,
  `image` text,
  `level` tinyint(4) DEFAULT '2' COMMENT 'Difficulty level of the word. It can be as following...\n1 For Difficult\n2 For Medium\n3 For Easy',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `words`
--

LOCK TABLES `words` WRITE;
/*!40000 ALTER TABLE `words` DISABLE KEYS */;
INSERT INTO `words` VALUES (1,'country','Geographical distibution of land','देश','कंट्री','abcd','wxyz','land, province, realm, clime','continent','https://pixabay.com/static/uploads/photo/2014/12/27/14/37/country-lane-581076_640.jpg',2),(2,'apple','A fruit name','सेब','एप्प्ल','abcd','wxyz','kashmiri seb, japani apple',NULL,'https://staticdelivery.nexusmods.com/mods/110/images/74627-0-1459502036.jpg',2),(3,'engagement','Pre agreement of marriage','सगाई','enˈgājmənt','abcd','wxyz','betrothal, affiance, plight','break up','http://www.photographyblogger.net/wp-content/uploads/2013/06/Engagement3.jpg',2),(4,'guitar','an organ to play music','guitar','guitar','git + aar','jeet mei haar','instrument, organ','piano','http://organworld.com/images/guitar.jpg',3);
/*!40000 ALTER TABLE `words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'dictionary'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_details_by_id` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_details_by_id`(item_id INT)
BEGIN
	SELECT words.*, categories.`name` as category from words
		LEFT JOIN category_map on words.`id` = category_map.`item_id`
		LEFT JOIN categories on category_map.`cat_id` = categories.`id`
		WHERE words.`id` = item_id
		ORDER BY words.`name` ASC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_details_by_name` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_details_by_name`(in item VARCHAR(50))
BEGIN
	SELECT words.*, categories.name as category from words
		LEFT JOIN category_map on words.id = category_map.item_id
		LEFT JOIN categories on category_map.cat_id = categories.id
		WHERE words.name = item
		ORDER BY words.name ASC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_user_details` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_user_details`(in email VARCHAR(50), in pass TEXT)
BEGIN
    SELECT 
        users.`id`,
        users.`first_name` as firstName,
        users.`last_name` as lastName,
        users.`email`,
        users.`gender`,
        users.`mobile`,
        users.`address`,
        users.`city`,
        users.`state`,
        users.`country`,
        users.`reg_date` as regDate,
        users.`status`
    FROM users
    WHERE users.`email` = email
    AND users.`password` = MD5(pass);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_words_by_category` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_words_by_category`(user_id INT, category VARCHAR(45))
BEGIN
	-- Getting that this category belongs to which service id
	SET @service_id = (SELECT categories.`service_id` FROM categories
		WHERE categories.`name` = category
        LIMIT 1);
            
		-- Getting subscription status whether service is subscribed or not
		SET @subscription = (SELECT COUNT(service_id) FROM service_map 
			WHERE service_map.`user_id` = user_id 
				AND service_map.`service_id` = @service_id);
            
	-- Checking whether the user subsribed service or not
	IF @subscription > 0 THEN
		-- Finally getting all words related to particular category
		SELECT words.*, categories.`name` as category from words
			LEFT JOIN category_map on words.`id` = category_map.`item_id`
			LEFT JOIN categories on category_map.`cat_id` = categories.`id`
		WHERE categories.`name` = category
		ORDER BY words.`name` ASC;    
    ELSE
		-- Getting only 100 words related to particular category for free users
        -- Difficulty level of those words will be 3 
		SELECT words.*, categories.`name` as category from words
			LEFT JOIN category_map on words.`id` = category_map.`item_id`
			LEFT JOIN categories on category_map.`cat_id` = categories.`id`
		WHERE categories.`name` = category
			AND words.`level` = 3 
		LIMIT 100;    
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insert_new_word` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_new_word`(
	c VARCHAR(50),
    n VARCHAR(50),
    d TEXT,
    t VARCHAR(100) CHARACTER SET utf8, 
    p VARCHAR(100) CHARACTER SET utf8, 
    k VARCHAR(254),
    m TEXT,
    s VARCHAR(254),
    a VARCHAR(254), 
    i TEXT)
BEGIN
	INSERT INTO words (
		words.`name`, 
		words.`description`, 
        words.`translation`, 
        words.`pronunciation`,
        words.`key`,
        words.`method`,
        words.`synonyms`, 
        words.`antonyms`, 
        words.`image`)
    VALUES (n, d, t, p, k, m, s, a, i);
        
    -- Entering into mapping table
    INSERT INTO category_map (item_id, cat_id) VALUES (
		(SELECT id FROM words ORDER BY id DESC LIMIT 1), 
		(SELECT id FROM categories WHERE name = c LIMIT 1)
    );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_register_new_user` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_register_new_user`(
	f VARCHAR(45),
    l VARCHAR(45),
    e VARCHAR(50),
    p TEXT,
    g BIT(1),
    m VARCHAR(13),
    a VARCHAR(254),
    ct VARCHAR(45),
    s VARCHAR(45),
    c VARCHAR(45)
)
BEGIN
	INSERT INTO users (
		users.`first_name`,
		users.`last_name`,
		users.`email`,
		users.`password`,
		users.`gender`,
		users.`mobile`,
		users.`address`,
		users.`city`,
		users.`state`,
		users.`country`,
		users.`reg_date`)
	VALUES (f, l, e, MD5(p), g, m, a, ct, s, c, UTC_TIMESTAMP());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_subscribe_new_service` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_subscribe_new_service`(user_id INT, service_name VARCHAR(45))
BEGIN
	INSERT INTO service_map (user_id, service_id)
	VALUES(user_id, (SELECT id FROM services WHERE name = service_name));
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

-- Dump completed on 2016-07-12  1:15:52
