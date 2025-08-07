package com.GameRecord.desk;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.GameRecord.entitys.DeskEntity;
import com.GameRecord.entitys.PlayerEntity;
import com.GameRecord.repository.DeskRepository;
import com.GameRecord.repository.PlayersRepository;
import com.GameRecord.utils.MyDateTimeUtil;

@Service
public class DeskService {

	@Autowired
	private DeskRepository deskRepository;

	@Autowired
	private PlayersRepository playersRepository;
	
	public Model queryDeskInfo(Model model) {
		// 查詢所有場次
		List<DeskEntity> deskInfoList = deskRepository.findAll();
		model.addAttribute("deskInfoList", deskInfoList);
		return model;
	}

	// 儲存新增場次資訊
	public String saveDesk(String deskName, List<String> playerNameList) {
		// 儲存場次
		DeskEntity deskEntity = new DeskEntity();
		deskEntity.setUuid(UUID.randomUUID().toString().replace("-", ""));
		deskEntity.setDeskName(deskName);
		deskEntity.setDatime(MyDateTimeUtil.getNowTimestamp());

		// 儲存後取得場次id
		deskEntity = deskRepository.save(deskEntity);

		// 儲存玩家名稱
		for (String playerName : playerNameList) {
			PlayerEntity playerEntity = new PlayerEntity();
			playerEntity.setPlayerName(playerName);
			playerEntity.setFkDesk(deskEntity.getId());
			playersRepository.save(playerEntity);
		}

		return deskEntity.getUuid();

	}

}
