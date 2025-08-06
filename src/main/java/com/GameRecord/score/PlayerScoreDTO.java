package com.GameRecord.score;

import lombok.Data;

@Data
public class PlayerScoreDTO {
	private Integer playerId;
	private Integer round;
	private Integer score;
	
	public PlayerScoreDTO(Integer playerId, Integer round, Integer score) {
		this.playerId = playerId;
		this.round = round;
		this.score = score;
	}
}
