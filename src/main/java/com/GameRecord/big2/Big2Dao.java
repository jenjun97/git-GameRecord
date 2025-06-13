package com.GameRecord.big2;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class Big2Dao {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	// 新增場次，並取得場次id
	public int addBig2Game(String playerName, String modeName, Timestamp dateTime) {
		String sql = "INSERT INTO t_desk (player_name, mode_name, datetime) VALUES (:playerName, :modeName, :dateTime);";

		Map<String, Object> params = new HashMap<>();
		params.put("playerName", playerName);
		params.put("modeName", modeName);
		params.put("dateTime", dateTime);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(sql, new MapSqlParameterSource(params), keyHolder, new String[] { "id" } // 指定自增主鍵欄位名稱
		);

		// 取出新增的id值，如果有錯則回-1
		Number generatedId = keyHolder.getKey();
		return (generatedId != null) ? generatedId.intValue() : -1;
	}

}
