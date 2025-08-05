package com.GameRecord.sample.dto.request;

import java.util.List;

public class AddScoreRequestDto {
	// 場次代號
	private String deskUuid;

	// 分數
	private List<Integer> score;

	// set & get
	public String getDeskUuid() {
		return deskUuid;
	}

	public void setDeskUuid(String deskUuid) {
		this.deskUuid = deskUuid;
	}

	public List<Integer> getScore() {
		return score;
	}

	public void setScore(List<Integer> score) {
		this.score = score;
	}

}
