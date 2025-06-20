package com.GameRecord.big2.dto.request;

import java.util.List;

public class AddRecordRequestDto {
	private String deskUuid;
	
	private List<Integer> score;

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
