package com.GameRecord.desk;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.GameRecord.utils.MyDateTimeUtil;

@Service
public class DeskService {

	@Autowired
	private DeskRepository deskRepository;

	// 儲存新增場次資訊
	public void saveDesk(String deskName, List<String> playerNameList, Model model) {
		// 儲存場次
		DeskEntity entity = new DeskEntity();
		entity.setUuid(UUID.randomUUID().toString().replace("-", ""));
		entity.setDeskName(deskName);
		entity.setDatime(MyDateTimeUtil.getNowTimestamp());
		// 取出場次id
		DeskEntity deskId = deskRepository.save(entity);

		// 儲存玩家名稱

		// 取出玩家列表
	}

}
