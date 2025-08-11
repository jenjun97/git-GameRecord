package com.GameRecord.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GameRecord.entitys.DeskEntity;

@Repository
public interface DeskRepository extends JpaRepository<DeskEntity, Integer> {

	// 用del查找
	public List<DeskEntity> findByDel(int del);

	// 用uuid查找反回id
	public DeskEntity findByUuid(String uuid);
}
