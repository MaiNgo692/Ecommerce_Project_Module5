CREATE DATABASE  IF NOT EXISTS `ecommerce` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ecommerce`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: ecommerce
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `address_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `full_address` varchar(255) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `receive_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,21,'134 Hồ Tùng Mậu, Nam Từ Liêm, Hà Nội','0346589742','Ngô Thị Mai'),(2,21,'15 Cầu Giấy, Hà Nội','0894567215','Trương Thị Lựa'),(3,21,'171 Xã đàn, Đống đa , Hà Nội','0998378267','hunghx');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  `description` text,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Máy tính bàn','Dầy đủ các hãng nổi tiếng',_binary ''),(2,'Màn hình','Độ phân giải cao sắc nét',_binary ''),(3,'Linh kiện máy tính','Thời trang phong cách trẻ trung',_binary '\0'),(4,'Thiết bị lưu trữ','Đầy đủ dung lượng theo nhu cầu khách hàng',_binary ''),(5,'Máy In, Máy Scan, Máy Chiếu','Bảo hành 1 năm đổi trả miễn phí',_binary ''),(6,'Phụ kiện máy tính','Đầy đủ phụ kiện chính hãng',_binary ''),(7,'Laptop','Đa dạng chủng loại, giá cả hợp lý',_binary '');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `unit_price` decimal(10,2) DEFAULT NULL,
  `order_quantity` int DEFAULT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `order_details_chk_1` CHECK ((`order_quantity` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (15,10,'Ổ cứng HDD 18TB, 16TB,14TB',4880000.00,2),(15,16,'Bàn phím Bluetooth Logitech K380s ',789000.00,1),(16,13,'Máy chiếu mini HY300 ',1519000.00,1),(16,14,'Máy In Nhiệt AYIN ',1750000.00,1),(17,14,'Máy In Nhiệt AYIN ',1750000.00,1),(18,14,'Máy In Nhiệt AYIN ',1750000.00,20);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `serial_number` varchar(100) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `status` enum('WAITING','CONFIRM','DELIVERY','SUCCESS','CANCEL','DENIED') DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `receive_name` varchar(100) DEFAULT NULL,
  `receive_address` varchar(255) DEFAULT NULL,
  `receive_phone` varchar(15) DEFAULT NULL,
  `created_at` date DEFAULT (curdate()),
  `receive_at` date DEFAULT ((curdate() + interval 4 day)),
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (15,'44b1c394-c040-4995-8257-4a543e25d94c',21,10549000.00,'SUCCESS',NULL,'Ngô Thị Mai','134 Hồ Tùng Mậu, Nam Từ Liêm, Hà Nội','0346589742','2024-05-01','2024-05-05'),(16,'96470a2d-cfca-406e-bd7a-00a6e606c834',21,3269000.00,'DELIVERY',NULL,'Ngô Thị Mai','134 Hồ Tùng Mậu, Nam Từ Liêm, Hà Nội','0346589742','2024-05-01','2024-05-05'),(17,'9bb835a7-3d04-4bed-9c83-41cc0f36e18d',21,1750000.00,'CANCEL',NULL,'Ngô Thị Mai','134 Hồ Tùng Mậu, Nam Từ Liêm, Hà Nội','0346589742','2024-05-11','2024-05-15'),(18,'acfd7e71-15a5-4d90-a5c2-c5373106eca8',21,35000000.00,'WAITING',NULL,'Ngô Thị Mai','134 Hồ Tùng Mậu, Nam Từ Liêm, Hà Nội','0346589742','2024-05-13','2024-05-17');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` bigint NOT NULL AUTO_INCREMENT,
  `sku` varchar(100) DEFAULT NULL,
  `product_name` varchar(100) NOT NULL,
  `description` text,
  `unit_price` decimal(10,2) DEFAULT NULL,
  `stock_quantity` int DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `created_at` date DEFAULT (curdate()),
  `update_at` date DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_name` (`product_name`),
  UNIQUE KEY `sku` (`sku`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `products_chk_1` CHECK ((`stock_quantity` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'0dbdcd29-6347-4eb6-b7d5-8c3b903737d7','PC Beelink SER7 Max','Máy tính mini (R7 7840HS / 32GB / 1TB/Win11)',15000000.00,89,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/mai.jpg',1,'2024-04-01','2024-05-13'),(2,'6f19aa3b-20e2-4358-a2aa-3fd9004a180e','Bộ máy tính văn phòng G41','Bộ máy tính văn phòng G41 /4GB /250GB màn hình 19 inch',8600000.00,0,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/test1.jpg',1,'2024-04-27',NULL),(3,'15fe06e4-3798-4268-92e1-12653d1ebcf7','Case Máy Tính Bộ DELL Optilex 3020 SFF ','Case Máy Tính Bộ DELL Optilex 3020 SFF Core i7 i5 i3 , Ram 8G, SSD 240GB, SSD 240GB ( Bảo hành 1 năm)',6000000.00,70,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/test1.jpg',1,'2024-04-28',NULL),(4,'8dd5538a-3e56-41fb-9918-a72e014e52d4','Màn hình VSP V2407S 24inch FHD IPS 75Hz HDMI/VGA','Hàng chính hãng',5100000.00,50,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/',2,'2024-05-03',NULL),(5,'11703698-8e31-44f7-9a57-3f0726908ab5','Màn Hình Di Động - [Rẻ Vô Địch] - Full HD IPS ','Kết nối Type-C và HDMI dùng cho điện thoại, máy tính, máy chơi game',2250000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Màn%20Hình%20Di%20Động%20-%20%5BRẻ%20Vô%20Địch%5D%20-%20Full%20HD%20IPS%20.jpg',2,'2024-05-03',NULL),(6,'dd8a648b-3459-4420-b486-c5fe6dc25db5','Màn Hình E-DRA EGM22F75 EGM24F75 EGM24F100S','EGM22F75P EGM24F100P (22\"\"-24\"\"/FHD/IPS/75Hz-100Hz/5ms-1ms/Vesa) Chính Hãng',1915000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Màn%20Hình%20Di%20Động%20-%20%5BRẻ%20Vô%20Địch%5D%20-%20Full%20HD%20IPS%20.jpg',2,'2024-05-04',NULL),(7,'00f898b6-5e64-4b17-a458-0b246787a0c4','RAM máy tính bàn,ram DDR3','PC BUSS1600,DDR 3 2G/4G/8G buss 1333 hàng bóc máy.Ram DDr4 4G buss 2133',159000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/RAM%20máy%20tính%20bàn,ram%20DDR3.jpg',3,'2024-05-05',NULL),(8,'d32f2647-ed1b-47b7-9e17-a5db4f462291','VGA MSI RX470 4G','Hàng đẹp như mới-Cạc cắm tự nhận',800000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/VGA%20MSI%20RX470%204G%20.jpg',3,'2024-05-05',NULL),(9,'1f0ca9d1-a605-4e1f-bef6-52a6f980fdb5','Main X99 DDR3 DDR4','Hàng mới không hộp',820000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Main%20X99%20DDR3%20DDR4.jpg',3,'2024-05-05',NULL),(10,'c798f63e-8794-41a8-8060-3dac8318ab49','Ổ cứng HDD 18TB, 16TB,14TB','HÀNG CHÍNH HÃNG TẶNG CÁP SATA, ỐC LẮP HDD',4880000.00,98,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Ổ%20cứng%20HDD%2018TB,%2016TB,14TB.jpg',4,'2024-05-06',NULL),(11,'85be147f-8576-49f3-b41d-01bc542dfb38','Ổ cứng SSD Kingston NV2 NVMe','Gen 4x4 PCIe 4.0 M.2 tốc độ đọc/ghi lên tới 3.500/2.800 MB/giây - Hàng chính hãng',1260000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Ổ%20cứng%20SSD%20Kingston%20NV2%20NVMe%20.jpg',4,'2024-05-06',NULL),(12,'b01c01b4-aafd-4bda-bbeb-d29a52702c67','Ổ cứng SSD Kingston A400','( 120GB / 240GB / 480GB ) 2.5\"\" SATA 3.0 6GB/giây - Hàng chính hãng',1219000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Ổ%20cứng%20SSD%20Kingston%20A400.jpg',4,'2024-05-06',NULL),(13,'5f05ea85-7fab-4095-bdf0-d8c2a5d82327','Máy chiếu mini HY300 ','Full HD android 11.0 độ sáng 8000 lumens Bluetooth 5.0 ít tiếng ồn bảo hành 5 năm máy chiếu phim',1519000.00,99,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Máy%20chiếu%20mini%20HY300%20.jpg',5,'2024-05-06',NULL),(14,'26329104-3c7c-40c7-b26e-77fba1de2d08','Máy In Nhiệt AYIN ','In Khổ A6,A7 In Đơn Hàng, Phiếu Gửi, Minicode, Logo Tự Dán, Bảo Hành 15 Tháng',1750000.00,96,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Máy%20In%20Nhiệt%20AYIN%20.jpg',5,'2024-05-07','2024-05-13'),(15,'3288ef68-dbc1-481d-b005-d039cfc46247','Máy chiếu mini XMAX10','chính hãng Full HD, chất lượng hình ảnh 4K siêu nét, hàng chính hãng, bh 36 tháng',5789000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Máy%20chiếu%20mini%20XMAX10.png',5,'2024-05-07',NULL),(16,'5c83435f-fcb5-4535-aa99-c28862316a7d','Bàn phím Bluetooth Logitech K380s ','Pebble 2 - Đa thiết bị, Phím tắt tùy chỉnh, Mỏng nhẹ, Easy-Switch',789000.00,99,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Bàn%20phím%20Bluetooth%20Logitech%20K380s%20.jpg',6,'2024-05-07',NULL),(17,'8027a2f0-3ec8-4742-a99f-2e88eeed7f42','Chuột Không Dây LOGITECH G304 - 12000 DPI ','Pin 250 Giờ - LightSpeed - Chính Hãng Giá Rẻ',790000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Chuột%20Không%20Dây%20LOGITECH%20G304%20-%2012000%20DPI%20.jpg',6,'2024-05-08',NULL),(18,'ba535431-0636-41ae-8734-a4b354a11b4e','Giá đỡ LAPTOP, MACBOOK, IPAD ','bằng nhôm có thể điều chỉnh được độ cao, đế tản nhiệt kê laptop nhôm',69000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Giá%20đỡ%20LAPTOP,%20MACBOOK,%20IPAD%20.jpg',6,'2024-05-08',NULL),(19,'8837f50e-f4fa-4fd4-809b-68fe4ffe82d0','[Window 10 SSD] Laptop Asus X201EP X201EV','siêu mỏng nhẹ kiểu dáng đẹp',1500000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/%5BWindow%2010%20SSD%5D%20Laptop%20Asus%20X201EP%20X201EV.jpg',7,'2024-05-08',NULL),(20,'ae45774b-cda4-48f0-8334-d01fcb6f09b1','Dell XPS Plus 9320','l i7-1260P l RAM 16GB l SSD 512GB l 13\' QHD 3,5K Touch [ BẢO HÀNH 3 - 12 THÁNG ]',25900000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/Dell%20XPS%20Plus%209320.jpg',7,'2024-05-13',NULL),(21,'a85adfd9-1d83-4a0b-9b11-d6a3fa835738','[New 2023] Laptop Asus Zenbook 14 Q420VA ','(i7-13700H, 16GB, SSD 512GB, Màn 14.5\' 2.8K, 120Hz OLED)',21590000.00,100,'file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/%5BNew%202023%5D%20Laptop%20Asus%20Zenbook%2014%20Q420VA%20.jpg',7,'2024-05-12',NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` enum('ROLE_ADMIN','ROLE_USER') DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoping_cart`
--

DROP TABLE IF EXISTS `shoping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shoping_cart` (
  `shoping_cart_id` int NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `order_quantity` int DEFAULT NULL,
  PRIMARY KEY (`shoping_cart_id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `shoping_cart_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `shoping_cart_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `shoping_cart_chk_1` CHECK ((`order_quantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoping_cart`
--

LOCK TABLES `shoping_cart` WRITE;
/*!40000 ALTER TABLE `shoping_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `shoping_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (21,1),(21,2),(24,2),(25,2),(26,2),(27,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullname` varchar(100) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `phone` varchar(15) NOT NULL,
  `address` varchar(255) NOT NULL,
  `created_at` date DEFAULT (curdate()),
  `update_at` date DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (21,'maingo1','ngomai@gmail.com','Ngô Thị Mai',_binary '','$2a$10$wTsJvPhsIdmTEH.KczSOPuBwRpqkfNQoq880gKC2n2vmQWAd6700u','file:/D:/10.Rikkei/Fukuoka_Module5/Ecommerce/uploads/mai2.jpg','0345678956','Quận Tân Bình, TP Hồ Chí Minh','2024-04-24','2024-04-24'),(24,'maingo','mai1@gmail.com','Ngô Thị Mai 1',_binary '','$2a$10$EVlAoHXxlOjxnOfWtqTb.OTs0YZ99avHr6f1O8vOHFxq.E3hO.d6W',NULL,'0346197826','Hà Nội','2024-04-26','2024-04-26'),(25,'hanguyen','hanguyen@gmail.com','Nguyễn Út Hà',_binary '','$2a$10$U/jS0wYgLkHsF06/D6LsB.L6h3bQyP2mUBVwQ7EdD3yhP5b8VNttq',NULL,'0346197816','Bắc Giang','2024-04-29','2024-04-29'),(26,'hoaivo','hoaivo@gmail.com','Võ Thị Hoài',_binary '','$2a$10$XOKbiD/wHiSKWU1pbrJwduGZ4EtGUuK40EtDRa/BD1i1d7i79QeHW',NULL,'0346197896','Hà Tĩnh','2024-04-29','2024-04-29'),(27,'tungle','tungle@gmail.com','Lê Xuân Tùng',_binary '','$2a$10$lY.iw24v6i/5iPJNqsf6cuFmcrbjWxc.WZD0S4PPAZxjWEDb89FJO',NULL,'0346197886','Ninh Bình','2024-04-29','2024-04-29');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish_list`
--

DROP TABLE IF EXISTS `wish_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wish_list` (
  `wish_list_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`wish_list_id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `wish_list_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `wish_list_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish_list`
--

LOCK TABLES `wish_list` WRITE;
/*!40000 ALTER TABLE `wish_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `wish_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-13 20:03:01
