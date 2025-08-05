package com.GameRecord.sample.dto.response;

import java.util.List;

public class ScoreboardResponseDto {
	// 場次識別碼
	public String deskUuid;

	// query玩家姓名
	public List<String> playerNameList;
	
	// query玩家每把分數
	public List<List<Integer>> roundScoreList;
	
	// query玩家總分
	public List<Integer> sumScoreList;
	
	

}
