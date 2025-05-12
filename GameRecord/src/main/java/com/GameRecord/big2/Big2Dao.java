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

	/**
	 * 新增遊戲場次
	 * 
	 * @param type
	 * @param title
	 * @param datetime
	 * @return
	 */
	public int createBigGame(String type, String title, Timestamp datetime) {
		String sql = "INSERT INTO t_games (type, title, create_datetime) VALUES (:type, :title, :datetime);";

		Map<String, Object> params = new HashMap<>();
		params.put("type", type);
		params.put("title", title);
		params.put("datetime", datetime);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbc.update(sql, new MapSqlParameterSource(params), keyHolder, new String[] { "id" } // 指定自增主鍵欄位名稱
		);

		// 取出新增的id值，如果有錯則回-1
		Number generatedId = keyHolder.getKey();
		return (generatedId != null) ? generatedId.intValue() : -1;
	}
	

}
