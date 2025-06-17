package com.GameRecord.big2.dto.request;

import java.util.List;

public class AddPlayerRequestDto {
	private String desk_name; // 對應 form 的 input name
	private List<String> playerName; // 對應 form 的多個玩家名稱欄位

	// Getter & Setter
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
