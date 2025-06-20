package com.GameRecord.big2.dto.response;

import java.sql.Timestamp;

public class HisDeskResponseDto {
	private String deskUuid;
	private String deskName;
	private Timestamp datetime;

	public String getDeskUuid() {
		return deskUuid;
	}

	public void setDeskUuid(String deskUuid) {
		this.deskUuid = deskUuid;
	}

	public String getDeskName() {
		return deskName;
	}

	public void setDeskName(String deskName) {
		this.deskName = deskName;
	}

	public Timestamp getDatetime() {
		return datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

}
