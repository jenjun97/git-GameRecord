-- 創建 DB
CREATE DATABASE `games_record`

/*----------------------
Table name : t_games
Description : 遊戲資訊
----------------------*/
CREATE TABLE `t_games` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `uuid` varchar(100) DEFAULT NULL COMMENT '識別碼',
  `type` varchar(100) DEFAULT NULL COMMENT '遊戲類型',
  `place` varchar(100) DEFAULT NULL COMMENT '遊戲地點',
  `datetime` datetime DEFAULT NULL COMMENT '遊戲日期時間',
  `del` INT DEFAULT NULL COMMENT '刪除',
  CONSTRAINT `t_players_id_pk` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*----------------------
Table name : t_players
Description : 玩家資訊
----------------------*/
CREATE TABLE `t_players` (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`name` varchar(100) NULL COMMENT '玩家名稱',
	`fk_games_id` INT NULL COMMENT '外鍵t_games id',
	CONSTRAINT `t_players_id_pk` PRIMARY KEY (`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*----------------------
Table name : t_score
Description : 分數資訊
----------------------*/
CREATE TABLE games_record.t_score (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`round` INT NULL COMMENT '回合',
	`fk_players_id` INT NULL COMMENT '外鍵t_players id',
	`datetime` DATETIME NULL COMMENT '分數日期時間',
	CONSTRAINT `t_score_id_pk` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

