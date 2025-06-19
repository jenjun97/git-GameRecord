package com.GameRecord.big2.dto.response;

public class RoundScoreDto {
	// 第幾把
	private int roundNo;

	// 玩家id
	private int playerId;

	// 玩家分數
	private int score;

	// set & get
	public int getRoundNo() {
		return roundNo;
	}

	public void setRoundNo(int roundNo) {
		this.roundNo = roundNo;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
