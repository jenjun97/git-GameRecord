CREATE DATABASE `games_score` 
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ 
/*!80016 DEFAULT ENCRYPTION='N' */;

-- games_score.t_desk definition
CREATE TABLE `t_desk` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) DEFAULT NULL,
  `desk_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `datime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- games_score.t_players definition
CREATE TABLE `t_players` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_name` varchar(255) DEFAULT NULL,
  `fk_desk` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- games_score.t_score definition
CREATE TABLE `t_score` (
  `id` int NOT NULL AUTO_INCREMENT,
  `round` int DEFAULT NULL,
  `fk_players` int DEFAULT NULL,
  `score` int DEFAULT NULL,
  `datime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;