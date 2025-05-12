package com.GameRecord.big2;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.GameRecord.utils.MyDateTimeUtil;

@Service
public class Big2Service {

	// 新增遊戲場次
	public int addNewGame(String gameTitle, List<String> players) {

		String playersAry = players.toArray().toString();
		return 0;
	}
}
