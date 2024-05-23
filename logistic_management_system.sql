-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.5-m3 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for logistics_management_system
CREATE DATABASE IF NOT EXISTS `logistics_management_system` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `logistics_management_system`;

-- Dumping structure for table logistics_management_system.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `CUSTOMER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS` varchar(255) NOT NULL,
  `CONTACT_NUMBER` varchar(255) NOT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `STATUS` varchar(255) NOT NULL,
  PRIMARY KEY (`CUSTOMER_ID`),
  UNIQUE KEY `CONTACT_NUMBER` (`CONTACT_NUMBER`),
  UNIQUE KEY `EMAIL` (`EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table logistics_management_system.customer: ~2 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`CUSTOMER_ID`, `ADDRESS`, `CONTACT_NUMBER`, `EMAIL`, `NAME`, `STATUS`) VALUES
	(1, 'jaffna', '0773754345', 'samn@gmail.com', 'saman', 'ACTIVE'),
	(2, 'Nugegoda', '0774323456', 'kamal@gmail.com', 'kamal gune', 'ACTIVE');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

-- Dumping structure for table logistics_management_system.ordertomanage
CREATE TABLE IF NOT EXISTS `ordertomanage` (
  `ORDER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime NOT NULL,
  `STATUS` varchar(255) NOT NULL,
  `CUSTOMER_ID_CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ORDER_ID`),
  KEY `FK_ORDERTOMANAGE_CUSTOMER_ID_CUSTOMER_ID` (`CUSTOMER_ID_CUSTOMER_ID`),
  CONSTRAINT `FK_ORDERTOMANAGE_CUSTOMER_ID_CUSTOMER_ID` FOREIGN KEY (`CUSTOMER_ID_CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table logistics_management_system.ordertomanage: ~2 rows (approximately)
/*!40000 ALTER TABLE `ordertomanage` DISABLE KEYS */;
INSERT INTO `ordertomanage` (`ORDER_ID`, `CREATED_AT`, `STATUS`, `CUSTOMER_ID_CUSTOMER_ID`) VALUES
	(1, '2024-05-13 00:00:00', 'PROCESSING', 1),
	(8, '2024-05-14 13:32:12', 'PENDING', 1);
/*!40000 ALTER TABLE `ordertomanage` ENABLE KEYS */;

-- Dumping structure for table logistics_management_system.product
CREATE TABLE IF NOT EXISTS `product` (
  `PRODUCT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  `TOTAL_WEIGHT` double NOT NULL,
  `ORDER_ORDER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`PRODUCT_ID`),
  KEY `FK_PRODUCT_ORDER_ORDER_ID` (`ORDER_ORDER_ID`),
  CONSTRAINT `FK_PRODUCT_ORDER_ORDER_ID` FOREIGN KEY (`ORDER_ORDER_ID`) REFERENCES `ordertomanage` (`ORDER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table logistics_management_system.product: ~2 rows (approximately)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`PRODUCT_ID`, `DESCRIPTION`, `NAME`, `QUANTITY`, `TOTAL_WEIGHT`, `ORDER_ORDER_ID`) VALUES
	(1, 'good condition', '64 inch Smart TV', 1, 5, 1),
	(8, 'used', 'samsung A13', 1, 0.3, 8);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- Dumping structure for table logistics_management_system.route
CREATE TABLE IF NOT EXISTS `route` (
  `ROUTE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DESTINATION` varchar(255) NOT NULL,
  `DISTANCE` double NOT NULL,
  `ESTIMATED_TIME` varchar(255) NOT NULL,
  `ORIGIN` varchar(255) NOT NULL,
  `START_DATE` datetime NOT NULL,
  PRIMARY KEY (`ROUTE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table logistics_management_system.route: ~2 rows (approximately)
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` (`ROUTE_ID`, `DESTINATION`, `DISTANCE`, `ESTIMATED_TIME`, `ORIGIN`, `START_DATE`) VALUES
	(1, 'Gampaha', 23, '01:30', 'Colombo', '2024-05-15 00:00:00'),
	(2, 'jaffna', 130, '48:00', 'colombo', '2024-05-15 19:02:03');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;

-- Dumping structure for table logistics_management_system.route_has_orders
CREATE TABLE IF NOT EXISTS `route_has_orders` (
  `order_id` bigint(20) NOT NULL,
  `route_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_id`,`route_id`),
  KEY `FK_route_has_orders_route_id` (`route_id`),
  CONSTRAINT `FK_route_has_orders_order_id` FOREIGN KEY (`order_id`) REFERENCES `ordertomanage` (`ORDER_ID`),
  CONSTRAINT `FK_route_has_orders_route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`ROUTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table logistics_management_system.route_has_orders: ~2 rows (approximately)
/*!40000 ALTER TABLE `route_has_orders` DISABLE KEYS */;
INSERT INTO `route_has_orders` (`order_id`, `route_id`) VALUES
	(1, 2),
	(8, 2);
/*!40000 ALTER TABLE `route_has_orders` ENABLE KEYS */;

-- Dumping structure for table logistics_management_system.route_has_vehicles
CREATE TABLE IF NOT EXISTS `route_has_vehicles` (
  `route_id` bigint(20) NOT NULL,
  `vehicle_id` bigint(20) NOT NULL,
  PRIMARY KEY (`route_id`,`vehicle_id`),
  KEY `FK_route_has_vehicles_vehicle_id` (`vehicle_id`),
  CONSTRAINT `FK_route_has_vehicles_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`VEHICLE_ID`),
  CONSTRAINT `FK_route_has_vehicles_route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`ROUTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table logistics_management_system.route_has_vehicles: ~1 rows (approximately)
/*!40000 ALTER TABLE `route_has_vehicles` DISABLE KEYS */;
INSERT INTO `route_has_vehicles` (`route_id`, `vehicle_id`) VALUES
	(2, 1);
/*!40000 ALTER TABLE `route_has_vehicles` ENABLE KEYS */;

-- Dumping structure for table logistics_management_system.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `registered_date` date DEFAULT NULL,
  `user_type_id` int(11) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `USER_TYPE_ID_USER_TYPE_idx` (`user_type_id`),
  CONSTRAINT `USER_TYPE_ID_USER_TYPE` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table logistics_management_system.user: ~2 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `email`, `username`, `password`, `registered_date`, `user_type_id`, `role`) VALUES
	(1, 'suji@gmail.com', 'suji', 'suji123', '2024-05-08', 1, 'all'),
	(4, 'nimal@gmail.com', 'nimal', '1234', '2024-05-12', 2, 'order manager');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table logistics_management_system.user_has_orders
CREATE TABLE IF NOT EXISTS `user_has_orders` (
  `id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`,`order_id`),
  KEY `FK_user_has_orders_order_id` (`order_id`),
  CONSTRAINT `FK_user_has_orders_order_id` FOREIGN KEY (`order_id`) REFERENCES `ordertomanage` (`ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table logistics_management_system.user_has_orders: ~2 rows (approximately)
/*!40000 ALTER TABLE `user_has_orders` DISABLE KEYS */;
INSERT INTO `user_has_orders` (`id`, `order_id`) VALUES
	(4, 1),
	(1, 8);
/*!40000 ALTER TABLE `user_has_orders` ENABLE KEYS */;

-- Dumping structure for table logistics_management_system.user_type
CREATE TABLE IF NOT EXISTS `user_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table logistics_management_system.user_type: ~2 rows (approximately)
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` (`id`, `name`) VALUES
	(1, 'admin'),
	(2, 'employee');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;

-- Dumping structure for table logistics_management_system.vehicle
CREATE TABLE IF NOT EXISTS `vehicle` (
  `VEHICLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CAPACITY` double NOT NULL,
  `STATUS` tinyint(1) NOT NULL DEFAULT '0',
  `TYPE` varchar(255) NOT NULL,
  PRIMARY KEY (`VEHICLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table logistics_management_system.vehicle: ~7 rows (approximately)
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` (`VEHICLE_ID`, `CAPACITY`, `STATUS`, `TYPE`) VALUES
	(1, 50, 0, 'SUV'),
	(2, 15, 1, 'MOTOR_CYCLE'),
	(3, 1000, 1, 'TRUCK'),
	(4, 500, 1, 'VAN'),
	(5, 200, 1, 'SEDAN'),
	(6, 15, 1, 'ELECTRIC_SCOOTER'),
	(7, 50, 1, 'SUV');
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
