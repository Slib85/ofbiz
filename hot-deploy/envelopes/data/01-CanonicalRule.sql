-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: ofbizupgrade
-- ------------------------------------------------------
-- Server version	5.7.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `canonical_rule`
--

DROP TABLE IF EXISTS `canonical_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `canonical_rule` (
  `RULE_ID` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `WEB_SITE_ID` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `URL` mediumtext COLLATE utf8_unicode_ci,
  `CANONICAL_URL` mediumtext COLLATE utf8_unicode_ci,
  `ACTIVE` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATED_STAMP` datetime DEFAULT NULL,
  `LAST_UPDATED_TX_STAMP` datetime DEFAULT NULL,
  `CREATED_STAMP` datetime DEFAULT NULL,
  `CREATED_TX_STAMP` datetime DEFAULT NULL,
  PRIMARY KEY (`RULE_ID`),
  KEY `CNNCL_RL_TXSTMP` (`LAST_UPDATED_TX_STAMP`),
  KEY `CNNCL_RL_TXCRTS` (`CREATED_TX_STAMP`),
  KEY `CR_WEBSITEID` (`WEB_SITE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `canonical_rule`
--

LOCK TABLES `canonical_rule` WRITE;
/*!40000 ALTER TABLE `canonical_rule` DISABLE KEYS */;
INSERT INTO `canonical_rule` VALUES ('052d05263b12d79e780d365961123050','folders','/folders/control/search?w=*&af=new:y%20st:blankpolybinders','/new-binders','Y','2019-10-01 13:31:59','2019-10-01 13:31:59','2019-10-01 13:31:59','2019-10-01 13:31:59'),('14ba667c29ad6c1293b5e8fdf33c2a9a','folders','/folders/control/main','/main','Y','2019-10-01 12:52:54','2019-10-01 12:52:54','2019-10-01 12:52:54','2019-10-01 12:52:54'),('3953642d77464641ccc9ac71faaba20b','envelopes','/envelopes/control/main','/main','Y','2019-10-01 13:26:00','2019-10-01 13:26:00','2019-10-01 13:26:00','2019-10-01 13:26:00'),('471157bb8b004ae2a7aed814b84906b9','folders','/folders/control/search?w=file%20tab%20folders&af=customizable:y','/custom-file-tab-folders','Y','2019-10-01 13:49:00','2019-10-01 13:49:00','2019-10-01 13:49:00','2019-10-01 13:49:00'),('811408cb0e2f003259d43987b67d092e','folders','/folders/control/search?w=card%20holder&af=st:blankcardholders','/blank-card-holders','Y','2019-10-01 13:30:50','2019-10-01 13:30:50','2019-10-01 13:30:50','2019-10-01 13:30:50'),('ace62798b260f5110c546d13864e8dfd','folders','/folders/control/search?af=st:blankpaddeddiplomacovers','/blank-padded-diploma-covers','Y','2019-10-01 13:28:05','2019-10-01 13:28:05','2019-10-01 13:28:05','2019-10-01 13:28:05'),('cff496857c5d6a8b4fa88c1f494923d7','folders','/folders/control/search?w=Portfolios&af=st:portfolios','/custom-portfolios','Y','2019-10-01 13:29:21','2019-10-01 13:29:21','2019-10-01 13:29:21','2019-10-01 13:29:21'),('d602d57615e8e1222e457487de1a045a','folders','/folders/control/taxShop','/taxShop','Y','2019-10-01 10:34:26','2019-10-01 10:34:26','2019-10-01 10:34:26','2019-10-01 10:34:26'),('d784119f8c417a9ac7c8502b19487432','folders','/folders/control/search?w=9x12%20folders','/9-x-12-custom-folders','Y','2019-10-01 13:31:08','2019-10-01 13:31:08','2019-10-01 13:31:08','2019-10-01 13:31:08'),('fb10e5e0595d612155507a65faa87cdc','folders','/folders/control/search?w=file%20tab%20folders','/file-tab-folders','Y','2019-10-01 13:48:29','2019-10-01 13:48:29','2019-10-01 13:48:29','2019-10-01 13:48:29');
/*!40000 ALTER TABLE `canonical_rule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-01 15:37:47
