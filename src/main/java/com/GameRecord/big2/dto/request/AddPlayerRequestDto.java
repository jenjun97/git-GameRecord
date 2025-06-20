package com.GameRecord.big2.dto.request;

import java.util.List;

public class AddPlayerRequestDto {
	// 場次名稱
	private String deskName;

	// 玩家姓名
	private List<String> playerName;

	// set & get
	public List<String> getPlayerName() {
		return playerName;
	}

	public String getDeskName() {
		return deskName;
	}

	public void setDeskName(String deskName) {
		this.deskName = deskName;
	}

	public void setPlayerName(List<String> playerName) {
		this.playerName = playerName;
	}
}
