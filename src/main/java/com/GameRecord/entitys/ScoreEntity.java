package com.GameRecord.entitys;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_score")
@Data
public class ScoreEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 對應 AUTO_INCREMENT
	private Integer id;
	private Integer round;
	@Column(name = "fk_players")
	private Integer fkPlayers;
	private Integer score;
	private Timestamp datime;
}
