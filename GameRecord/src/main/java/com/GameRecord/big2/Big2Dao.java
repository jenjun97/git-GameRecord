package com.GameRecord.big2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Big2Dao {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	public int createBigGame() {
		
		return 0;
	}

}
