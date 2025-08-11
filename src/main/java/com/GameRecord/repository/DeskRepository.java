package com.GameRecord.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GameRecord.entitys.DeskEntity;

import jakarta.transaction.Transactional;

@Repository
public interface DeskRepository extends JpaRepository<DeskEntity, Integer> {

	// 用del查找
	public List<DeskEntity> findByDel(int del);

	// 用uuid查找反回id
	public DeskEntity findByUuid(String uuid);

	// 刪除場次
	@Modifying
	@Transactional
	@Query("UPDATE DeskEntity d SET d.del = 1 WHERE d.uuid = :deskUuid")
	int deleteByUuid(@Param("deskUuid") String deskUuid);
}
