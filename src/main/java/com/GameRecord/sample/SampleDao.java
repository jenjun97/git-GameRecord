package com.GameRecord.sample;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.GameRecord.sample.dto.response.HisDeskResponseDto;
import com.GameRecord.sample.dto.response.RoundScoreResponseDto;

@Repository
public class SampleDao {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	/**
	 * 新增場次
	 * 
	 * @param deskUuid
	 * @param deskName
	 * @param kindName
	 * @param dateTime
	 * @return
	 */
	public int addDesk(String deskUuid, String deskName, String kindName, Timestamp dateTime) {
		String sql = "INSERT INTO t_desk (desk_uuid, desk_name, kind_name, datetime) VALUES (:deskUuid, :deskName, :kindName, :dateTime);";
		Map<String, Object> params = new HashMap<>();
		params.put("deskUuid", deskUuid);
		params.put("deskName", deskName);
		params.put("kindName", kindName);
		params.put("dateTime", dateTime);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(sql, new MapSqlParameterSource(params), keyHolder, new String[] { "id" } // 指定自增主鍵欄位名稱
		);

		// 取出新增的id值，如果有錯則回-1
		Number generatedId = keyHolder.getKey();
		return (generatedId != null) ? generatedId.intValue() : -1;
	}

	/**
	 * 新增玩家
	 * 
	 * @param deskId
	 * @param playerNum
	 * @param playerName
	 */
	public void addPlayers(int deskId, int playerNum, String playerName) {
		String sql = "INSERT INTO t_players ( player_num, player_name, fk_desk_id) VALUES( :playerNum, :playerName, :deskId);";

		Map<String, Object> params = new HashMap<>();
		params.put("deskId", deskId);
		params.put("playerNum", playerNum);
		params.put("playerName", playerName);

		jdbc.update(sql, params);
	}

	/**
	 * query玩家每把分數
	 * 
	 * @param deskUuid
	 * @return
	 */
	public List<RoundScoreResponseDto> queryScore(String deskUuid) {
		String sql = "SELECT ts.round_no, tp.player_num, ts.score\r\n" + "FROM t_score ts\r\n"
				+ "join t_players tp on tp.id = ts.fk_player_id \r\n" + "join t_desk td on td.id =tp.fk_desk_id\r\n"
				+ "where td.desk_uuid =:deskUuid \r\n" + "order by ts.round_no , ts.fk_player_id";
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("deskUuid", deskUuid);

		List<RoundScoreResponseDto> query = jdbc.query(sql, paramMap,
				BeanPropertyRowMapper.newInstance(RoundScoreResponseDto.class));
		return query;
	}

	// query玩家姓名
	public List<String> queryPlayerName(String deskUuid) {
		String sql = "select tp.player_name\r\n" + "from t_players tp \r\n"
				+ "join t_desk td on td.id = tp.fk_desk_id\r\n" + "where td.desk_uuid = :deskUuid \r\n"
				+ "order by tp.player_num";
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("deskUuid", deskUuid);

		List<String> query = jdbc.queryForList(sql, paramMap, String.class);
		return query;
	}

	// query玩家id
	public List<Integer> queryPlayerId(String deskUuid) {
		String sql = "select tp.id\r\n" + "from t_players tp\r\n" + "join t_desk td on td.id = tp.fk_desk_id\r\n"
				+ "where td.desk_uuid = :deskUuid \r\n" + "order by tp.id";
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("deskUuid", deskUuid);

		List<Integer> query = jdbc.queryForList(sql, paramMap, Integer.class);
		return query;
	}

	// query最後一把的號次
	public Integer queryLastRoundNum(int playerId) {
		String sql = "select max(round_no) as lastRoundNum \r\n" + "from t_score ts \r\n"
				+ "where fk_player_id = :playerId";
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("playerId", playerId);
		Integer lastRoundNum = jdbc.queryForObject(sql, paramMap, Integer.class);
		return lastRoundNum;
	}

	// query玩家總數
	public List<Integer> querySumScore(String deskUuid) {
		String sql = "SELECT SUM(ts.score) AS total_score\r\n" + "FROM t_score ts\r\n"
				+ "JOIN t_players tp ON tp.id = ts.fk_player_id\r\n" + "JOIN t_desk td ON td.id = tp.fk_desk_id\r\n"
				+ "WHERE td.desk_uuid = :deskUuid \r\n" + "GROUP BY tp.player_num\r\n" + "ORDER BY tp.player_num";
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("deskUuid", deskUuid);

		List<Integer> query = jdbc.queryForList(sql, paramMap, Integer.class);
		return query;
	}

	// query歷史場次
	public List<HisDeskResponseDto> queryHisDesk(String kindName) {
		String sql = "SELECT desk_uuid, desk_name, `datetime` \r\n" + "FROM t_desk\r\n"
				+ "where kind_name = :kindName \r\n" + "order by ID;";
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("kindName", kindName);

		List<HisDeskResponseDto> query = jdbc.query(sql, paramMap,
				BeanPropertyRowMapper.newInstance(HisDeskResponseDto.class));
		return query;
	}

	// insert分數
	public void addScore(int roundNo, int fkPlayerId, int score) {
		String sql = "INSERT INTO t_score\r\n" + "( round_no, fk_player_id, score)\r\n"
				+ "VALUES(:roundNo, :fkPlayerId, :score);";
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("roundNo", roundNo);
		paramMap.put("fkPlayerId", fkPlayerId);
		paramMap.put("score", score);
		
		jdbc.update(sql, paramMap);
	}

}
