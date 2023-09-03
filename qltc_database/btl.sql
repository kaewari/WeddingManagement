CREATE DATABASE  IF NOT EXISTS `qltc` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `qltc`;
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
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*INSERT INTO `permissions` VALUES (1,'ADD_BRANCHES'),(2,'ADD_DISHES'),(3,'ADD_DISHES_BRANCHES'),(4,'ADD_HALLS'),(5,'ADD_ORDER_DETAILS'),(6,'ADD_ORDERS'),(7,'ADD_PERMISSION'),(8,'ADD_SERVICES'),(9,'ADD_USER_GROUP_PERMISSION',1),(10,'ADD_USER_GROUPS',1),(11,'ADD_USER_IN_GROUP',1),(12,'ADD_USERS',1),(13,'ADD_WEDDING',1),(14,'DELETE_BRANCHES',1),(15,'DELETE_DISHES',1),(16,'DELETE_DISHES_BRANCHES',1),(17,'DELETE_HALLS',1),(18,'DELETE_ORDER_DETAILS',1),(19,'DELETE_ORDERS',1),(20,'DELETE_PERMISSION',1),(21,'DELETE_SERVICES',1),(22,'DELETE_USER_GROUP_PERMISSION',1),(23,'DELETE_USER_GROUPS',1),(24,'DELETE_USER_IN_GROUP',1),(25,'DELETE_USERS',1),(26,'DELETE_WEDDING',1),(27,'EDIT_BRANCHES',1),(28,'EDIT_DISHES',1),(29,'EDIT_DISHES_BRANCH',1),(30,'EDIT_DISHES_BRANCHES',1),(31,'EDIT_HALLS',1),(32,'EDIT_ORDER_DETAILS',1),(33,'EDIT_ORDERS',1),(34,'EDIT_PERMISSIONS',1),(35,'EDIT_SERVICES',1),(36,'EDIT_USER',1),(37,'EDIT_USER_GROUP',1),(38,'EDIT_USER_GROUP_PERMISSION',1),(39,'EDIT_USER_IN_GROUP',1),(40,'EDIT_USER_PERMISSION',1),(41,'VIEW_DETAIL_WEDDING',1),(42,'VIEW_LIST_BRANCHES',1),(43,'VIEW_LIST_DISHES',1),(44,'VIEW_LIST_DISHES_BRANCH',1),(45,'VIEW_LIST_FEEDBACKS',1),(46,'VIEW_LIST_HALLS',1),(47,'VIEW_LIST_ORDER_DETAILS',1),(48,'VIEW_LIST_ORDERS',1),(49,'VIEW_LIST_PERMISSION',1),(50,'VIEW_LIST_SERVICES',1),(51,'VIEW_LIST_USER_GROUP_PERMISSION',1),(52,'VIEW_LIST_USER_GROUPS',1),(53,'VIEW_LIST_USER_IN_GROUP',1),(54,'VIEW_LIST_USER_PERMISSION',1),(55,'VIEW_LIST_USERS',1),(56,'VIEW_LIST_WEDDING_PICTURES',1),(57,'VIEW_LIST_WEDDINGS',1),(58,'VIEW_STAT_VENUES',1);
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;------
----------------------------------------------
---------------------- MAKE NEW HERE------------------------
----------------------------------------------------------------------------
-------------------------------------------------------------------------------
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
  `password` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `phone` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `identityNumber` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `address` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `createdDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'sonhoang236@gmail.com','123123','SonHoang','0938927561','111111','123/231',NULL,1,'2023-08-14 22:42:19'),(2,'tung112@gmail.com','111111','TungNguyen','0938999111','000012','42/213',NULL,1,'2023-08-14 22:47:12'),(3,'tu56@gmail.com','111111','TuNguyen','0938099011','000002','55/2',NULL,1,'2023-08-14 22:47:12'),(4,'thinh53@gmail.com','111111','ThinhNguyen','0938922145','000003','111/3',NULL,1,'2023-08-14 22:47:12'),(5,'nguyen666@gmail.com','111111','NguyenTran','0938321455','000004','235/25',NULL,1,'2023-08-14 22:47:12'),(6,'khangg12@gmail.com','111111','KhangNguyen','0938900111','000005','111/12',NULL,1,'2023-08-14 22:47:12'),(7,'dao255@gmail.com','111111','DaoTran','0126471239','000006','99A',NULL,1,'2023-08-14 22:47:12'),(8,'thao142@gmail.com','111111','ThaoBui','0927018312','000007','112B',NULL,1,'2023-08-14 22:47:12'),(9,'hung166@gmail.com','111111','HungHoang','0938421412','000008','43A',NULL,1,'2023-08-14 22:47:12'),(10,'van325@gmail.com','111111','VanLe','0938990001','000009','52A',NULL,1,'2023-08-14 22:47:12'),(11,'hau123@gmail.com','111111','HauNguen','0938432533','000010','46/12',NULL,1,'2023-08-14 22:47:12'),(12,'hau632@gmail.com','111111','HauLe','0938994752','000011','385/12',NULL,1,'2023-08-14 22:47:12'),(13,'duc66@gmail.com','111111','DucLe','0938416222','000001','42/232',NULL,1,'2023-08-14 22:47:12'),(14,'tien321@gmail.com','123123','TienNguyen','0938113222','111112','32/432',NULL,1,'2023-08-15 10:54:50'),(15,'huy43@gmail.com','123123','HuynhHuy','0938775664','111113','99A',NULL,1,'2023-08-15 10:56:49');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;




--
-- Table structure for table `user_permission`
--


DROP TABLE IF EXISTS `user_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `allows` tinyint(1) NOT NULL DEFAULT FALSE,
  `userId` int NOT NULL,
  `permissionId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USERS_USERPERMISSION_idx` (`userId`),
  KEY `FK_PERMISSIONS_USERPERMISSION_idx` (`permissionId`),
  CONSTRAINT `FK_PERMISSIONS_USERPERMISSION` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FK_USERS_USERPERMISSION` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permission`
--

LOCK TABLES `user_permission` WRITE;
/*!40000 ALTER TABLE `user_permission` DISABLE KEYS */;
INSERT INTO `user_permission` VALUES (1,1,1,1),(2,1,1,2),(3,1,1,3),(4,1,1,4),(5,1,1,5),(6,1,1,6),(7,1,1,7);
/*!40000 ALTER TABLE `user_permission` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_groups`
--

LOCK TABLES `user_groups` WRITE;
/*!40000 ALTER TABLE `user_groups` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_in_group`
--

LOCK TABLES `user_in_group` WRITE;
/*!40000 ALTER TABLE `user_in_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_in_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group_permission`
--

DROP TABLE IF EXISTS `user_group_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_group_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `allows` tinyint(1) NOT NULL DEFAULT FALSE,
  `groupId` int NOT NULL,
  `permissionId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PERMISSIONS_USERGROUPPERMISSION_idx` (`permissionId`),
  KEY `FK_USERGROUPS_USERGROUPPERMISSION_idx` (`groupId`),
  CONSTRAINT `FK_PERMISSIONS_USERGROUPPERMISSION` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FK_USERGROUPS_USERGROUPPERMISSION` FOREIGN KEY (`groupId`) REFERENCES `user_groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group_permission`
--

LOCK TABLES `user_group_permission` WRITE;
/*!40000 ALTER TABLE `user_group_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_group_permission` ENABLE KEYS */;
UNLOCK TABLES;


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
  `createdDate` datetime DEFAULT CURRENT_TIMESTAMP,
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
INSERT INTO `customer_feedbacks` VALUES (1,1,'Nhân Viên','Good','2020-10-01 08:55:00','thanks',1),(2,2,'Nhân Viên','Nice','2020-15-01 09:55:00','thank you',1);
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
  `discount` float NOT NULL,
  `unit` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `type` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `wOnly` tinyint(1) NOT NULL,
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `isAvailable` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (1,'Súp gà',100000,10,'dĩa','1',1,'111',1),(2,'Gà hấp lá chanh',150000,8,'dĩa','1',1,'111',1),(3,'Súp bí đỏ kem nấm',80000,5,'chén','1',1,'111',1),(4,'Khai vị 3 món',50000,10,'dĩa','1',1,'111',1),(5,'Gỏi gà nước bạc hà',70000,10,'dĩa','1',1,'111',1),(6,'Sò huyết tứ xuyên',70000,10,'dĩa','1',1,'111',1),(7,'Tôm sốt phô mai',70000,10,'dĩa','1',1,'111',1),(8,'Bánh Filo nhân vịt',60000,10,'dĩa','1',1,'111',1),(9,'Súp bắp non gà xé',30000,10,'dĩa','1',1,'111',1),(10,'Bánh kem bắp',40000,10,'dĩa ','1',1,'111',1),(11,'Chả giò tôm',40000,10,'dĩa','1',1,'111',1),(12,'Sườn heo nướng mật ông',50000,8,'dĩa','1',1,'111',1),(13,'Súp hào nấm tuyết',70000,9,'thố','1',1,'111',1),(14,'Đậu hủ hạnh nhân',50000,10,'dĩa','1',1,'111',1),(15,'Sò điêp sốt bacon',60000,11,'dĩa','1',1,'111',1),(16,'Cơm chiên hải sản',80000,10,'dĩa','1',1,'111',1),(17,'Cơm chiên dương châu',80000,10,'dĩa','1',1,'111',1),(18,'Bánh flan',10000,5,'hũ','1',1,'111',1),(19,'Rau câu',10000,5,'hũ','1',1,'111',1),(20,'Pepsi',10000,0,'lon','1',1,'111',1),(21,'Bia',15000,0,'lon','1',1,'111',1);
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes_in_branch`
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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes_in_branch`
--

LOCK TABLES `dish_in_branch` WRITE;
/*!40000 ALTER TABLE `dish_in_branch` DISABLE KEYS */;
INSERT INTO `dish_in_branch` VALUES (1,1,1,1),(2,2,2,1),(3,1,3,1),(4,1,4,1),(5,3,5,1),(6,1,6,1),(7,1,7,1),(8,2,8,1),(9,3,9,1),(10,2,10,1),(11,1,11,1),(12,3,12,1),(13,3,13,1),(14,2,14,1),(15,3,15,1),(16,1,16,1),(17,3,17,1),(18,3,18,1),(19,3,19,1),(20,1,20,1),(21,2,21,1),(22,1,21,1),(23,3,21,1),(24,1,8,1);
/*!40000 ALTER TABLE `dish_in_branch` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,1,'Hoang','Son','111111','Admin',1),(2,14,'Nguyen','Tien','111112','Admin',2),(3,15,'Huynh','Huy','111113','Manager',3);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `halls`
--

LOCK TABLES `halls` WRITE;
/*!40000 ALTER TABLE `halls` DISABLE KEYS */;
INSERT INTO `halls` VALUES (1,'Sảnh Platin','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',25,250,'2020-01-01 00:00:00',1,1,1),(2,'Sảnh Venus','Sảnh Platin với kích thước 386 m2 được bày trí theo phong cách Châu Âu sang trọng sẽ thật sự phù hợp cho một hôn lễ ấm cúng với số lượng khách mời trong khoảng 20 đến 26 bàn tiệc. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',40,400,1,'2020-01-01 00:00:00',1,1),(3,'Sảnh Elite','Sảnh Elite với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền vàng ấn tượng. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',30,300,2,'2020-01-01 00:00:00',1,1),(4,'Sảnh Mercury','Sảnh Elite với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền vàng ấn tượng. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 1500 khách (150 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',40,400,2,'2020-01-01 00:00:00',1,1),(5,'Sảnh Pavilon','Sảnh Pavilon với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền xanh độc đáo. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 850 khách (85 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',40,500,3,'2020-01-01 00:00:00',1,1),(6,'Sảnh Fontana','Sảnh Pavilon với kích thước sảnh 544 m2 được bày trí theo phong cách Châu Âu sang trọng có sức chứa từ 30 đến 40 bàn tiệc trên nền xanh độc đáo. Diện tích sảnh có thể mở rộng linh hoạt với sức chứa lên đến 850 khách (85 bàn) nhằm đáp ứng cho nhiều hình thức và nhu cầu đặt tiệc khác nhau.',35,400,3,'2020-01-01 00:00:00',1,1);
/*!40000 ALTER TABLE `halls` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_prices`
--

LOCK TABLES `service_prices` WRITE;
/*!40000 ALTER TABLE `service_prices` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_prices` ENABLE KEYS */;
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
  `receiptNo` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `paidVia` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `employeeId` int NOT NULL,
  `note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `createdDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_CUSTOMERS_ORDERS_idx` (`customerId`),
  KEY `FK_EMPLOYEES_ORDERS_idx` (`employeeId`),
  CONSTRAINT `FK_CUSTOMERS_ORDERS` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_EMPLOYEES_ORDERS` FOREIGN KEY (`employeeId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
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
  `discount` decimal NOT NULL DEFAULT 0,
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
-- Table structure for table `order_dishes_details`
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_dishes_details`
--

LOCK TABLES `order_dish_details` WRITE;
/*!40000 ALTER TABLE `order_dish_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_dish_details` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `orders_halls_details`
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_halls_details`
--

LOCK TABLES `order_halls_details` WRITE;
/*!40000 ALTER TABLE `order_halls_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_halls_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_services_details`
--

DROP TABLE IF EXISTS `order_services_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_services_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int NOT NULL,
  `servicePriceId` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `price` decimal NOT NULL,
  `note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ORDERS_ORDERSSERVICEDETAILS_idx` (`orderId`),
  KEY `FK_SERVICES_ORDERSSERVICEDETAILS_idx` (`servicePriceId`),
  CONSTRAINT `FK_ORDERS_ORDERSSERVICEDETAILS` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_SERVICES_ORDERSSERVICEDETAILS` FOREIGN KEY (`servicePriceId`) REFERENCES `service_prices` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_services_details`
--

LOCK TABLES `order_services_details` WRITE;
/*!40000 ALTER TABLE `order_services_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_services_details` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
