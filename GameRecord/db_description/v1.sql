CREATE DATABASE `games_record` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

-- game_record.t_games definition
CREATE TABLE `t_games` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `create_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- games_record.t_players definition
CREATE TABLE `t_players` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_name` varchar(100) DEFAULT NULL,
  `fk_games_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;