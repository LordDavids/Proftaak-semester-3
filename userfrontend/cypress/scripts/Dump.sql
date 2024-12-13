-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: fontys_solutions
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Truncate table before insert `category`
--

TRUNCATE TABLE `category`;

--
-- Dumping data for table `category`
--

/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'CPU'),(2,'GPU'),(3,'Memory'),(4,'Storage');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `articlenumber` bigint NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `decription` varchar(300) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `category_id` int NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `category_id_idx` (`category_id`),
  CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Truncate table before insert `product`
--

TRUNCATE TABLE `product`;

--
-- Dumping data for table `product`
--

INSERT INTO `product` VALUES (1,'Ryzen 5600X',730143312042,300.00,'This processor features a 3.7 GHz base clock, 6 cores, 12 threads, and comes with the AMD Wraith Stealth Cooler.',_binary '',100,1),(2,'Ryzen 5800X',730143312043,400.00,'A high-performance processor with 8 cores, 16 threads, and a 4.7 GHz boost clock, ideal for gaming and productivity.',_binary '',50,1),(3,'Intel Core i5-12600K',735858512610,280.00,'A mid-tier CPU with 10 cores and 16 threads, perfect for gamers and power users.',_binary '',150,1),(4,'Intel Core i9-12900K',735858512620,600.00,'A flagship CPU with 16 cores and 24 threads, offering exceptional multitasking and performance.',_binary '',20,1),(5,'Ryzen 5900X',730143312045,550.00,'With 12 cores, 24 threads, and a 4.8 GHz boost clock, this CPU excels in both gaming and content creation.',_binary '',40,1),(6,'Ryzen 5950X',730143312046,800.00,'A 16-core, 32-thread CPU designed for high-end computing and rendering tasks.',_binary '',15,1),(7,'Intel Core i7-13700K',735858512630,450.00,'This CPU offers a mix of high efficiency and performance with 16 cores and 24 threads.',_binary '',75,1),(8,'Ryzen 7600',730143312050,250.00,'An entry-level AM5 CPU with 6 cores and 12 threads, suitable for modern gaming setups.',_binary '',200,1),(9,'Ryzen 7700X',730143312051,450.00,'A performance-focused CPU with 8 cores and 16 threads, ideal for gaming and productivity.',_binary '',100,1),(10,'Intel Core i5-13400F',735858512635,200.00,'A budget-friendly option with 10 cores and 16 threads, optimized for gaming.',_binary '',300,1),(11,'NVIDIA RTX 3060',810012080290,300.00,'A capable GPU with 12GB GDDR6 memory, great for 1080p and 1440p gaming.',_binary '',250,2),(12,'NVIDIA RTX 3080',810012080298,700.00,'A top-tier GPU with 10GB GDDR6X memory, delivering outstanding 4K performance.',_binary '',100,2),(13,'AMD Radeon RX 6700 XT',849962111115,480.00,'A mid-range GPU with 12GB GDDR6 memory, ideal for 1440p gaming.',_binary '',125,2),(14,'AMD Radeon RX 7900 XTX',849962111118,1000.00,'A high-performance GPU with 24GB GDDR6 memory for 4K and ray tracing.',_binary '',50,2),(15,'NVIDIA RTX 4090',810012080400,1600.00,'The ultimate GPU with 24GB GDDR6X memory, designed for the most demanding workloads.',_binary '',10,2),(16,'Crucial 32GB DDR5 RAM',649528823709,200.00,'High-speed memory with a frequency of 4800 MHz for next-gen computing.',_binary '',150,3),(17,'Corsair Vengeance 16GB DDR4 RAM',843591091072,80.00,'A reliable memory module with 3200 MHz speed for gamers and professionals.',_binary '',250,3),(18,'Kingston Fury Beast 64GB DDR5 RAM',740617304718,450.00,'A massive 64GB RAM with blazing-fast 5200 MHz speed for power users.',_binary '',50,3),(19,'Samsung 970 EVO Plus 1TB',889894135890,120.00,'A high-performance NVMe SSD with exceptional read/write speeds for storage-intensive tasks.',_binary '',200,4),(20,'WD Blue 2TB HDD',718037846218,60.00,'A reliable hard drive for everyday storage needs with a balance of speed and capacity.',_binary '',300,4),(21,'Seagate Barracuda 4TB HDD',763649118460,120.00,'A high-capacity HDD with ample storage for games, media, and backups.',_binary '',150,4);


--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_id_UNIQUE` (`role_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Truncate table before insert `role`
--

TRUNCATE TABLE `role`;

--
-- Dumping data for table `role`
--

INSERT INTO `role` VALUES (2,'ADMIN'),(1,'USER');


--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(320) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int NOT NULL,
  `phone_number` varchar(25) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Truncate table before insert `user`
--

TRUNCATE TABLE `user`;

--
-- Dumping data for table `user`
--

INSERT INTO `user` VALUES (1,'local','host','david@localhost.nl','$2a$12$8CAcd4I38gSQBPBhSIxKeukCUSof1wNhIx.vzxs8pUJ1eLw5qJHpi',1,'12345678'),(3,'admin','admin','admin@localhost.nl','$2a$12$5tgZvDcBGh9DpWS9jAY9eOTnIFM42wB2jb4EWc0hAGtAs9TGPJVm.',2,'12345678');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-12 10:00:22
