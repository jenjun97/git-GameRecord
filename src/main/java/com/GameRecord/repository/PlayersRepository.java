package com.GameRecord.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GameRecord.entitys.PlayerEntity;

@Repository
public interface PlayersRepository extends JpaRepository<PlayerEntity, Integer> {

	// 用deskId取得Players資料並且用id升序排序
	public List<PlayerEntity> findByFkDeskOrderByIdAsc(int deskId);
}
