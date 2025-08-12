package com.GameRecord.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GameRecord.entitys.ScoreEntity;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, Integer> {

	/**
	 * 查詢特定 playerId 的所有 round & score 使用 DTO 接結果（注意 group by 是多餘的可省略，JPA 自動處理）
	 */
	@Query(value = 
			"select \r\n"
			+ "	ts.round \r\n"
			+ "	,tp.id as playerId \r\n"
			+ "	,ts.score \r\n"
			+ " from t_players tp \r\n"
			+ " join t_score ts on ts.fk_players = tp.id \r\n"
			+ " where \r\n"
			+ " 	tp.id in :playerIds \r\n"
			+ " group by tp.id, ts.round, ts.score \r\n"
			+ " order by  ts.round, tp.id"
			, nativeQuery = true)
	public List<List<Integer>> queryScoreByIdList(@Param("playerIds") List<Integer> playerIds);

	// 查詢特定 playerId 的最大 round
	@Query("SELECT MAX(s.round) FROM ScoreEntity s WHERE s.fkPlayers = :fkPlayers")
	public Integer findMaxRoundByFkPlayers(@Param("fkPlayers") Integer fkPlayers);
	
	// 取得每位玩家的總分
	@Query(value =
			"select\r\n"
			+ "	tp.id as playerId\r\n"
			+ "	,sum(ts.score)\r\n"
			+ " from t_players tp\r\n"
			+ " join t_score ts on ts.fk_players = tp.id\r\n"
			+ " where\r\n"
			+ " 	tp.id in :playerIds\r\n"
			+ " group by tp.id\r\n"
			+ " order by tp.id"
			, nativeQuery = true)
	public List<List<Integer>> findTotalScore(@Param("playerIds")List<Integer> playerIds);
	
	// 查詢單局記分資料
	@Query(value = 
            "select \r\n"
            + "	tp.id as playerId \r\n"
            + "	,ts.score \r\n"
            + " from t_players tp \r\n"
            + " join t_score ts on ts.fk_players = tp.id \r\n"
            + " where \r\n"
            + " 	ts.round = :round \r\n"
            + "		and tp.id in :playerIds\r\n"
            + " order by tp.id, ts.round"
            , nativeQuery = true)
	public List<List<Integer>> queryScoreByRoundAndPlayerIds(@Param("round") int round, @Param("playerIds") List<Integer> playerIds);

	 // 依照 round 與 fkPlayers 查詢符合的分數資料
    public List<ScoreEntity> findByRoundAndFkPlayers(Integer round, Integer fkPlayers);
}
