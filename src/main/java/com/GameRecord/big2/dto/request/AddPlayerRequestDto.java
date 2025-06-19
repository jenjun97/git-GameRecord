package com.GameRecord.big2.dto.request;

import java.util.List;

public class AddPlayerRequestDto {
	// 場次名稱
	private String desk_name;

	// 玩家姓名
	private List<String> playerName;

	// set & get
	public String getDesk_name() {
		return desk_name;
	}

	public void setDesk_name(String desk_name) {
		this.desk_name = desk_name;
	}

	public List<String> getPlayerName() {
		return playerName;
	}

	public void setPlayerName(List<String> playerName) {
		this.playerName = playerName;
	}
}
