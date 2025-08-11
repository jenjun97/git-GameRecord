package com.GameRecord.entitys;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_desk") // 對應資料表名稱
@Data
public class DeskEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 對應 AUTO_INCREMENT
	public Integer id;
	public String uuid;
	public String deskName;
	public Timestamp datime;
	public int del;
}
