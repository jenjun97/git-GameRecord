package com.GameRecord.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_players")
@Data
public class PlayerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 對應 AUTO_INCREMENT
	private Integer id;
	private String playerName;
	private int fkDesk;
}
