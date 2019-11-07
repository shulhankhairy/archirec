-- MySQL dump 10.16  Distrib 10.2.17-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: u771833889_archi
-- ------------------------------------------------------
-- Server version	10.2.17-MariaDB

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
-- Table structure for table `PEMESANAN`
--

DROP TABLE IF EXISTS `PEMESANAN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PEMESANAN` (
  `PESAN_ID` int(11) NOT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  `PESAN_NAMA` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_HP` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_PEKERJAAN` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_PERUSAHAAN` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_ALAMAT` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_UKURAN_LAHAN` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_ARAH_HADAP_LAHAN` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_LEBAR_JALAN` int(11) DEFAULT NULL,
  `PESAN_GBR_KEBUTUHAN_RUANG` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_GBR_DENAH` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_FOTO_DEPAN` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_FOTO_KANAN` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_FOTO_KIRI` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_FOTO_JALAN` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PESAN_CREATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`PESAN_ID`),
  KEY `FK_USER_ORDER` (`USER_ID`),
  CONSTRAINT `FK_USER_ORDER` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PEMESANAN`
--

/*!40000 ALTER TABLE `PEMESANAN` DISABLE KEYS */;
INSERT INTO `PEMESANAN` VALUES (1,4,'ahmad','085749663736','swasta','arsireon','keputih 34','30,60','utara',20,NULL,NULL,NULL,NULL,NULL,NULL,'2018-04-28 14:13:41'),(2,4,'ahmad','085749663736','ja','hahaha','disini','3,2','timur',1,NULL,NULL,NULL,NULL,NULL,NULL,'2018-04-28 14:35:32'),(3,4,'ahmad','085749663736','sss','','disini','2,8','f',5,NULL,NULL,NULL,NULL,NULL,NULL,'2018-04-28 14:40:19'),(4,4,'ahmad','085749663736','hahaha','','disini','2,3','utara',6,NULL,NULL,NULL,NULL,NULL,NULL,'2018-04-29 14:26:21'),(5,4,'ahmad','085749663736','hahaha','','disini','3,4','timur',7,NULL,NULL,NULL,NULL,NULL,NULL,'2018-04-29 14:27:23'),(6,4,'ahmad','085749663736','hahaha','','disini','3,6','timur',9,'coba','http://www.logiswebmedia.com/archirecon/archirecon-android/image/denah/6.png',NULL,NULL,NULL,NULL,'2018-04-29 14:30:22'),(7,3,'khairy','085749663736','yy','','mana ajadeh','22,6','???? ',9,'coba','',NULL,NULL,NULL,NULL,'2018-05-15 02:03:12'),(8,3,'khairy','085749663736','yyy','','mana ajadeh','2222,22','hhh',22,'just','http://www.logiswebmedia.com/archirecon/archirecon-android/image/denah/8.png',NULL,NULL,NULL,NULL,'2018-05-15 02:29:29'),(9,3,'khairy','085749663736','jjj','','mana ajadeh','331,55','jjs',88,NULL,NULL,NULL,NULL,NULL,NULL,'2018-05-15 03:05:44'),(10,4,'ahmad','085749663736','y','','disini','3,3','luar',3,NULL,NULL,NULL,NULL,NULL,NULL,'2018-05-16 02:19:31'),(11,5,'Danny','08970335107','lol','','Jl. Mawar 10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-05-19 01:47:19'),(12,5,'Danny','08970335107','yyy','','Jl. Mawar 10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-05-19 02:15:48'),(13,6,'novan','076784','hsja','hshs','alen',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-05-19 02:18:34'),(14,5,'Danny','08970335107','coba','','Jl. Mawar 10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-05-19 02:19:27'),(15,5,'Danny','08970335107','lol','','Jl. Mawar 10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-05-19 02:25:07'),(16,7,'fauzan','08816220757','web programmer','logisweb','surabaya','20,10','utara',3,'3',NULL,NULL,NULL,NULL,NULL,'2018-07-19 08:00:40');
/*!40000 ALTER TABLE `PEMESANAN` ENABLE KEYS */;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `USER_ID` int(11) NOT NULL,
  `USER_USERNAME` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `USER_PASSWORD` char(8) COLLATE utf8_unicode_ci NOT NULL,
  `USER_NAMA` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `USER_ALAMAT` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `USER_HP` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `USER_STATUS` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `USER_ROLE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `USER_CODE` int(11) NOT NULL,
  `USER_VERIFICATION` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `USER_CREATE_DATE` datetime NOT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (2,'archirecon@archirecon.com','archirec','archirecon','keputih','089609546387','active','admin',88354,'NO','2018-04-26 23:49:37'),(3,'al.khair.21@gmail.com','satusatu','khairy','mana ajadeh','085749663736','active','user',48900,'NO','2018-04-27 01:57:46'),(4,'shulhankhairy@gmail.com','hahaha','ahmad','disini','085749663736','active','user',48955,'NO','2018-04-27 02:00:56'),(5,'danny.zulfikar99@gmail.com','password','Danny','Jl. Mawar 10','08970335107','active','user',46409,'NO','2018-05-19 01:46:47'),(6,'novannanega@gmail.com','alen','novan','alen','076784','active','user',40752,'NO','2018-05-19 02:13:00'),(7,'kelasimportir@gmail.com','bismilla','fauzan','surabaya','08816220757','active','user',44398,'NO','2018-07-19 07:52:57');
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;

--
-- Dumping routines for database 'u771833889_archi'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-27 21:24:22
