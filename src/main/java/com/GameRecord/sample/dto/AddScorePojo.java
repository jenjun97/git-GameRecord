package com.GameRecord.sample.dto;

public class AddScorePojo {
	// 本次第幾把的號次
	public int roundNo;
	
	// 玩家id
	public int fkPlayerId;
	
	// 分數
	public int score;

	// 有參建構子
	public AddScorePojo(int roundNo, int fkPlayerId, int score) {
		this.roundNo = roundNo;
		this.fkPlayerId = fkPlayerId;
		this.score = score;
	}
}
