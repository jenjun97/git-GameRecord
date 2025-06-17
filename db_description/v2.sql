CREATE DATABASE `games_score` 
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ 
/*!80016 DEFAULT ENCRYPTION='N' */;

-- games_score.t_desk definition
CREATE TABLE `t_desk` (
  `id` int NOT NULL AUTO_INCREMENT,
  `desk_uuid` varchar(100) DEFAULT NULL,
  `desk_name` varchar(255) DEFAULT NULL,
  `kind_name` varchar(100) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- games_score.t_player definition
CREATE TABLE `t_player` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_num` int DEFAULT NULL,
  `player_name` varchar(100) DEFAULT NULL,
  `fk_desk_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- games_score.t_score definition
CREATE TABLE `t_score` (
  `id` int NOT NULL AUTO_INCREMENT,
  `run_index` int DEFAULT NULL,
  `fk_player_id` int DEFAULT NULL,
  `score` int DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;