-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: qltc
-- ------------------------------------------------------
-- Server version	8.0.32

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

--
-- Table structure for table `branches`
--

DROP TABLE IF EXISTS `branches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branches` (
  `id` int NOT NULL AUTO_INCREMENT,
  `province` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `district` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `ward` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `quarter` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `houseNumber` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branches`
--

LOCK TABLES `branches` WRITE;
/*!40000 ALTER TABLE `branches` DISABLE KEYS */;
INSERT INTO `branches` VALUES (1,'HCM','1','Tân Định','1','44A',1),(2,'HCM','3','Võ Thị Sáu','2','144B',1),(3,'HCM','1','Cô Giang','3','412A',1);
/*!40000 ALTER TABLE `branches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_feedbacks`
--

DROP TABLE IF EXISTS `customer_feedbacks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_feedbacks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customerId` int NOT NULL,
  `feedbackType` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `reply` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USERS_CUSTOMERFEEDBACKS_idx` (`userId`),
  KEY `FK_CUSTOMER_CUSTOMERFEEDBACKS_idx` (`customerId`),
  CONSTRAINT `FK_CUSTOMER_CUSTOMERFEEDBACKS` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_USERS_CUSTOMERFEEDBACKS` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_feedbacks`
--

LOCK TABLES `customer_feedbacks` WRITE;
/*!40000 ALTER TABLE `customer_feedbacks` DISABLE KEYS */;
INSERT INTO `customer_feedbacks` VALUES (1,1,'Nhân Viên','Good','thanks',1),(2,2,'Nhân Viên','Nice','thank you',1);
/*!40000 ALTER TABLE `customer_feedbacks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes`
--

DROP TABLE IF EXISTS `dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `discount` double DEFAULT NULL,
  `unit` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `type` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `wOnly` tinyint(1) NOT NULL,
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `isAvailable` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (1,'Súp gà',100000,10,'dĩa','1',1,'111',1),(2,'Gà hấp lá chanh',150000,8,'dĩa','1',1,'111',1),(3,'Súp bí đỏ kem nấm',80000,5,'chén','1',1,'111',1),(4,'Khai vị 3 món',50000,10,'dĩa','1',1,'111',1),(5,'Gỏi gà nước bạc hà',70000,10,'dĩa','1',1,'111',1),(6,'Sò huyết tứ xuyên',70000,10,'dĩa','1',1,'111',1),(7,'Tôm sốt phô mai',70000,10,'dĩa','1',1,'111',1),(8,'Bánh Filo nhân vịt',60000,10,'dĩa','1',1,'111',1),(9,'Súp bắp non gà xé',30000,10,'dĩa','1',1,'111',1),(10,'Bánh kem bắp',40000,10,'dĩa ','1',1,'111',1),(11,'Chả giò tôm',40000,10,'dĩa','1',1,'111',1),(12,'Sườn heo nướng mật ông',50000,8,'dĩa','1',1,'111',1),(13,'Súp hào nấm tuyết',70000,9,'thố','1',1,'111',1),(14,'Đậu hủ hạnh nhân',50000,10,'dĩa','1',1,'111',1),(15,'Sò điệp sốt bơ tỏi',60000,11,'dĩa','1',1,'111',1),(16,'Cơm chiên hải sản',80000,10,'dĩa','1',1,'111',1),(17,'Cơm chiên dương châu',80000,10,'dĩa','1',1,'111',1),(18,'Bánh flan',10000,5,'hũ','1',1,'111',1),(19,'Rau câu',10000,5,'hũ','1',1,'111',1),(20,'Pepsi',10000,0,'lon','1',1,'111',1),(21,'Bia 333',15000,0,'lon','1',1,'111',1),(22,'Bia Heineken',15000,0,'lon','1',1,'111',1);
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes_in_branch`
--

DROP TABLE IF EXISTS `dishes_in_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishes_in_branch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `branchId` int NOT NULL,
  `dishId` int NOT NULL,
  `isAvailable` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DISHES_DISHESINBRANCH_idx` (`dishId`),
  KEY `FK_BRANCHES_DISHESINBRANCH_idx` (`branchId`),
  CONSTRAINT `FK_BRANCHES_DISHESINBRANCH` FOREIGN KEY (`branchId`) REFERENCES `branches` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `FK_DISHES_DISHESINBRANCH` FOREIGN KEY (`dishId`) REFERENCES `dishes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes_in_branch`
--

LOCK TABLES `dishes_in_branch` WRITE;
/*!40000 ALTER TABLE `dishes_in_branch` DISABLE KEYS */;
INSERT INTO `dishes_in_branch` VALUES (1,1,1,1),(2,2,2,1),(3,1,3,1),(4,1,4,1),(5,3,5,1),(6,1,6,1),(7,1,7,1),(8,2,8,1),(9,3,9,1),(10,2,10,1),(11,1,11,1),(12,3,12,1),(13,3,13,1),(14,2,14,1),(15,3,15,1),(16,1,16,1),(17,3,17,1),(18,3,18,1),(19,3,19,1),(20,1,20,1),(21,2,21,1),(22,1,21,1),(23,3,21,1),(24,1,8,1);
/*!40000 ALTER TABLE `dishes_in_branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `lastName` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `identityNumber` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `position` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `branchId` int NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId_UNIQUE` (`userId`),
  UNIQUE KEY `identityNumber_UNIQUE` (`identityNumber`),
  KEY `FK_BRANCHES_EMPLOYEES_idx` (`branchId`),
  CONSTRAINT `FK_BRANCHES_EMPLOYEES` FOREIGN KEY (`branchId`) REFERENCES `branches` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `FK_USERS_EMPLOYEES` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Hoang','Son','111111','Manager',1,1),(2,'Nguyen','Tien','111112','Manager',2,14),(3,'Huynh','Huy','111113','Manager',3,15),(4,'Nguyen','Hau','000011','Cashier',1,11),(11,'dasdas','Hau','000014','nbvnhnh',1,3),(19,'Nguyen','Hau','000009','Cashier',1,4),(20,'Nguyendasd','Hausda','000022','Cashier',1,7),(21,'jjjjj','Hausda','000021','Cashier',1,8),(22,'Thinh','Huy','000049','Cashier',1,60),(25,'Thinh11','Huy11','000088','Manager',2,86);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hall_price`
--

DROP TABLE IF EXISTS `hall_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hall_price` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hallId` int NOT NULL,
  `period` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `discount` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_HALLS_HALLPRICE_idx` (`hallId`),
  CONSTRAINT `FK_HALLS_HALLPRICE` FOREIGN KEY (`hallId`) REFERENCES `halls` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hall_price`
--

LOCK TABLES `hall_price` WRITE;
/*!40000 ALTER TABLE `hall_price` DISABLE KEYS */;
INSERT INTO `hall_price` VALUES (1,1,'Sáng',5000000,8),(2,1,'Trưa',5500000,8),(3,1,'Tối',600000,8),(4,1,'Cuối tuần',7000000,8),(5,2,'Sáng',5000000,8),(6,2,'Trưa',5500000,8),(7,2,'Tối',600000,8),(8,2,'Cuối tuần',7000000,8),(9,3,'Sáng',5000000,8),(10,3,'Trưa',5500000,8),(11,3,'Tối',600000,8),(12,3,'Cuối tuần',7000000,8),(13,4,'Sáng',5000000,8),(14,4,'Trưa',5500000,8),(15,4,'Tối',600000,8),(16,4,'Cuối tuần',7000000,8),(17,5,'Sáng',5000000,8),(18,5,'Trưa',5500000,8),(19,5,'Tối',600000,8),(20,5,'Cuối tuần',7000000,8),(21,6,'Sáng',5000000,8),(22,6,'Trưa',5500000,8),(23,6,'Tối',600000,8),(24,6,'Cuối tuần',7000000,8);
/*!40000 ALTER TABLE `hall_price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `halls`
--

DROP TABLE IF EXISTS `halls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `halls` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `discription` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `tableCount` int NOT NULL,
  `guestUpTo` int NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `discount` float NOT NULL,
  `branchId` int NOT NULL,
  `modifiedBy` int NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USERS_HALLS_idx` (`modifiedBy`),
  KEY `FK_BRANCHES_HALLS_idx` (`branchId`),
  CONSTRAINT `FK_BRANCHES_HALLS` FOREIGN KEY (`branchId`) REFERENCES `branches` (`id`),
  CONSTRAINT `FK_USERS_HALLS` FOREIGN KEY (`modifiedBy`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `halls`
--

LOCK TABLES `halls` WRITE;
/*!40000 ALTER TABLE `halls` DISABLE KEYS */;
INSERT INTO `halls` VALUES (1,'Sảnh Platin','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.','',25,250,5000000,0,1,1,1),(2,'Sảnh Venus','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.','',40,400,8000000,0,1,1,1),(3,'Sảnh Elite','Sảnh Elite với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền vàng ấn tượng. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.','',30,300,6500000,0,2,1,1),(4,'Sảnh Mercury','Sảnh Elite với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền vàng ấn tượng. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.','',40,400,8000000,0,2,1,1),(5,'Sảnh Pavilon','Sảnh Pavilon với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền xanh độc đáo. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 850 khách (85 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.','',40,500,9000000,0,3,1,1),(6,'Sảnh Fontana','Sảnh Pavilon với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền xanh độc đáo. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 850 khách (85 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.','',35,400,7000000,0,3,1,1);
/*!40000 ALTER TABLE `halls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customerId` int NOT NULL,
  `employeeId` int NOT NULL,
  `total` decimal(10,0) NOT NULL,
  `discount` double NOT NULL,
  `receiptNo` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `paidVia` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `receiptNo_UNIQUE` (`receiptNo`),
  KEY `FK_CUSTOMERS_ORDERS_idx` (`customerId`),
  KEY `FK_EMPLOYEES_ORDERS` (`employeeId`) /*!80000 INVISIBLE */,
  CONSTRAINT `FK_CUSTOMERS_ORDERS` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_EMPLOYEES_ORDERS` FOREIGN KEY (`employeeId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,5,1,111,10,'1000000','Momo',NULL,'2023-09-01 22:17:31'),(2,6,1,111,10,'1000001','Momo',NULL,'2023-09-02 22:17:31'),(3,7,3,111,10,'1000003','Momo','this is test','2023-09-04 22:17:31'),(4,8,4,111,10,'1000004','Momo','this is test','2023-09-05 22:17:31'),(5,9,3,111,10,'1000005','Momo',NULL,'2023-09-05 01:01:01'),(6,10,4,111,10,'1000006','Momo',NULL,'2023-09-06 22:17:31'),(7,12,1,111,10,'1000007','Momo',NULL,'2023-09-06 22:17:31'),(8,13,2,111,10,'1000008','Momo',NULL,'2023-09-06 22:17:31'),(9,14,2,111,10,'1000009','Momo',NULL,'2023-09-06 22:17:31'),(10,15,2,111,10,'1000010','Momo',NULL,'2023-09-06 22:17:31'),(11,16,2,111,10,'1000011','Momo',NULL,'2023-09-06 22:17:31'),(12,17,2,111,10,'1000012','Momo',NULL,'2023-09-06 22:17:31'),(13,18,3,111,10,'1000013','Momo',NULL,'2023-09-06 22:17:31'),(14,6,3,111,10,'1000014','Momo',NULL,'2023-09-06 23:48:45'),(15,7,2,111,10,'1000015','Momo',NULL,'2023-09-06 23:48:49'),(16,18,1,111,10,'1000016','Momo',NULL,'2023-09-08 01:48:53');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_dishes_details`
--

DROP TABLE IF EXISTS `orders_dishes_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_dishes_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int NOT NULL,
  `dishId` int NOT NULL,
  `price` decimal(10,0) NOT NULL DEFAULT '0',
  `quantity` int NOT NULL DEFAULT '1',
  `discount` double NOT NULL DEFAULT '0',
  `note` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `createdDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_ORDERS_ORDERDDISHESDETAILS_idx` (`orderId`),
  KEY `FK_DISHES_ORDERDDISHESDETAILS_idx` (`dishId`),
  CONSTRAINT `FK_DISHES_ORDERDDISHESDETAILS` FOREIGN KEY (`dishId`) REFERENCES `dishes` (`id`),
  CONSTRAINT `FK_ORDERS_ORDERDDISHESDETAILS` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_dishes_details`
--

LOCK TABLES `orders_dishes_details` WRITE;
/*!40000 ALTER TABLE `orders_dishes_details` DISABLE KEYS */;
INSERT INTO `orders_dishes_details` VALUES (1,1,1,1111111,5,0,NULL,'2023-09-07 00:07:23'),(2,1,2,1111111,5,0,NULL,'2023-09-07 00:07:23'),(3,1,3,1111111,5,0,NULL,'2023-09-07 00:07:23'),(4,1,4,1111111,5,0,NULL,'2023-09-07 00:07:23'),(7,1,5,1111111,5,0,NULL,'2023-09-07 00:07:23'),(8,2,2,1111111,5,0,NULL,'2023-09-07 00:07:23'),(9,2,1,1111111,5,0,NULL,'2023-09-07 00:07:23'),(10,2,2,1111111,5,0,NULL,'2023-09-07 00:07:23'),(11,2,3,1111111,5,0,NULL,'2023-09-07 00:07:23'),(12,3,4,1111111,5,0,NULL,'2023-09-07 00:07:23'),(13,3,5,1111111,5,0,NULL,'2023-09-07 00:07:23'),(14,3,2,1111111,5,0,NULL,'2023-09-07 00:07:23'),(15,3,1,1111111,1,0,NULL,'2023-09-07 15:37:39');
/*!40000 ALTER TABLE `orders_dishes_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_halls_details`
--

DROP TABLE IF EXISTS `orders_halls_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_halls_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int NOT NULL,
  `hallPriceId` int NOT NULL,
  `price` decimal(10,0) NOT NULL DEFAULT '0',
  `discount` double NOT NULL DEFAULT '0',
  `note` varchar(45) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `createdDate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `orderId_UNIQUE` (`orderId`),
  KEY `FK_ORDERS_ORDERSHALLSDETAILS_idx` (`orderId`),
  KEY `FK_HALLPRICE_ORDERSHALLSDETAILS_idx` (`hallPriceId`),
  CONSTRAINT `FK_HALLPRICE_ORDERSHALLSDETAILS` FOREIGN KEY (`hallPriceId`) REFERENCES `hall_price` (`id`),
  CONSTRAINT `FK_ORDERS_ORDERSHALLSDETAILS` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_halls_details`
--

LOCK TABLES `orders_halls_details` WRITE;
/*!40000 ALTER TABLE `orders_halls_details` DISABLE KEYS */;
INSERT INTO `orders_halls_details` VALUES (1,1,1,1111111,0,NULL,'2023-09-01 00:06:16'),(2,2,1,1111111,0,NULL,'2023-09-02 00:06:16'),(3,3,2,1111111,0,NULL,'2023-09-03 15:38:48'),(4,4,3,1111111,0,NULL,'2023-09-04 00:06:16'),(5,5,4,1111111,0,NULL,'2023-09-05 00:06:16'),(6,6,1,1111111,0,NULL,'2023-09-07 15:38:48'),(7,7,1,1111111,0,NULL,'2023-09-07 00:06:16'),(8,8,6,1111111,0,NULL,'2023-09-07 00:06:16'),(9,9,2,1111111,0,NULL,'2023-09-07 15:38:48'),(10,10,3,1111111,0,NULL,'2023-09-07 00:06:16'),(11,11,4,1111111,0,NULL,'2023-09-07 00:06:16'),(12,12,6,1111111,0,NULL,'2023-09-07 15:38:48');
/*!40000 ALTER TABLE `orders_halls_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_services_details`
--

DROP TABLE IF EXISTS `orders_services_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_services_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int NOT NULL,
  `serviceId` int NOT NULL,
  `price` decimal(10,0) NOT NULL DEFAULT '0',
  `quantity` int NOT NULL DEFAULT '1',
  `discount` double NOT NULL DEFAULT '0',
  `note` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `createdDate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_ORDERS_ORDERSSERVICEDETAILS_idx` (`orderId`),
  KEY `FK_SERVICES_ORDERSSERVICEDETAILS_idx` (`serviceId`),
  CONSTRAINT `FK_ORDERS_ORDERSSERVICEDETAILS` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_SERVICES_ORDERSSERVICEDETAILS` FOREIGN KEY (`serviceId`) REFERENCES `service_price` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_services_details`
--

LOCK TABLES `orders_services_details` WRITE;
/*!40000 ALTER TABLE `orders_services_details` DISABLE KEYS */;
INSERT INTO `orders_services_details` VALUES (1,1,1,1111111,5,0,NULL,'2023-09-07 00:07:23'),(2,1,2,1111111,5,0,NULL,'2023-09-07 00:07:23'),(3,1,3,1111111,5,0,NULL,'2023-09-07 00:07:23'),(4,2,4,1111111,5,0,NULL,'2023-09-07 00:07:23'),(7,2,5,1111111,5,0,NULL,'2023-09-07 00:07:23'),(8,2,1,1111111,5,0,NULL,'2023-09-07 00:07:23'),(9,2,2,1111111,5,0,NULL,'2023-09-07 00:07:23'),(10,3,1,1111111,5,0,NULL,'2023-09-07 00:07:23'),(11,3,4,1111111,5,0,NULL,'2023-09-07 00:07:23'),(12,3,2,1111111,5,0,NULL,'2023-09-07 00:07:23'),(13,4,1,1111111,5,0,NULL,'2023-09-07 00:07:23'),(14,4,2,1111111,5,0,NULL,'2023-09-07 00:07:23'),(15,5,7,1111111,5,0,NULL,'2023-09-07 00:07:23'),(16,5,8,1111111,5,0,NULL,'2023-09-07 00:07:23'),(17,5,9,1111111,5,0,NULL,'2023-09-07 00:07:23'),(18,6,3,1111111,5,0,NULL,'2023-09-07 00:07:23'),(19,6,10,1111111,5,0,NULL,'2023-09-07 00:07:23'),(20,6,11,1111111,5,0,NULL,'2023-09-07 00:07:23'),(21,6,12,1111111,5,0,NULL,'2023-09-07 00:07:23'),(22,6,9,1111111,5,0,NULL,'2023-09-07 00:07:23'),(23,7,11,1111111,5,0,NULL,'2023-09-07 00:07:23'),(24,7,12,1111111,5,0,NULL,'2023-09-07 00:07:23'),(25,7,7,1111111,5,0,NULL,'2023-09-07 00:07:23'),(26,7,8,1111111,5,0,NULL,'2023-09-07 00:07:23');
/*!40000 ALTER TABLE `orders_services_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `value_UNIQUE` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'ADD_BRANCH'),(2,'ADD_DISH'),(3,'ADD_DISH_BRANCH'),(62,'ADD_EMPLOYEE'),(69,'ADD_FEEDBACK'),(4,'ADD_HALL'),(70,'ADD_HALL_PRICE\r '),(6,'ADD_ORDER'),(5,'ADD_ORDER_DETAIL'),(7,'ADD_PERMISSION'),(8,'ADD_SERVICE'),(12,'ADD_USER'),(9,'ADD_USER_GROUP_PERMISSION'),(11,'ADD_USER_IN_GROUP'),(10,'ADD_USERS_GROUP'),(13,'ADD_WEDDING'),(14,'DELETE_BRANCH'),(15,'DELETE_DISH'),(16,'DELETE_DISHES_BRANCH'),(64,'DELETE_EMPLOYEE'),(17,'DELETE_HALL'),(68,'DELETE_HALL_PRICE'),(19,'DELETE_ORDER'),(18,'DELETE_ORDER_DETAIL'),(20,'DELETE_PERMISSION'),(21,'DELETE_SERVICES'),(25,'DELETE_USER'),(22,'DELETE_USER_GROUP_PERMISSION'),(24,'DELETE_USER_IN_GROUP'),(23,'DELETE_USERS_GROUP'),(26,'DELETE_WEDDING'),(27,'UPDATE_BRANCH'),(28,'UPDATE_DISH'),(29,'UPDATE_DISHES_BRANCH'),(63,'UPDATE_EMPLOYEE'),(31,'UPDATE_HALL'),(67,'UPDATE_HALL_PRICE'),(33,'UPDATE_ORDER'),(32,'UPDATE_ORDER_DETAILS'),(34,'UPDATE_PERMISSION'),(35,'UPDATE_SERVICE'),(66,'UPDATE_USER'),(37,'UPDATE_USER_GROUP'),(38,'UPDATE_USER_GROUP_PERMISSION'),(39,'UPDATE_USER_IN_GROUP'),(40,'UPDATE_USER_PERMISSION'),(71,'UPDATE_USERS_GROUP'),(41,'VIEW_DETAIL_WEDDING'),(60,'VIEW_EMPLOYEE_BY_ID'),(61,'VIEW_EMPLOYEE_BY_IDENTITY_NUMBER'),(42,'VIEW_LIST_BRANCH'),(43,'VIEW_LIST_DISH'),(44,'VIEW_LIST_DISH_BRANCH'),(59,'VIEW_LIST_EMPLOYEE'),(45,'VIEW_LIST_FEEDBACK'),(46,'VIEW_LIST_HALL'),(48,'VIEW_LIST_ORDER'),(47,'VIEW_LIST_ORDER_DETAILS'),(49,'VIEW_LIST_PERMISSION'),(50,'VIEW_LIST_SERVICE'),(55,'VIEW_LIST_USER'),(51,'VIEW_LIST_USER_GROUP_PERMISSION'),(53,'VIEW_LIST_USER_IN_GROUP'),(54,'VIEW_LIST_USER_PERMISSION'),(52,'VIEW_LIST_USERS_GROUP'),(57,'VIEW_LIST_WEDDING'),(56,'VIEW_LIST_WEDDING_PICTURE'),(58,'VIEW_STAT_VENUES'),(65,'VIEW_USER_BY_ID'),(30,'VIEW_USER_BY_NAME');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_price`
--

DROP TABLE IF EXISTS `service_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_price` (
  `id` int NOT NULL AUTO_INCREMENT,
  `wServiceId` int NOT NULL,
  `period` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `isAvailable` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_WEDDINGSERVICES_SERVICEPRICE_idx` (`wServiceId`),
  CONSTRAINT `FK_WEDDINGSERVICES_SERVICEPRICE` FOREIGN KEY (`wServiceId`) REFERENCES `wedding_services` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_price`
--

LOCK TABLES `service_price` WRITE;
/*!40000 ALTER TABLE `service_price` DISABLE KEYS */;
INSERT INTO `service_price` VALUES (1,1,'Sáng',100000,1),(2,1,'Trưa',100000,1),(3,1,'Tối',100000,1),(4,1,'Cuối tuần',100000,1),(5,2,'Sáng',100000,1),(6,2,'Trưa',100000,1),(7,2,'Tối',100000,1),(8,2,'Cuối tuần',100000,1),(9,3,'Sáng',100000,1),(10,3,'Trưa',100000,1),(11,3,'Tối',100000,1),(12,3,'Cuối tuần',100000,1),(13,4,'Sáng',100000,1),(14,4,'Trưa',100000,1),(15,4,'Tối',100000,1),(16,4,'Cuối tuần',100000,1),(17,5,'Sáng',100000,1),(18,5,'Trưa',100000,1),(19,5,'Tối',100000,1),(20,5,'Cuối tuần',100000,1),(21,6,'Sáng',100000,1),(22,6,'Trưa',100000,1),(23,6,'Tối',100000,1),(24,6,'Cuối tuần',100000,1),(25,7,'Sáng',100000,1),(26,7,'Trưa',100000,1),(27,7,'Tối',100000,1),(28,7,'Cuối tuần',100000,1),(29,8,'Sáng',100000,1),(30,8,'Trưa',100000,1),(31,8,'Tối',100000,1),(32,8,'Cuối tuần',100000,1),(33,9,'Sáng',100000,1),(34,9,'Trưa',100000,1),(35,9,'Tối',100000,1),(36,9,'Cuối tuần',100000,1),(37,10,'Sáng',100000,1),(38,10,'Trưa',100000,1),(39,10,'Tối',100000,1),(40,10,'Cuối tuần',100000,1),(41,11,'Sáng',100000,1),(42,11,'Trưa',100000,1),(43,11,'Tối',100000,1),(44,11,'Cuối tuần',100000,1),(45,12,'Sáng',100000,1),(46,12,'Trưa',100000,1),(47,12,'Tối',100000,1),(48,12,'Cuối tuần',100000,1);
/*!40000 ALTER TABLE `service_price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group_permission`
--

DROP TABLE IF EXISTS `user_group_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_group_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `groupId` int NOT NULL,
  `permissionId` int NOT NULL,
  `allow` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_PERMISSIONS_USERGROUPPERMISSION_idx` (`permissionId`),
  KEY `FK_USERGROUPS_USERGROUPPERMISSION_idx` (`groupId`),
  CONSTRAINT `FK_PERMISSIONS_USERGROUPPERMISSION` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FK_USERGROUPS_USERGROUPPERMISSION` FOREIGN KEY (`groupId`) REFERENCES `user_groups` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group_permission`
--

LOCK TABLES `user_group_permission` WRITE;
/*!40000 ALTER TABLE `user_group_permission` DISABLE KEYS */;
INSERT INTO `user_group_permission` VALUES (1,1,1,1),(2,1,2,1),(3,1,3,1),(4,1,4,1),(5,1,5,1),(6,1,6,1),(7,1,7,1),(8,1,8,1),(9,1,9,1),(10,1,10,1),(11,1,11,1),(12,1,12,1),(13,1,13,1),(14,1,14,1),(15,1,15,1),(16,1,16,1),(17,1,17,1),(18,1,18,1),(19,1,19,1),(20,1,20,1),(21,1,21,1),(22,1,22,1),(23,1,23,1),(24,1,24,1),(25,1,25,1),(26,1,26,1),(27,1,27,1),(28,1,28,1),(29,1,29,1),(30,1,30,1),(31,1,31,1),(32,1,32,1),(33,1,33,1),(34,1,34,1),(35,1,35,1),(37,1,37,1),(38,1,38,1),(39,1,39,1),(40,1,40,1),(41,1,41,1),(42,1,42,1),(43,1,43,1),(44,1,44,1),(45,1,45,1),(46,1,46,1),(47,1,47,1),(48,1,48,1),(49,1,49,1),(50,1,50,1),(51,1,51,1),(52,1,52,1),(53,1,53,1),(54,1,54,1),(55,1,55,1),(56,1,56,1),(57,1,57,1),(58,1,58,1),(59,1,59,1),(60,1,60,1),(61,1,61,1),(62,1,62,1),(63,1,63,1),(64,1,64,1),(65,1,65,1),(66,6,50,1),(67,6,46,1),(68,6,42,1),(69,6,43,1),(70,6,44,1),(71,6,45,1),(72,6,41,1),(73,6,66,1);
/*!40000 ALTER TABLE `user_group_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_groups`
--

DROP TABLE IF EXISTS `user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_groups` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_groups`
--

LOCK TABLES `user_groups` WRITE;
/*!40000 ALTER TABLE `user_groups` DISABLE KEYS */;
INSERT INTO `user_groups` VALUES (1,'ADMIN'),(2,'MANAGER'),(6,'USER'),(4,'WAITER');
/*!40000 ALTER TABLE `user_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_in_group`
--

DROP TABLE IF EXISTS `user_in_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_in_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `groupId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USERS_USERINGROUP_idx` (`userId`),
  KEY `FK_USERGROUPS_USERINGROUP_idx` (`groupId`),
  CONSTRAINT `FK_GROUP_USERINGROUP` FOREIGN KEY (`groupId`) REFERENCES `user_groups` (`id`),
  CONSTRAINT `FK_USERS_USERINGROUP` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_in_group`
--

LOCK TABLES `user_in_group` WRITE;
/*!40000 ALTER TABLE `user_in_group` DISABLE KEYS */;
INSERT INTO `user_in_group` VALUES (1,1,1),(2,5,6),(3,60,6);
/*!40000 ALTER TABLE `user_in_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_permission`
--

DROP TABLE IF EXISTS `user_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `permissionId` int NOT NULL,
  `allow` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_USERS_USERPERMISSION_idx` (`userId`),
  KEY `FK_PERMISSIONS_USERPERMISSION_idx` (`permissionId`),
  CONSTRAINT `FK_PERMISSIONS_USERPERMISSION` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FK_USERS_USERPERMISSION` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permission`
--

LOCK TABLES `user_permission` WRITE;
/*!40000 ALTER TABLE `user_permission` DISABLE KEYS */;
INSERT INTO `user_permission` VALUES (1,1,1,1),(2,1,2,1),(3,1,3,1),(4,1,4,1),(5,1,5,1),(6,1,6,1),(7,1,7,1),(8,1,8,1),(9,1,9,1),(11,1,11,1),(12,1,12,1),(13,1,13,1),(14,1,14,1),(15,1,15,1),(16,1,16,1),(17,1,17,1),(18,1,18,1),(19,1,19,1),(20,1,20,1),(21,1,21,1),(22,1,22,1),(23,1,23,1),(24,1,24,1),(25,1,25,1),(26,1,26,1),(27,1,27,1),(28,1,28,1),(29,1,29,1),(30,1,30,1),(31,1,31,1),(32,1,32,1),(33,1,33,1),(34,1,34,1),(35,1,35,1),(37,1,37,1),(38,1,38,1),(39,1,39,1),(40,1,40,1),(41,1,41,1),(42,1,42,1),(43,1,43,1),(44,1,44,1),(45,1,45,1),(46,1,46,1),(47,1,47,1),(48,1,48,1),(49,1,49,1),(50,1,50,1),(51,1,51,1),(52,1,52,1),(53,1,53,1),(54,1,54,1),(55,1,55,1),(56,1,56,1),(57,1,57,1),(58,1,58,1),(59,1,59,1),(60,1,60,1),(61,1,61,1),(62,1,62,1),(63,1,63,1),(64,1,64,1),(65,1,65,1),(66,1,66,1),(67,10,1,1),(143,2,1,1),(144,2,12,1),(145,2,11,1),(146,60,50,1),(147,60,46,1),(148,60,42,1),(149,60,43,1),(150,60,44,1),(151,60,45,1),(152,60,41,1),(153,60,66,1),(154,61,50,1),(155,61,46,1),(156,61,42,1),(157,61,43,1),(158,61,44,1),(159,61,45,1),(160,61,41,1),(161,61,66,1),(162,62,50,1),(163,62,46,1),(164,62,42,1),(165,62,43,1),(166,62,44,1),(167,62,45,1),(168,62,41,1),(169,62,66,1),(170,63,50,1),(171,63,46,1),(172,63,42,1),(173,63,43,1),(174,63,44,1),(175,63,45,1),(176,63,41,1),(177,63,66,1),(178,64,50,1),(179,64,46,1),(180,64,42,1),(181,64,43,1),(182,64,44,1),(183,64,45,1),(184,64,41,1),(185,64,66,1),(186,65,50,1),(187,65,46,1),(188,65,42,1),(189,65,43,1),(190,65,44,1),(191,65,45,1),(192,65,41,1),(193,65,66,1),(194,66,50,1),(195,66,46,1),(196,66,42,1),(197,66,43,1),(198,66,44,1),(199,66,45,1),(200,66,41,1),(201,66,66,1),(202,67,50,1),(203,67,46,1),(204,67,42,1),(205,67,43,1),(206,67,44,1),(207,67,45,1),(208,67,41,1),(209,67,66,1),(210,68,50,1),(211,68,46,1),(212,68,42,1),(213,68,43,1),(214,68,44,1),(215,68,45,1),(216,68,41,1),(217,68,66,1),(218,69,50,1),(219,69,46,1),(220,69,42,1),(221,69,43,1),(222,69,44,1),(223,69,45,1),(224,69,41,1),(225,69,66,1),(226,70,50,1),(227,70,46,1),(228,70,42,1),(229,70,43,1),(230,70,44,1),(231,70,45,1),(232,70,41,1),(233,70,66,1),(234,71,50,1),(235,71,46,1),(236,71,42,1),(237,71,43,1),(238,71,44,1),(239,71,45,1),(240,71,41,1),(241,71,66,1);
/*!40000 ALTER TABLE `user_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb3_unicode_ci NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `phone` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `identityNumber` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `address` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `createdDate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `identityNumber_UNIQUE` (`identityNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'sonhoang236@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','SonHoang','0938927561','000001','123/231','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:42:19'),(2,'tung112@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','TungNguyen','0938999111','000002','42/213','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:47:12'),(3,'tu56@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','TuNguyen','0938099011','000003','55/2','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:47:12'),(4,'thinh53@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','ThinhNguyen','0938922145','000004','111/3','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:47:12'),(5,'nguyen666@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','NguyenTran','0938321455','000005','235/25','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:47:12'),(6,'khangg12@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','KhangNguyen','0938900111','000006','111/12','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:47:12'),(7,'dao255@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','DaoTran','0126471239','000007','99A','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:47:12'),(8,'thao142@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','ThaoBui','0927018312','000008','112B','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:47:12'),(9,'hung166@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','HungHoang','0938421412','000009','43A','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:47:12'),(10,'van325@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','VanLe','0938990001','000010','52A','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:47:12'),(11,'haunguyen123@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','HauNguyen','0938112573','000011','235/95','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1693502553/ouvg9vhr5lfoztgvfpsq.jpg',0,'2023-08-14 22:47:12'),(12,'hau632@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','HauLe','0938994752','000012','385/12','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-14 22:47:12'),(13,'thinh22@yahoo.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','ThinhBui','0938999221','000013','341/12','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-29 08:24:01'),(14,'tien321@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','TienNguyen','0938113222','111114','32/432','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-15 10:54:50'),(15,'huy43@gmail.com','$2a$10$kMLX.LpqfBLEX/.YXwoPsOrTKeQ3JpNP2Fl1b/dcx.oYmb1gyQ2yu','HuynhHuy','0938775664','111115','99A','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-08-15 10:56:49'),(16,'tungtran313@gmail.com','$2a$10$ngQjxL/6Hx7eQUGStTktt.sabCJcxoTeryy2pt7X1otS2ngJV180.','TungTran','0938111942','000018','22/11','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1693426935/ycff8wa2hw0y7iekvch5.jpg',0,'2023-09-01 00:17:25'),(17,'tungphan21@gmail.com','$2a$10$kuVFcp/ti7xTSJPQjWC8vOziAD3uky9/y3w/6dhhKYRQHKzAh/1Vm','TungPhan','0938177558','000019','22/11','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1693426968/n0r3bifzighg0wjkvoib.jpg',0,'2023-09-01 00:17:25'),(18,'tunghoang313@gmail.com','$2a$10$sfzaZnf/mdwm9EatxXpFQOsvtC2oSHD/uJLUMy7JYmWzwS7xXL6z.','NgocVan','0938353212','000021','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-01 00:17:25'),(19,'ngoctran311@gmail.com','$2a$10$H4p2flBSK4CZ3YhglZjRu.iDJyy9Xa7FXQ2f5TZ2stqpf.siU1Nl2','NgocTran','0938333811','000022','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-01 00:19:36'),(20,'ngocpham33@gmail.com','$2a$10$furPbsRyagXP25SqK7GHbuCbU/S1k4YI1qp3h44wr2yFl3vSy95pS','NgocPham','0938342567','000023','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-01 00:28:45'),(21,'ngsds3333@gmail.com','$2a$10$CfItK5WdLwgFeH/h6AXOAep/v8BnyAsk4wBBW3GtblW1bQuDF997G','asdasd','093824234','000033','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.pnghttps://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-03 02:06:34'),(22,'ngs33@gmail.com','$2a$10$Ej/OULLkN8II5.OFh2o6beHBT.Rhc9EDET6RU7ew4Svfri4jmb85O','qrwasdasd','0938242342','000034','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-06 02:18:24'),(23,'ngs3GG3@gmail.com','$2a$10$yDH/NXltTYQEnRAHGJarKOaUU2uMJSd7p0I676vHwmSWwvn/VkUB.','HHHHHHH','0938242447','000035','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-06 19:38:21'),(54,'ngsgggg3@gmail.com','$2a$10$vgef2TBXJHGQOg.5D0/6meLlzWSBBYBfhKgy620YcFKRyZDwfvY6G','HHHH','0938242442','000036','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 18:58:52'),(60,'huythinh22@gmail.com','$2a$10$1ecjdO5JQxWiwvTA0DOrrehbFu35CehLjzYPKjutnZZAcJsR3Yf7.','HuyThinh','0938476001','000037','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 22:21:20'),(61,'huythinh23@gmail.com','$2a$10$HSUSawNcdJK5wyJBV67kJ.5ebavDPTiIxItyUVJKFA/fB4HHhY/92','HuyThinh1','0938476002','000038','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 22:43:10'),(62,'huythinh24@gmail.com','$2a$10$Aj7I.mshaX1GSoRTmQeBo.Zpjv.55ggzAnYyI5T/x/o2n.mKkJjZC','HuyThinh2','0938476003','000039','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 22:46:27'),(63,'huythinh25@gmail.com','$2a$10$GxkSeja.p4l4D2v1SCH9quLfWw.yX7tPBK0iRVC74cJox9ALWfrrC','HuyThinh3','0938476004','000040','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 22:52:28'),(64,'huythinh26@gmail.com','$2a$10$uBBYPVvq.JUz3iY.fKYFyu9VzQz7SDWgIe/1Yd4sAtm5XNEJM3lTO','HuyThinh4','0938476005','000041','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 23:00:27'),(65,'huythinh27@gmail.com','$2a$10$V/RyVJ7ScwhtTUAmR.HQn.N4GGqJVIEcqKTNIYz.kTWMHHWBKZFQK','HuyThinh5','0938476006','000042','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 23:01:52'),(66,'huythinh28@gmail.com','$2a$10$j6/D5CjE1al7z8gTCTmrgumjtg31U1V7DxYMcM55/S5Af4AjTAQM.','HuyThinh6','0938476007','000043','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 23:09:04'),(67,'huythinh29@gmail.com','$2a$10$G55ija4EgeypkU65E2AeBOng1a5xMes4RhO3MSze/Mxrw4rN5lmtG','HuyThinh7','0938476008','000044','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 23:13:05'),(68,'huythinh30@gmail.com','$2a$10$9wH7.KcSykLgSExFyxogq.LrGLKYfG6VeN.fTF.QMB3ABquTnacoK','HuyThinh8','0938476009','000045','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 23:21:00'),(69,'huythinh31@gmail.com','$2a$10$5YWop8mMd7s.cdbsfuFusuYT/ZseshyWBmpbcsZsLY.aKEMaRP1ZC','HuyThinh9','0938476010','000046','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 23:25:32'),(70,'huythinh32@gmail.com','$2a$10$nafXKHoWOVYICE/.5NawDet9lKgBmiSgpepaCAuCcvrUrsp5qiAOu','HuyThinh10','0938476077','000047','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 23:27:14'),(71,'huythinh33@gmail.com','$2a$10$TqQ3MIklv17kIr5tl7y65eDgdOzc7Z0NtSctUWbFpryqxEotLmRva','HuyThinh11','0938476078','000048','283/47','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-08 23:29:12'),(76,'huythinh35@gmail.com','123456','huythinh35','0938476079','000050','123/12','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-09 01:31:09'),(86,'huyt666@gmail.com','123456','huyt666','0938000177','000088','123/12','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',0,'2023-09-09 02:03:16');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wedding`
--

DROP TABLE IF EXISTS `wedding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wedding` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int NOT NULL,
  `customerId` int NOT NULL,
  `deposit` decimal(10,0) NOT NULL,
  `totalLeft` decimal(10,0) NOT NULL,
  `discount` float NOT NULL,
  `paidVia` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `receiptNo` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `celebrityDate` datetime NOT NULL,
  `isCompleted` tinyint(1) NOT NULL,
  `tableNumber` int NOT NULL,
  `guestNumber` int NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `createdDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createdBy` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CUSTOMERS_WEDDING_idx` (`customerId`),
  KEY `FK_ORDERS_WEDDING_idx` (`orderId`),
  KEY `FK_USERS_WEDDING_idx` (`createdBy`),
  CONSTRAINT `FK_CUSTOMERS_WEDDING` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_ORDERS_WEDDING` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`),
  CONSTRAINT `FK_USERS_WEDDING` FOREIGN KEY (`createdBy`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wedding`
--

LOCK TABLES `wedding` WRITE;
/*!40000 ALTER TABLE `wedding` DISABLE KEYS */;
/*!40000 ALTER TABLE `wedding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wedding_pictures`
--

DROP TABLE IF EXISTS `wedding_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wedding_pictures` (
  `id` int NOT NULL AUTO_INCREMENT,
  `weddingId` int NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `isPublic` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_WEDDING_WEDDINGPICTURES_idx` (`weddingId`),
  CONSTRAINT `FK_WEDDING_WEDDINGPICTURES` FOREIGN KEY (`weddingId`) REFERENCES `wedding` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wedding_pictures`
--

LOCK TABLES `wedding_pictures` WRITE;
/*!40000 ALTER TABLE `wedding_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `wedding_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wedding_services`
--

DROP TABLE IF EXISTS `wedding_services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wedding_services` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `modifiedBy` int NOT NULL,
  `isAvailable` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USERS_WEDDINGSERVICES_idx` (`modifiedBy`),
  CONSTRAINT `FK_USERS_WEDDINGSERVICES` FOREIGN KEY (`modifiedBy`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wedding_services`
--

LOCK TABLES `wedding_services` WRITE;
/*!40000 ALTER TABLE `wedding_services` DISABLE KEYS */;
INSERT INTO `wedding_services` VALUES (1,'Nghi thức khai tiệc',1,1),(2,'Tượng đá hoa đăng',1,1),(3,'Tranh cát',1,1),(4,'Đèn LED',1,1),(5,'Cổng hoa tươi',1,1),(6,'Album dàn trang',1,1),(7,'Hoa cầm tay',1,1),(8,'Camera chụp hình',1,1),(9,'Tranh ký  tên',1,1),(10,'Tiết mục khai tiệc',1,1),(11,'Khu vực chụp hình',1,1),(12,'Pianist và Violinist đón khách',1,1);
/*!40000 ALTER TABLE `wedding_services` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-09 19:18:52
