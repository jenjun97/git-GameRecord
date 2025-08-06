package com.GameRecord.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GameRecord.entitys.ScoreEntity;
import com.GameRecord.score.PlayerScoreDTO;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, Integer> {

	/**
	 * 查詢特定 playerId 的所有 round & score 使用 DTO 接結果（注意 group by 是多餘的可省略，JPA 自動處理）
	 */
	@Query(value = "select "
			+ "	tp.id as playerId "
			+ "	,ts.round "
			+ "	,ts.score "
			+ "from t_players tp "
			+ "join t_score ts on ts.fk_players = tp.id "
			+ "where "
			+ "	tp.id in :playerIds "
			+ "group by tp.id, ts.round, ts.score "
			+ "order by  ts.round, tp.id"
			, nativeQuery = true)
	List<PlayerScoreDTO> queryScoreByIdList(@Param("playerIds") List<Integer> playerIds);
}
