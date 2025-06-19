package com.GameRecord.big2;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GameRecord.big2.dto.request.AddPlayerRequestDto;
import com.GameRecord.utils.MyDateTimeUtil;
import com.GameRecord.utils.MySourceProperties;

/**
 * 新增場次及玩家
 */
@Service
public class Big2AddPlayerService {

	@Autowired
	Big2Dao big2Dao;

	// 新增場次及玩家
	public int addBig2Game(AddPlayerRequestDto requestDto) throws Exception {
		// 防呆
		validates(requestDto.getPlayerName());

		// 新增場次，取得id
		UUID uuid = UUID.randomUUID();
		int deskId = big2Dao.addDesk(UUID.randomUUID().toString(), requestDto.getDesk_name(),
				MySourceProperties.get("mode.big2"), MyDateTimeUtil.getNowTimestamp());

		// 新增場次, 玩家，並取得場次id
//		String mode = MySourceProperties.get("mode.big2");
//		Timestamp dateTime = MyDateTimeUtil.getNowTimestamp();
//		int deskId = big2Dao.addBig2Game(nameList.toString(), mode, dateTime);
		return 0;
	}


	// 防呆檢查
	private void validates(List<String> nameList) throws Exception {
		for (String name : nameList) {
			if (name == null || name.isBlank()) {
				throw new Exception("玩家名稱不得為空");
			}
		}
	}

}
