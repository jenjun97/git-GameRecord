package com.GameRecord.utils;

import java.io.InputStream;
import java.util.Properties;

public class MySourceUtil {
	private static final Properties props = new Properties();

	// 在類別載入時就讀取 source.properties
	static {
		try (InputStream input = MySourceUtil.class.getClassLoader().getResourceAsStream("source.properties")) {
			if (input == null) {
				throw new Exception("無法找到 source.properties");
			} else {
				props.load(input); // 載入 properties
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 取得指定 key 的值
	 * 
	 * @param key 屬性名稱
	 * @return 對應的屬性值，如果不存在則回傳 null
	 */
	public static String get(String key) {
		return props.getProperty(key);
	}
}
