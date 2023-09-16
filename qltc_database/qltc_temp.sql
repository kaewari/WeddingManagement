-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: qltc_tmp
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branches`
--

LOCK TABLES `branches` WRITE;
/*!40000 ALTER TABLE `branches` DISABLE KEYS */;
INSERT INTO `branches` VALUES (1,'Hai Phong','Tan Anh','Phu Quang 5','14','44A',1),(2,'HCM','3','Võ Thị Sáu','2','144B',1),(3,'HCM','1','Cô Giang','3','412A',1),(4,'HN','1','Quan Tu LIem','1','44A',1),(5,'HCM','3','Tân Định 1','1','44A',1),(6,'HCM','2','SO SO','14','44A',1),(7,'HNFFFF','2','Phu Quang 1','14','44A',1),(8,'HCM','2','SO SO','14','44A',1),(9,'HCM','2','SO SO','14','44A',1),(10,'HCM','2','SO SO','14','44A',1),(11,'HCM','2','SO SO','14','44A',1),(12,'HCM','2','SO SO','14','44A',1),(13,'HCM','2','SO SO','14','44A',1),(14,'HCM','2','SO SO','14','44A',1),(15,'HCM','2','SO SO','14','44A',1),(16,'HCM','2','SO SO','14','44A',1),(18,'HCM','2','SO SO','14','44A',1),(20,'HCM','2','SO SO','14','44A',1);
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
  `createdDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `reply` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USERS_CUSTOMERFEEDBACKS_idx` (`userId`),
  KEY `FK_CUSTOMER_CUSTOMERFEEDBACKS_idx` (`customerId`),
  CONSTRAINT `FK_CUSTOMER_CUSTOMERFEEDBACKS` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_USERS_CUSTOMERFEEDBACKS` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_feedbacks`
--

LOCK TABLES `customer_feedbacks` WRITE;
/*!40000 ALTER TABLE `customer_feedbacks` DISABLE KEYS */;
INSERT INTO `customer_feedbacks` VALUES (1,1,'Nhân Viên','Good','2020-10-01 08:55:00','thanks',1),(4,1,'OK','OK','2020-10-01 08:59:00','yes',1),(6,1,'Restaurants','OK 1','2023-09-08 07:26:41','Yes sir.',1),(7,1,'345345','OK','2023-09-08 08:27:26',NULL,NULL),(8,1,'Restaurants','OK','2023-09-08 21:50:28','Yes sir.',1),(9,1,'DM','OK','2023-09-09 09:56:08',NULL,NULL),(10,1,'Tiệc cưới','OK','2023-09-12 13:44:36',NULL,NULL),(11,1,'Tiệc cưới','yes','2023-09-12 13:54:35',NULL,NULL);
/*!40000 ALTER TABLE `customer_feedbacks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish_in_branch`
--

DROP TABLE IF EXISTS `dish_in_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish_in_branch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `branchId` int NOT NULL,
  `dishId` int NOT NULL,
  `isAvailable` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DISHES_DISHESINBRANCH_idx` (`dishId`),
  KEY `FK_BRANCHES_DISHESINBRANCH_idx` (`branchId`),
  CONSTRAINT `FK_BRANCHES_DISHESINBRANCH` FOREIGN KEY (`branchId`) REFERENCES `branches` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `FK_DISHES_DISHESINBRANCH` FOREIGN KEY (`dishId`) REFERENCES `dishes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish_in_branch`
--

LOCK TABLES `dish_in_branch` WRITE;
/*!40000 ALTER TABLE `dish_in_branch` DISABLE KEYS */;
INSERT INTO `dish_in_branch` VALUES (1,1,1,1),(2,2,2,1),(3,1,3,1),(4,1,4,1),(5,3,5,1),(6,1,6,1),(7,1,7,1),(8,2,8,1),(9,3,9,1),(10,2,10,1),(11,1,11,1),(12,3,12,1),(13,3,13,1),(14,2,14,1),(15,3,15,1),(16,1,16,1),(17,3,17,1),(18,3,18,1),(19,3,19,1),(20,1,20,1),(21,2,21,1),(22,1,21,1),(23,3,21,1),(24,1,8,1);
/*!40000 ALTER TABLE `dish_in_branch` ENABLE KEYS */;
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
  `discount` float NOT NULL,
  `unit` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `type` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `wOnly` tinyint(1) NOT NULL,
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `isAvailable` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (1,'Súp gà',100000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261172/tiec%20cuoi/sup-ga_zrmizp.jpg',1),(2,'Gà hấp lá chanh',150000,8,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261183/tiec%20cuoi/ga-hap-la-chanhjpg_hn67we.jpg',1),(3,'Súp bí đỏ kem nấm',80000,5,'chén','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261176/tiec%20cuoi/aup-bi-do-kem-nam_jek6ti.jpg',1),(4,'Khai vị 3 món',50000,10,'dĩa','món ăn',1,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261171/tiec%20cuoi/khai-vi-3-mon_fjdgmx.jpg',1),(5,'Gỏi gà nước bạc hà',70000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261183/tiec%20cuoi/goi-ga-bac-ha_tlyy1j.jpg',1),(6,'Sò huyết tứ xuyên',70000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261169/tiec%20cuoi/so-huyet-sot-tu-xuyen_lm3z9e.jpg',1),(7,'Tôm sốt phô mai',70000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261177/tiec%20cuoi/tom-sot-pho-mai_as5ll9.webp',1),(8,'Bánh Filo nhân vịt',60000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261174/tiec%20cuoi/banh-filo-nhan-vit_awyons.webp',1),(9,'Súp bắp non gà xé',30000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261169/tiec%20cuoi/sup-bap-non-ga-xe_xev88u.jpg',1),(10,'Bánh kem bắp',40000,10,'dĩa ','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261178/tiec%20cuoi/banh-kem-bap_edecex.png',1),(11,'Chả giò tôm',40000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261180/tiec%20cuoi/cha-gio-tom_cfvson.jpg',1),(12,'Sườn heo nướng mật ông',50000,8,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261176/tiec%20cuoi/suon-heo-nuong_hd0lms.png',1),(13,'Súp hào nấm tuyết',70000,9,'thố','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261172/tiec%20cuoi/sup-hau-nam-tuyet_oz3eso.jpg',1),(14,'Đậu hủ hạnh nhân',50000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261181/tiec%20cuoi/dau-hu-hanh-nhan_kf1yqp.jpg',1),(15,'Sò điêp sốt bơ tỏi',60000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261170/tiec%20cuoi/so-diep-sot-bo-toi_spl4tj.jpg',1),(16,'Cơm chiên hải sản',80000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261182/tiec%20cuoi/com-chien-hai-san_wv1lq4.webp',1),(17,'Cơm chiên dương châu',80000,10,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261168/tiec%20cuoi/lam-com-chien-duong-chau_efsptw.jpg',1),(18,'Bánh flan',10000,0,'hũ','tráng miệng',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261173/tiec%20cuoi/banh-flan_k2uqwi.webp',1),(19,'Rau câu',10000,0,'hũ','tráng miệng',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261168/tiec%20cuoi/rau-cau_qnw95g.webp',1),(20,'Pepsi',10000,0,'lon','thức uống',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261168/tiec%20cuoi/pepsi_vfdx1n.webp',1),(21,'Bia 333',15000,0,'lon','thức uống',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261179/tiec%20cuoi/bia-333_m68in4.jpg',1),(22,'Cơm gà xối mỡ',150000,0,'dĩa','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694291042/tiec%20cuoi/com-ga-xoi-mo_n7pmsz.jpg',1),(23,'Bia Heineken',15000,0,'lon','món ăn',0,'https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694261179/tiec%20cuoi/bia-heineken_whbs06.webp',1);
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `firstName` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `lastName` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `identityNumber` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `position` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `branchId` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId_UNIQUE` (`userId`),
  KEY `FK_USERS_EMPLOYEES_idx` (`userId`),
  KEY `FK_BRANCHES_EMPLOYEES_idx` (`branchId`),
  CONSTRAINT `FK_BRANCHES_EMPLOYEES` FOREIGN KEY (`branchId`) REFERENCES `branches` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `FK_USERS_EMPLOYEES` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,1,'Hoang','Son','111111','Admin',1),(2,14,'Nguyen','Tien','111112','Admin',2),(7,20,'Thinh11','Huy11','000088','Manager',2),(8,19,'dasdasdqwe','asdasdeqw','13121231','dcccc',1),(9,15,'asddas','asdasdeqw','13121232','dcccc',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hall_price`
--

LOCK TABLES `hall_price` WRITE;
/*!40000 ALTER TABLE `hall_price` DISABLE KEYS */;
INSERT INTO `hall_price` VALUES (1,1,'Sáng',5000000,8),(2,1,'Trưa',5500000,8),(3,1,'Tối',600000,8),(4,1,'Cuối tuần',7000000,8),(5,2,'Sáng',5000000,8),(6,2,'Trưa',5500000,8),(7,2,'Tối',600000,8),(8,2,'Cuối tuần',7000000,8),(9,3,'Sáng',5000000,8),(10,3,'Trưa',5500000,8),(11,3,'Tối',600000,8),(12,3,'Cuối tuần',7000000,8),(13,4,'Sáng',5000000,8),(14,4,'Trưa',5500000,8),(15,4,'Tối',600000,8),(16,4,'Cuối tuần',7000000,8),(17,5,'Sáng',5000000,8),(18,5,'Trưa',5500000,8),(19,5,'Tối',600000,8),(20,5,'Cuối tuần',7000000,8),(21,6,'Sáng',5000000,8),(22,6,'Trưa',5500000,8),(23,6,'Tối',600000,8),(24,6,'Cuối tuần',7000000,8),(26,1,'Ngay dac biet',120000,0.1),(27,1,'Ngay dac biet 2',120000,0.1),(28,30,'Sang',123,0);
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
  `description` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `tableCount` int NOT NULL,
  `guestUpTo` int NOT NULL,
  `branchId` int NOT NULL,
  `createdDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `modifiedBy` int NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USERS_HALLS_idx` (`modifiedBy`),
  KEY `FK_BRANCHES_HALLS_idx` (`branchId`),
  CONSTRAINT `FK_BRANCHES_HALLS` FOREIGN KEY (`branchId`) REFERENCES `branches` (`id`),
  CONSTRAINT `FK_USERS_HALLS` FOREIGN KEY (`modifiedBy`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `halls`
--

LOCK TABLES `halls` WRITE;
/*!40000 ALTER TABLE `halls` DISABLE KEYS */;
INSERT INTO `halls` VALUES (1,'Sảnh Plati 1 n 1','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',27,290,2020,'2020-01-01 00:00:00',1,1),(2,'Sảnh Venus','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',40,400,1,'2020-01-01 00:00:00',1,1),(3,'Sảnh Elite','Sảnh Elite với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền vàng ấn tượng. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,300,2,'2020-01-01 00:00:00',1,1),(4,'Sảnh Mercury','Sảnh Elite với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền vàng ấn tượng. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',40,400,2,'2020-01-01 00:00:00',1,1),(5,'Sảnh Pavilon','Sảnh Pavilon với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền xanh độc đáo. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 850 khách (85 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',40,500,3,'2020-01-01 00:00:00',1,1),(6,'Sảnh Fontana','Sảnh Pavilon với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền xanh độc đáo. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 850 khách (85 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',35,400,3,'2020-01-01 00:00:00',1,1),(7,'Sảnh Venus','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',40,400,15,'2023-09-07 11:35:01',1,1),(8,'Sảnh Venu s 1','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,353,15,'2023-09-07 13:04:42',1,1),(9,'Sảnh Venu s 1','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,353,15,'2023-09-07 13:05:21',1,1),(10,'Sảnh Venu s 1','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,353,15,'2023-09-07 13:05:59',1,1),(11,'Sảnh Venu s 1','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,353,15,'2023-09-07 13:08:33',1,1),(12,'Sảnh Venu s 1','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,353,15,'2023-09-07 13:09:58',1,1),(13,'Sảnh Venu s 1','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,353,15,'2023-09-07 13:10:08',1,1),(14,'Sảnh Venu s 1','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,353,15,'2023-09-07 13:12:59',1,1),(15,'Sảnh Venu s 1','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,353,16,'2023-09-07 13:13:05',1,1),(16,'Sảnh Venu s 1','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,353,16,'2023-09-07 13:13:14',1,1),(17,'Sảnh Venu s 1 2','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,353,16,'2023-09-07 13:13:22',1,1),(18,'Sảnh Venu s 1 2','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',30,353,16,'2023-09-07 13:13:29',1,1),(19,'Sảnh Venu s 1 2','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:13:36',1,1),(20,'Sảnh Venu s 1 2','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:13:43',1,1),(21,'Sảnh Venu s 1 2','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:17:13',1,1),(22,'Sảnh Venu s 1 2','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:17:16',1,1),(23,'Sảnh Venu s 1 2','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:17:17',1,1),(24,'Sảnh Venu s 1 2','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:23:29',1,1),(25,'Sảnh Venu s234234334343343','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:23:40',1,1),(26,'Sảnh Venu s234234334343343','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:24:18',1,1),(27,'Sảnh Venu s234234334343343','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:24:31',1,1),(28,'Sảnh Venu ffffffffffff12324343343','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:24:39',1,1),(29,'Sảnh Venu ffffffffffff12324343343','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-07 13:26:10',1,1),(30,'Sảnh Venu ss 123','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,1,'2023-09-07 13:36:28',1,1),(31,'Sảnh Venu ffffffffffff12324343343','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-09 18:12:05',1,1),(32,'Sảnh Venu ffffffffffff12324343343','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-09 18:14:21',1,1),(33,'Sảnh Suny','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-09 18:14:36',1,1),(34,'Sảnh Suny','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau. werwe',230,3533,16,'2023-09-09 18:23:10',1,1);
/*!40000 ALTER TABLE `halls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_dish_details`
--

DROP TABLE IF EXISTS `order_dish_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_dish_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int NOT NULL,
  `dishId` int NOT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `discount` float DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ORDERS_ORDERDDISHESDETAILS_idx` (`orderId`),
  KEY `FK_DISHES_ORDERDDISHESDETAILS_idx` (`dishId`),
  CONSTRAINT `FK_DISHES_ORDERDDISHESDETAILS` FOREIGN KEY (`dishId`) REFERENCES `dishes` (`id`),
  CONSTRAINT `FK_ORDERS_ORDERDDISHESDETAILS` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_dish_details`
--

LOCK TABLES `order_dish_details` WRITE;
/*!40000 ALTER TABLE `order_dish_details` DISABLE KEYS */;
INSERT INTO `order_dish_details` VALUES (15,1,1,12,0,30,'Khong cho ot vao mon nay'),(16,1,2,14,0.00001,30,'Khong'),(21,1,1,12,0,30,'Khong cho ot vao mon nay'),(22,2,2,14,0.00001,30,'Khong'),(23,2,1,12,0,30,'Khong cho ot vao mon nay'),(24,3,2,14,0.00001,30,'Khong'),(25,3,1,12,0,30,'Khong cho ot vao mon nay'),(26,3,2,14,0.00001,30,'Khong'),(29,3,1,12,0,30,'Khong cho ot vao mon nay'),(30,4,2,14,0.00001,30,'Khong'),(31,4,1,12,0,30,'Khong cho ot vao mon nay'),(32,5,2,14,0.00001,30,'Khong'),(33,5,1,12,0,30,'Khong cho ot vao mon nay'),(34,5,2,14,0.00001,30,'Khong'),(35,5,1,12,0,30,'Khong cho ot vao mon nay'),(36,5,2,14,0.00001,30,'Khong'),(37,5,1,12,0,30,'Khong cho ot vao mon nay'),(38,5,2,14,0.00001,30,'Khong'),(39,5,1,12,0,30,'Khong cho ot vao mon nay'),(40,42,2,14,0.00001,30,'Khong'),(41,42,1,12,0,30,'Khong cho ot vao mon nay'),(42,43,2,14,0.00001,30,'Khong'),(45,43,1,12,0,30,'Khong cho ot vao mon nay'),(46,58,2,14,0.00001,30,'Khong'),(54,5,1,1234,0.005,1,'OK NHAS'),(55,5,3,23223,0.005,1,'OK NHA 2'),(62,5,2,14,0.00001,30,'Khong'),(64,5,2,14,0.00001,30,'Khong'),(69,5,1,12,0,30,'Khong cho ot vao mon nay'),(70,5,2,14,0.00001,30,'Khong'),(71,68,1,12,0,30,'Khong cho ot vao mon nay'),(72,68,2,14,0.00001,30,'Khong'),(74,66,3,23223,0.005,1,'OK NHA 2');
/*!40000 ALTER TABLE `order_dish_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_halls_details`
--

DROP TABLE IF EXISTS `order_halls_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_halls_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int NOT NULL,
  `hallPriceId` int NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `discount` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ORDERS_ORDERSHALLSDETAILS_idx` (`orderId`),
  KEY `FK_HALLPRICE_ORDERSHALLSDETAILS_idx` (`hallPriceId`),
  CONSTRAINT `FK_HALLPRICE_ORDERSHALLSDETAILS` FOREIGN KEY (`hallPriceId`) REFERENCES `hall_price` (`id`),
  CONSTRAINT `FK_ORDERS_ORDERSHALLSDETAILS` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_halls_details`
--

LOCK TABLES `order_halls_details` WRITE;
/*!40000 ALTER TABLE `order_halls_details` DISABLE KEYS */;
INSERT INTO `order_halls_details` VALUES (13,1,10,100000,0),(14,2,5,200000,0),(19,3,10,100000,0),(20,4,5,200000,0),(21,5,10,100000,0),(22,42,5,200000,0),(23,43,10,100000,0),(24,46,5,200000,0),(27,47,10,100000,0),(28,48,5,200000,0),(29,50,10,100000,0),(30,51,5,200000,0),(31,52,10,100000,0),(32,53,5,200000,0),(33,54,10,100000,0),(34,55,5,200000,0),(35,56,10,100000,0),(36,58,5,200000,0),(37,63,10,100000,0),(38,64,5,200000,0),(39,65,10,100000,0),(40,66,5,200000,0),(43,67,10,100000,0),(44,68,5,200000,0);
/*!40000 ALTER TABLE `order_halls_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_services_details`
--

DROP TABLE IF EXISTS `order_services_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_services_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int NOT NULL,
  `servicePriceId` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `price` decimal(10,0) NOT NULL,
  `note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ORDERS_ORDERSSERVICEDETAILS_idx` (`orderId`),
  KEY `FK_SERVICES_ORDERSSERVICEDETAILS_idx` (`servicePriceId`),
  CONSTRAINT `FK_ORDERS_ORDERSSERVICEDETAILS` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_SERVICES_ORDERSSERVICEDETAILS` FOREIGN KEY (`servicePriceId`) REFERENCES `service_prices` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_services_details`
--

LOCK TABLES `order_services_details` WRITE;
/*!40000 ALTER TABLE `order_services_details` DISABLE KEYS */;
INSERT INTO `order_services_details` VALUES (5,5,1,1,243000,NULL),(8,4,1,1,243000,NULL),(9,5,1,1,243000,NULL),(10,5,1,1,243000,NULL),(12,5,1,1,243000,NULL),(13,4,1,1,243000,NULL),(14,2,1,1,243000,NULL),(15,1,1,1,243000,NULL),(16,3,1,1,243000,NULL),(17,42,1,1,243000,NULL),(18,4,1,1,243000,NULL),(20,5,1,1,243000,NULL),(21,5,1,1,123000,NULL),(24,43,1,1,243000,NULL);
/*!40000 ALTER TABLE `order_services_details` ENABLE KEYS */;
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
  `total` decimal(10,0) NOT NULL,
  `discount` decimal(10,0) DEFAULT NULL,
  `receiptNo` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `paidVia` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `employeeId` int NOT NULL,
  `note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `createdDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_CUSTOMERS_ORDERS_idx` (`customerId`),
  KEY `FK_EMPLOYEES_ORDERS_idx` (`employeeId`),
  CONSTRAINT `FK_CUSTOMERS_ORDERS` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_EMPLOYEES_ORDERS` FOREIGN KEY (`employeeId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,2,270089,184,'123','Momo',5,'OK','2021-01-01 10:05:24'),(2,2,443746,5,'123','Momo',5,'OK','2021-01-01 10:06:56'),(3,2,443746,5,'123','Momo',5,'OK','2021-02-01 10:08:20'),(4,2,443746,5,'123','Momo',5,'OK','2021-03-02 10:14:07'),(5,2,443746,5,'123','Momo',5,'OK','2021-04-02 10:27:05'),(42,2,543800,5,'123','Momo',5,'OK','2021-07-03 11:25:30'),(43,2,543800,5,'123','Momo',5,'OK','2021-12-03 11:35:29'),(46,2,543800,5,'123','Momo',5,'OK','2023-09-04 11:40:31'),(47,2,543800,5,'123','Momo',5,'OK','2023-09-04 11:42:45'),(48,2,543800,5,'123','Momo',5,'OK','2023-09-05 11:42:57'),(50,2,543800,5,'123','Momo',5,'OK','2023-09-05 11:46:33'),(51,2,543800,5,'123','Momo',5,'OK','2023-09-09 11:46:45'),(52,2,543800,5,'123','Momo',5,'OK','2023-09-09 11:47:36'),(53,2,543800,5,'123','Momo',5,'OK','2023-09-09 11:49:28'),(54,2,543800,5,'123','Momo',5,'OK','2023-09-09 11:50:21'),(55,2,543800,5,'123','Momo',5,'OK','2023-09-09 12:02:26'),(56,2,543800,5,'123','Momo',5,'OK','2023-09-09 12:05:54'),(58,2,523800,5,NULL,NULL,5,'OK','2023-09-09 12:37:54'),(63,2,800,0,NULL,NULL,1,'OK','2023-09-09 15:54:44'),(64,2,123297,62,NULL,NULL,1,'OK','2023-09-09 15:57:57'),(65,2,420,0,NULL,NULL,1,'OK','2023-09-09 16:00:11'),(66,2,23107,116,'128hw87c2g83d','VNPay',1,'OK','2023-09-09 16:01:01'),(67,2,543800,5,'242352353','Cash',5,'OK','2023-09-09 16:29:00'),(68,2,800,0,NULL,NULL,1,'OK','2023-09-09 18:49:50');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
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
INSERT INTO `permissions` VALUES (2,'ADD_DISH'),(3,'ADD_DISH_BRANCH'),(62,'ADD_EMPLOYEE'),(69,'ADD_FEEDBACK'),(4,'ADD_HALL'),(70,'ADD_HALL_PRICE\r '),(1,'ADD_NEW_BRANCH'),(6,'ADD_ORDER'),(5,'ADD_ORDER_DETAIL'),(7,'ADD_PERMISSION'),(8,'ADD_SERVICE'),(12,'ADD_USER'),(9,'ADD_USER_GROUP_PERMISSION'),(11,'ADD_USER_IN_GROUP'),(10,'ADD_USERS_GROUP'),(13,'ADD_WEDDING'),(14,'DELETE_BRANCH'),(15,'DELETE_DISH'),(16,'DELETE_DISHES_BRANCH'),(64,'DELETE_EMPLOYEE'),(17,'DELETE_HALL'),(68,'DELETE_HALL_PRICE'),(19,'DELETE_ORDER'),(18,'DELETE_ORDER_DETAIL'),(20,'DELETE_PERMISSION'),(21,'DELETE_SERVICES'),(25,'DELETE_USER'),(22,'DELETE_USER_GROUP_PERMISSION'),(24,'DELETE_USER_IN_GROUP'),(23,'DELETE_USERS_GROUP'),(26,'DELETE_WEDDING'),(27,'UPDATE_BRANCH'),(28,'UPDATE_DISH'),(29,'UPDATE_DISHES_BRANCH'),(63,'UPDATE_EMPLOYEE'),(31,'UPDATE_HALL'),(67,'UPDATE_HALL_PRICE'),(33,'UPDATE_ORDER'),(32,'UPDATE_ORDER_DETAILS'),(34,'UPDATE_PERMISSION'),(35,'UPDATE_SERVICE'),(66,'UPDATE_USER'),(37,'UPDATE_USER_GROUP'),(38,'UPDATE_USER_GROUP_PERMISSION'),(39,'UPDATE_USER_IN_GROUP'),(40,'UPDATE_USER_PERMISSION'),(71,'UPDATE_USERS_GROUP'),(41,'VIEW_DETAIL_WEDDING'),(60,'VIEW_EMPLOYEE_BY_ID'),(61,'VIEW_EMPLOYEE_BY_IDENTITY_NUMBER'),(42,'VIEW_LIST_BRANCH'),(43,'VIEW_LIST_DISH'),(44,'VIEW_LIST_DISH_BRANCH'),(59,'VIEW_LIST_EMPLOYEE'),(45,'VIEW_LIST_FEEDBACK'),(46,'VIEW_LIST_HALL'),(48,'VIEW_LIST_ORDER'),(47,'VIEW_LIST_ORDER_DETAILS'),(49,'VIEW_LIST_PERMISSION'),(50,'VIEW_LIST_SERVICE'),(55,'VIEW_LIST_USER'),(51,'VIEW_LIST_USER_GROUP_PERMISSION'),(53,'VIEW_LIST_USER_IN_GROUP'),(54,'VIEW_LIST_USER_PERMISSION'),(52,'VIEW_LIST_USERS_GROUP'),(57,'VIEW_LIST_WEDDING'),(56,'VIEW_LIST_WEDDING_PICTURE'),(58,'VIEW_STAT_VENUES'),(65,'VIEW_USER_BY_ID'),(30,'VIEW_USER_BY_NAME');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_prices`
--

DROP TABLE IF EXISTS `service_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_prices` (
  `id` int NOT NULL AUTO_INCREMENT,
  `wServiceId` int NOT NULL,
  `period` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `isAvailable` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_WEDDINGSERVICES_SERVICEPRICE_idx` (`wServiceId`),
  CONSTRAINT `FK_WEDDINGSERVICES_SERVICEPRICE` FOREIGN KEY (`wServiceId`) REFERENCES `wedding_services` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_prices`
--

LOCK TABLES `service_prices` WRITE;
/*!40000 ALTER TABLE `service_prices` DISABLE KEYS */;
INSERT INTO `service_prices` VALUES (1,1,'Ban ngay',120300,1),(3,13,'Buoi toi',121241242,1);
/*!40000 ALTER TABLE `service_prices` ENABLE KEYS */;
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
  `allows` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_PERMISSIONS_USERGROUPPERMISSION_idx` (`permissionId`),
  KEY `FK_USERGROUPS_USERGROUPPERMISSION_idx` (`groupId`),
  CONSTRAINT `FK_PERMISSIONS_USERGROUPPERMISSION` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FK_USERGROUPS_USERGROUPPERMISSION` FOREIGN KEY (`groupId`) REFERENCES `user_groups` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group_permission`
--

LOCK TABLES `user_group_permission` WRITE;
/*!40000 ALTER TABLE `user_group_permission` DISABLE KEYS */;
INSERT INTO `user_group_permission` VALUES (1,1,1,1),(2,1,2,1),(3,1,3,1),(4,1,4,1),(5,1,5,1),(6,1,6,1),(7,1,7,1),(8,1,8,1),(9,1,9,1),(10,1,10,1),(11,1,11,1),(12,1,12,1),(13,1,13,1),(14,1,14,1),(15,1,15,1),(16,1,16,1),(17,1,17,1),(18,1,18,1),(19,1,19,1),(20,1,20,1),(21,1,21,1),(22,1,22,1),(23,1,23,1),(24,1,24,1),(25,1,25,1),(26,1,26,1),(27,1,27,1),(28,1,28,1),(29,1,29,1),(30,1,30,1),(31,1,31,1),(32,1,32,1),(33,1,33,1),(34,1,34,1),(35,1,35,1),(37,1,37,1),(38,1,38,1),(39,1,39,1),(40,1,40,1),(41,1,41,1),(42,1,42,1),(43,1,43,1),(44,1,44,1),(45,1,45,1),(46,1,46,1),(47,1,47,1),(48,1,48,1),(49,1,49,1),(50,1,50,1),(51,1,51,1),(52,1,52,1),(53,1,53,1),(54,1,54,1),(55,1,55,1),(56,1,56,1),(57,1,57,1),(58,1,58,1),(59,1,59,1),(60,1,60,1),(61,1,61,1),(62,1,62,1),(63,1,63,1),(64,1,64,1),(65,1,65,1),(66,5,50,1),(67,5,46,1),(68,5,42,1),(69,5,43,1),(70,5,44,1),(71,5,45,1),(72,5,41,1),(73,5,66,1),(74,2,1,1),(75,2,2,1),(76,2,3,1),(77,2,4,1),(78,2,5,1),(79,2,6,1),(80,2,7,1),(81,2,8,1),(82,2,9,1),(83,2,10,1),(84,2,11,1),(85,2,12,1),(86,2,13,1),(87,2,27,1),(88,2,28,1),(89,2,29,1),(90,2,30,1),(91,2,31,1),(92,2,32,1),(93,2,33,1),(94,2,34,1),(95,2,35,1),(96,2,37,1),(97,2,38,1),(98,2,39,1),(99,2,40,1),(100,2,41,1),(101,2,42,1),(102,2,43,1),(103,2,44,1),(104,2,45,1),(105,2,46,1),(106,2,47,1),(107,2,48,1),(108,2,49,1),(109,2,50,1),(110,2,51,1),(111,2,52,1),(112,2,53,1),(113,2,54,1),(114,2,55,1),(115,2,56,1),(116,2,57,1),(117,2,59,1),(118,2,60,1),(119,2,61,1),(120,2,62,1),(121,2,63,1),(122,2,65,1),(123,2,66,1),(124,2,67,1),(125,2,68,1),(126,2,69,1),(127,2,70,1),(128,2,71,1),(129,1,66,1),(130,1,67,1),(131,1,68,1),(132,1,69,1),(133,1,70,1),(134,1,71,1),(135,4,50,1),(136,4,46,1),(137,4,42,1),(138,4,43,1),(139,4,44,1),(140,4,45,1),(141,4,41,1),(142,4,66,1);
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
  `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_groups`
--

LOCK TABLES `user_groups` WRITE;
/*!40000 ALTER TABLE `user_groups` DISABLE KEYS */;
INSERT INTO `user_groups` VALUES (1,'Administrators'),(2,'Managers'),(4,'Waiters'),(5,'Users');
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_in_group`
--

LOCK TABLES `user_in_group` WRITE;
/*!40000 ALTER TABLE `user_in_group` DISABLE KEYS */;
INSERT INTO `user_in_group` VALUES (16,1,1),(17,2,1),(18,3,1),(19,4,1),(22,19,5),(23,20,5),(24,21,5),(25,22,5),(26,23,5),(27,20,4),(28,19,4),(29,15,4);
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
  `allows` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_USERS_USERPERMISSION_idx` (`userId`),
  KEY `FK_PERMISSIONS_USERPERMISSION_idx` (`permissionId`),
  CONSTRAINT `FK_PERMISSIONS_USERPERMISSION` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FK_USERS_USERPERMISSION` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permission`
--

LOCK TABLES `user_permission` WRITE;
/*!40000 ALTER TABLE `user_permission` DISABLE KEYS */;
INSERT INTO `user_permission` VALUES (1,1,1,1),(2,1,2,1),(3,1,3,1),(4,1,4,1),(5,1,5,1),(6,1,6,1),(7,1,7,1),(8,1,8,1),(9,1,9,1),(11,1,11,1),(12,1,12,1),(13,1,13,1),(14,1,14,1),(15,1,15,1),(16,1,16,1),(17,1,17,1),(18,1,18,1),(19,1,19,1),(20,1,20,1),(21,1,21,1),(22,1,22,1),(23,1,23,1),(24,1,24,1),(25,1,25,1),(26,1,26,1),(27,1,27,1),(28,1,28,1),(29,1,29,1),(30,1,30,1),(31,1,31,1),(32,1,32,1),(33,1,33,1),(34,1,34,1),(35,1,35,1),(37,1,37,1),(38,1,38,1),(39,1,39,1),(40,1,40,1),(41,1,41,1),(42,1,42,1),(43,1,43,1),(44,1,44,1),(45,1,45,1),(46,1,46,1),(47,1,47,1),(48,1,48,1),(49,1,49,1),(50,1,50,1),(51,1,51,1),(52,1,52,1),(53,1,53,1),(54,1,54,1),(55,1,55,1),(56,1,56,1),(57,1,57,1),(58,1,58,1),(59,1,59,1),(60,1,60,1),(61,1,61,1),(62,1,62,1),(63,1,63,1),(64,1,64,1),(65,1,65,1),(66,1,66,1),(146,19,50,1),(147,19,46,1),(148,19,42,1),(149,19,43,1),(150,19,44,1),(151,19,45,1),(152,19,41,1),(153,19,66,1),(154,20,50,1),(155,20,46,1),(156,20,42,1),(157,20,43,1),(158,20,44,1),(159,20,45,1),(160,20,41,1),(161,20,66,1),(162,21,50,1),(163,21,46,1),(164,21,42,1),(165,21,43,1),(166,21,44,1),(167,21,45,1),(168,21,41,1),(169,21,66,1),(170,22,50,1),(171,22,46,1),(172,22,42,1),(173,22,43,1),(174,22,44,1),(175,22,45,1),(176,22,41,1),(177,22,66,1),(178,23,50,1),(179,23,46,1),(180,23,42,1),(181,23,43,1),(182,23,44,1),(183,23,45,1),(184,23,41,1),(185,23,66,1),(186,20,50,1),(187,20,46,1),(188,20,42,1),(189,20,43,1),(190,20,44,1),(191,20,45,1),(192,20,41,1),(193,20,66,1),(194,19,50,1),(195,19,46,1),(196,19,42,1),(197,19,43,1),(198,19,44,1),(199,19,45,1),(200,19,41,1),(201,19,66,1),(202,15,50,1),(203,15,46,1),(204,15,42,1),(205,15,43,1),(206,15,44,1),(207,15,45,1),(208,15,41,1),(209,15,66,1),(210,1,69,1);
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
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `phone` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `identityNumber` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `address` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `createdDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'sonhoang236@gmail.com','$2a$10$VyAbVFiN..yJElsk/EfJYueRX1IQmDYl0xV8CD4qcoJ6B7P.MAggi','SonHoang','0938927561','111111','123/23','https://res.cloudinary.com/dxxwcby8l/image/upload/v1694450714/hc2vaesexixowph3ddx4.jpg',1,'2023-08-14 22:42:19'),(2,'tung112@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','TungNguyen','0938999111','000012','42/213','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(3,'tu56@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','TuNguyen','0938099011','000002','55/2','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(4,'thinh53@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','ThinhNguyen','0938922145','000003','111/3','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(5,'nguyen666@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','NguyenTran','0938321455','000004','235/25','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(6,'khangg12@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','KhangNguyen','0938900111','000005','111/12','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(7,'dao255@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','DaoTran','0126471239','000006','99A','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(8,'thao142@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','ThaoBui','0927018312','000007','112B','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(9,'hung166@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','HungHoang','0938421412','000008','43A','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(10,'van325@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','VanLe','0938990001','000009','52A','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(11,'hau123@gmail.com','$2a$10$SaHRBzmrEpQGeTOyi1kp6.J.dkrqMQ7mfs36zlMWVghk00LNKk5By','HauNguyen','0938432533','000010','235/958678','https://res.cloudinary.com/dxxwcby8l/image/upload/v1694363373/qbre9zfmjda7f7i77duj.jpg',1,'2023-08-14 22:47:12'),(12,'hau632@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','HauLe','0938994752','000011','385/12','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(13,'duc66@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','DucLe','0938416222','000001','42/232','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-14 22:47:12'),(14,'tien321@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','TienNguyen','0938113222','111112','32/432','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-15 10:54:50'),(15,'huy43@gmail.com','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','HuynhHuy','0938775664','111113','99A','https://res.cloudinary.com/dt8p4xhzz/image/upload/v1694195525/default_rwvckq.png',1,'2023-08-15 10:56:49'),(19,'huythinh347@gmail.com','$2a$10$dbdL8ClT8ToI2rXlpiuhkuArlHgw/LhQd74nxGvzs/WgG9esxk9Wa','HuyThinh144','0938476077','000049','283/47',NULL,0,'2023-09-10 21:58:11'),(20,'SonHoangdas@gmail.com','$2a$10$sO2y8OxXqPdcC5tRrJG4R.DcaMNpVZobPIlcoLCKU476DZ2iaM0he','asdasd','1823122','123023','123','https://res.cloudinary.com/dxxwcby8l/image/upload/v1694459134/qsvclu6kgmkl6p04vl76.jpg',0,'2023-09-12 02:05:12'),(21,'SonHoangdasqwe@gmail.com','$2a$10$1v4YHkscf5Tt/2jXd1CB6OAcfNv.VfpyPUW5pbU9MI4atuS24ZNnS','asdasdqwe','182312230','123023123','123/123','https://res.cloudinary.com/dxxwcby8l/image/upload/v1694459197/ucpjfld7ey4l6czkijm3.jpg',0,'2023-09-12 02:06:17'),(22,'Sondasqwe@gmail.com','$2a$10$U9rIXF49K2gP9ZeKlO5aouTvSViqE894d6e26BAv6y8xa7DsdcmhC','An111231','0292641236','12421411','123/123','https://res.cloudinary.com/dxxwcby8l/image/upload/v1694459347/nvprxnopeph9pwdcbv2o.jpg',0,'2023-09-12 02:08:44'),(23,'SonHoangdddd@gmail.com','$2a$10$OwRphvZz9UPZ.nlyzm9WZe2Db86DiHcfwYG5VQ4GiC5jZ4gbbjeGS','dddddddd','1312312312','1231231231','dasd','https://res.cloudinary.com/dxxwcby8l/image/upload/v1694460199/kczn9sotgzhrwknfdu8g.jpg',0,'2023-09-12 02:23:17');
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
  `deposit` decimal(10,0) DEFAULT NULL,
  `totalLeft` decimal(10,0) DEFAULT NULL,
  `discount` decimal(10,0) NOT NULL DEFAULT '0',
  `paidVia` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `receiptNo` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `celebrityDate` datetime NOT NULL,
  `isCompleted` tinyint(1) NOT NULL,
  `tableNumber` int NOT NULL,
  `guestNumber` int NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `createdDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createdBy` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CUSTOMERS_WEDDING_idx` (`customerId`),
  KEY `FK_ORDERS_WEDDING_idx` (`orderId`),
  KEY `FK_USERS_WEDDING_idx` (`createdBy`),
  CONSTRAINT `FK_CUSTOMERS_WEDDING` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_ORDERS_WEDDING` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`),
  CONSTRAINT `FK_USERS_WEDDING` FOREIGN KEY (`createdBy`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wedding`
--

LOCK TABLES `wedding` WRITE;
/*!40000 ALTER TABLE `wedding` DISABLE KEYS */;
INSERT INTO `wedding` VALUES (5,50,1,0,543800,0,NULL,NULL,'2023-09-09 02:05:12',0,30,250,'Tiec cuoi phai lam dung gio','2023-09-09 11:46:33',NULL),(6,51,1,0,543800,0,NULL,NULL,'2023-09-09 02:05:12',0,30,250,'Tiec cuoi phai lam dung gio','2023-09-09 11:46:45',NULL),(7,52,1,0,543800,0,NULL,NULL,'2023-09-09 02:05:12',0,30,250,'Tiec cuoi phai lam dung gio','2023-09-09 11:47:36',NULL),(8,53,1,0,543800,0,NULL,NULL,'2023-09-09 02:05:12',0,30,250,'Tiec cuoi phai lam dung gio','2023-09-09 11:49:28',NULL),(9,54,1,0,543800,0,NULL,NULL,'2023-09-09 02:05:12',0,30,250,'Tiec cuoi phai lam dung gio','2023-09-09 11:50:21',NULL),(10,55,1,0,543800,0,NULL,NULL,'2023-09-09 02:05:12',0,30,250,'Tiec cuoi phai lam dung gio','2023-09-09 12:02:26',NULL),(11,56,1,0,543800,0,NULL,NULL,'2023-09-09 02:05:12',0,30,250,'Tiec cuoi phai lam dung gio','2023-09-09 12:05:54',NULL),(13,58,1,0,533800,20000,NULL,NULL,'2023-09-09 02:05:12',0,18,150,'Tiec cuoi phai lam dung gio 1','2023-09-09 12:37:55',NULL),(14,67,1,25000,518800,0,'Cash','242352353','2023-09-09 02:05:12',0,30,250,'Tiec cuoi phai lam dung gio','2023-09-09 16:18:59',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wedding_services`
--

LOCK TABLES `wedding_services` WRITE;
/*!40000 ALTER TABLE `wedding_services` DISABLE KEYS */;
INSERT INTO `wedding_services` VALUES (1,'Nghi thức khai tiệc',1,1),(2,'Tượng đá hoa đăng',1,1),(3,'Tranh cát',1,1),(4,'Đèn LED',1,1),(5,'Cổng hoa tươi',1,1),(6,'Album dàn trang',1,1),(7,'Hoa cầm tay',1,1),(8,'Camera chụp hình',1,1),(9,'Tranh ký  tên',1,1),(10,'Tiết mục khai tiệc',1,1),(11,'Khu vực chụp hình',1,1),(13,'Bong bóng',1,0);
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

-- Dump completed on 2023-09-12 14:11:11
