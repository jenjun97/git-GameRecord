package com.GameRecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GameRecord.entitys.DeskEntity;

@Repository
public interface DeskRepository extends JpaRepository<DeskEntity, Integer> {

	// 用uuid查找反回id
	public DeskEntity findByUuid(String uuid);
}
