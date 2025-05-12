package com.GameRecord.big2;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GameRecord.utils.MyDateTimeUtil;

@Service
public class Big2Service {

	@Autowired
	Big2Dao big2Dao;

	// 新增遊戲場次
	public void addNewGame(String gameTitle, List<String> players) {
		
		// 防呆檢查
		
		// 新增場次資料
		Timestamp nowDatetime = MyDateTimeUtil.getNowTimestamp();
		int gamesId = big2Dao.createBigGame("Big2", gameTitle, nowDatetime);

		// 新增玩家資料
		// 取回玩家id
		
		// 組玩家models
		// playerId
		// playerName
		// Lsit <record model> no_num record
		

	}
}
