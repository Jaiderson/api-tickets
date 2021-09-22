CREATE DATABASE ticketDb;

CREATE TABLE `tickets` (
  `ticket_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime NOT NULL,
  `total_amount` double NOT NULL,
  PRIMARY KEY (`ticket_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `details` (
  `detail_id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `description` varchar(255) NOT NULL,
  `ticket_id` bigint NOT NULL,
  PRIMARY KEY (`detail_id`),
  KEY `FKgmx3tf85uavvfy920cfusk94q` (`ticket_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
