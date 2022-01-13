-- MySQL dump 10.13  Distrib 5.7.36, for Linux (x86_64)
--
-- Host: localhost    Database: ecobike
-- ------------------------------------------------------
-- Server version	5.7.36-0ubuntu0.18.04.1

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
-- Table structure for table `Bikes`
--

DROP TABLE IF EXISTS `Bikes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Bikes` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `barcode` varchar(255) DEFAULT NULL,
  `licensePlate` varchar(255) DEFAULT NULL,
  `imageUrl` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `dockId` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dockId` (`dockId`),
  CONSTRAINT `Bikes_ibfk_1` FOREIGN KEY (`dockId`) REFERENCES `Dock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Bikes`
--

LOCK TABLES `Bikes` WRITE;
/*!40000 ALTER TABLE `Bikes` DISABLE KEYS */;
INSERT INTO `Bikes` VALUES (1,'CODEBIKE1','90A-06109','@../images/b1-bike.jpeg','standard',1,2),(2,'CODEBIKE2','29A-06109','@../images/b1-bike.jpeg','standard',1,2),(3,'CODEBIKE3','29A-16109','@../images/b1-bike.jpeg','standard',1,1),(4,'CODEBIKE4','29A-16109','@../images/b1-bike.jpeg','standard',1,2),(5,'CODEBIKE5','29A-16109','@../images/b1-bike.jpeg','standard',1,2),(6,'CODEBIKE6','29A-16109','@../images/b1-bike.jpeg','standard',1,2),(7,'CODEBIKE7','29A-16109','@../images/b1-bike.jpeg','standard',1,2),(8,'CODEBIKE8','90A-06109','@../images/b1-bike.jpeg','standard-e',1,1),(9,'CODEBIKE9','90A-06109','@../images/b1-bike.jpeg','standard-e',1,1),(10,'CODEBIKE10','90A-06109','@../images/b1-bike.jpeg','standard-e',1,2),(11,'CODEBIKE11','90A-06109','@../images/b1-bike.jpeg','twin',1,2),(12,'CODEBIKE12','90A-06109','@../images/b1-bike.jpeg','twin',1,7),(13,'CODEBIKE13','90A-06109','@../images/b1-bike.jpeg','twin',1,1);
/*!40000 ALTER TABLE `Bikes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Dock`
--

DROP TABLE IF EXISTS `Dock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Dock` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `numOfPoint` int(11) DEFAULT NULL,
  `maxCapacity` int(11) DEFAULT NULL,
  `numOfAvailableBike` int(11) DEFAULT NULL,
  `area` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Dock`
--

LOCK TABLES `Dock` WRITE;
/*!40000 ALTER TABLE `Dock` DISABLE KEYS */;
INSERT INTO `Dock` VALUES (1,'Lang Ha','So 6 Lang Ha',300,296,4,45.50),(2,'Dai hoc Back Khoa Ha Noi','So 1 Dai Co Viet',300,292,8,65.50),(3,'Vincom NCT','54A D.Nguyen Chi Thanh',300,300,0,65.50),(4,'Ba Trieu','54A Pho Ba Trieu',300,300,0,65.50),(5,'Luong The Vinh','so 6 ngo 60 D.Luong The Vinh',300,300,0,65.50),(6,'Dai hoc Quoc Gia Ha Noi','Cau Giay Ha Noi',300,300,0,65.50),(7,'Dai hoc Quoc Gia Ha Noi','Cau Giay Ha Noi',300,299,1,65.50),(9,'Thanh Cong','cho Thanh Cong',300,300,0,65.50),(10,'Thanh Cong','cho Thanh Cong',300,300,0,65.50),(11,'Thanh Cong','cho Thanh Cong',300,300,0,65.50);
/*!40000 ALTER TABLE `Dock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Invoices`
--

DROP TABLE IF EXISTS `Invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Invoices` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `amount` int(11) DEFAULT NULL,
  `contents` varchar(255) DEFAULT NULL,
  `rentalId` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rentalId` (`rentalId`),
  CONSTRAINT `Invoices_ibfk_1` FOREIGN KEY (`rentalId`) REFERENCES `Rental` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Invoices`
--

LOCK TABLES `Invoices` WRITE;
/*!40000 ALTER TABLE `Invoices` DISABLE KEYS */;
INSERT INTO `Invoices` VALUES (35,160000,'Pay deposit for renting bike CODEBIKE2',72),(36,-160000,'Refund or pay for turning bike CODEBIKE2',72),(37,220000,'Pay deposit for renting bike CODEBIKE12',73),(38,-220000,'Refund or pay for turning bike CODEBIKE12',73),(39,280000,'Pay deposit for renting bike CODEBIKE8',74),(40,-280000,'Refund or pay for turning bike CODEBIKE8',74),(41,160000,'Pay deposit for renting bike CODEBIKE3',76),(42,-160000,'Refund or pay for turning bike CODEBIKE3',76),(43,280000,'Pay deposit for renting bike CODEBIKE8',78),(44,-280000,'Refund or pay for turning bike CODEBIKE8',78),(45,280000,'Pay deposit for renting bike CODEBIKE9',80),(46,-280000,'Refund or pay for turning bike CODEBIKE9',80);
/*!40000 ALTER TABLE `Invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Rental`
--

DROP TABLE IF EXISTS `Rental`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Rental` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `totalUpToNow` int(11) DEFAULT NULL,
  `timeStart` mediumtext,
  `timeEnd` mediumtext,
  `bikeId` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bikeId` (`bikeId`),
  CONSTRAINT `Rental_ibfk_1` FOREIGN KEY (`bikeId`) REFERENCES `Bikes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rental`
--

LOCK TABLES `Rental` WRITE;
/*!40000 ALTER TABLE `Rental` DISABLE KEYS */;
INSERT INTO `Rental` VALUES (72,0,'1641155191','1641155286',2),(73,0,'1641155322','1641155382',12),(74,0,'1641181554','1641181600',8),(76,0,'1641186033','1641186056',3),(78,0,'1641186926','1641186970',8),(80,0,'1641187336','1641187375',9);
/*!40000 ALTER TABLE `Rental` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StandardBikes`
--

DROP TABLE IF EXISTS `StandardBikes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StandardBikes` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `bikeId` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bikeId` (`bikeId`),
  CONSTRAINT `StandardBikes_ibfk_1` FOREIGN KEY (`bikeId`) REFERENCES `Bikes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StandardBikes`
--

LOCK TABLES `StandardBikes` WRITE;
/*!40000 ALTER TABLE `StandardBikes` DISABLE KEYS */;
INSERT INTO `StandardBikes` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7);
/*!40000 ALTER TABLE `StandardBikes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StandardEBikes`
--

DROP TABLE IF EXISTS `StandardEBikes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StandardEBikes` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `bikeId` bigint(20) unsigned DEFAULT NULL,
  `battery` double(10,2) DEFAULT NULL,
  `remainingTimeTs` mediumtext,
  PRIMARY KEY (`id`),
  KEY `bikeId` (`bikeId`),
  CONSTRAINT `StandardEBikes_ibfk_1` FOREIGN KEY (`bikeId`) REFERENCES `Bikes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StandardEBikes`
--

LOCK TABLES `StandardEBikes` WRITE;
/*!40000 ALTER TABLE `StandardEBikes` DISABLE KEYS */;
INSERT INTO `StandardEBikes` VALUES (1,8,100.00,'7200'),(2,9,100.00,'7200'),(3,10,100.00,'7200');
/*!40000 ALTER TABLE `StandardEBikes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transactions`
--

DROP TABLE IF EXISTS `Transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Transactions` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `amount` int(11) DEFAULT NULL,
  `contents` varchar(255) DEFAULT NULL,
  `invoiceId` bigint(20) unsigned DEFAULT NULL,
  `cardCode` varchar(255) DEFAULT NULL,
  `cardHolder` varchar(255) DEFAULT NULL,
  `expDate` varchar(255) DEFAULT NULL,
  `issuingBank` varchar(255) DEFAULT NULL,
  `transactionId` varchar(255) DEFAULT NULL,
  `createdAt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `invoiceId` (`invoiceId`),
  CONSTRAINT `Transactions_ibfk_1` FOREIGN KEY (`invoiceId`) REFERENCES `Invoices` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transactions`
--

LOCK TABLES `Transactions` WRITE;
/*!40000 ALTER TABLE `Transactions` DISABLE KEYS */;
INSERT INTO `Transactions` VALUES (34,160000,'Pay deposit for renting bike CODEBIKE2',35,'vn_group7_2021','Group 7','1125',NULL,'61d20a778bdbf000160e363d','2022-01-03 03:26:29'),(35,160000,'Refund or pay for turning bike CODEBIKE2',36,'vn_group7_2021','Group 7','1125',NULL,'61d20ad78bdbf000160e363f','2022-01-03 03:28:06'),(36,220000,'Pay deposit for renting bike CODEBIKE12',37,'vn_group7_2021','Group 7','1125',NULL,'61d20afa8bdbf000160e3640','2022-01-03 03:28:40'),(37,220000,'Refund or pay for turning bike CODEBIKE12',38,'vn_group7_2021','Group 7','1125',NULL,'61d20b378bdbf000160e3643','2022-01-03 03:29:42'),(38,280000,'Pay deposit for renting bike CODEBIKE8',39,'vn_group7_2021','Group 7','1125',NULL,'61d271712127ea00160a6ae7','2022-01-03 10:45:53'),(39,280000,'Refund or pay for turning bike CODEBIKE8',40,'vn_group7_2021','Group 7','1125',NULL,'61d271a12127ea00160a6ae8','2022-01-03 10:46:40'),(40,160000,'Pay deposit for renting bike CODEBIKE3',41,'vn_group7_2021','Group 7','1125',NULL,'61d282f12127ea00160a6b1a','2022-01-03 12:00:31'),(41,160000,'Refund or pay for turning bike CODEBIKE3',42,'vn_group7_2021','Group 7','1125',NULL,'61d283092127ea00160a6b1b','2022-01-03 12:00:56'),(42,280000,'Pay deposit for renting bike CODEBIKE8',43,'vn_group7_2021','Group 7','1125',NULL,'61d2866e2127ea00160a6b21','2022-01-03 12:15:25'),(43,280000,'Refund or pay for turning bike CODEBIKE8',44,'vn_group7_2021','Group 7','1125',NULL,'61d2869a2127ea00160a6b22','2022-01-03 12:16:10'),(44,280000,'Pay deposit for renting bike CODEBIKE9',45,'vn_group7_2021','Group 7','1125',NULL,'61d288082127ea00160a6b24','2022-01-03 12:22:16'),(45,280000,'Refund or pay for turning bike CODEBIKE9',46,'vn_group7_2021','Group 7','1125',NULL,'61d288302127ea00160a6b26','2022-01-03 12:22:55');
/*!40000 ALTER TABLE `Transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TwinBikes`
--

DROP TABLE IF EXISTS `TwinBikes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TwinBikes` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `bikeId` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bikeId` (`bikeId`),
  CONSTRAINT `TwinBikes_ibfk_1` FOREIGN KEY (`bikeId`) REFERENCES `Bikes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TwinBikes`
--

LOCK TABLES `TwinBikes` WRITE;
/*!40000 ALTER TABLE `TwinBikes` DISABLE KEYS */;
INSERT INTO `TwinBikes` VALUES (1,11),(2,12),(3,13);
/*!40000 ALTER TABLE `TwinBikes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-03 12:29:28
