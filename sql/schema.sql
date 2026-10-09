CREATE DATABASE IF NOT EXISTS board_db DEFAULT CHARACTER SET utf8mb4;
USE board_db;

CREATE TABLE `user` (
  `user_seq` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `user_pw` varchar(255) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'USER',
  `join_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_seq`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `board` (
  `board_seq` int NOT NULL AUTO_INCREMENT,
  `user_seq` int NOT NULL,
  `category` varchar(20) NOT NULL,
  `board_title` varchar(200) NOT NULL,
  `board_content` text NOT NULL,
  `view_count` int DEFAULT '0',
  `board_writedate` datetime DEFAULT CURRENT_TIMESTAMP,
  `board_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`board_seq`),
  KEY `user_seq` (`user_seq`),
  CONSTRAINT `board_ibfk_1` FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `comment` (
  `comment_seq` int NOT NULL AUTO_INCREMENT,
  `user_seq` int NOT NULL,
  `board_seq` int NOT NULL,
  `comment_content` text NOT NULL,
  `comment_writedate` datetime DEFAULT CURRENT_TIMESTAMP,
  `comment_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_seq`),
  KEY `user_seq` (`user_seq`),
  KEY `board_seq` (`board_seq`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`board_seq`) REFERENCES `board` (`board_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
