package com.GameRecord.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class MyDateTimeUtil {

	/**
	 * 取得現在時間的Timestamp
	 * 
	 * @return
	 */
	public static Timestamp getNowTimestamp() {
		return Timestamp.valueOf(LocalDateTime.now());
	}

	/**
	 * 取當日日期字串yyyyMMdd
	 * 
	 * @param outFormat 指定輸出的日期格式 ex:yyyyMMdd yyyy-MM-dd
	 * @return 當日日期字串
	 */
	public static String getNowDateStr(String outFormat) {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(outFormat);
		return sdf.format(today);
	}

	/**
	 * 將日期格式化字串
	 * 
	 * @param date      要格式化的日期
	 * @param outFormat 指定輸出的日期格式 yyyyMMdd or yyyy-MM-dd
	 * @return
	 */
	public static String dateToStr(Date date, String outFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(outFormat);
		return sdf.format(date);
	}

	/**
	 * 取得當下時間字串
	 * 
	 * @param outTimeFormat 指定輸出的時間格式 HH:mm:ss(HH為24時制)
	 * @return
	 */
	public static String getNowTimeStr(String outTimeFormat) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(outTimeFormat);
		return now.format(formatter);
	}

	/**
	 * 日期時間字串轉換成Date型態，日期字串必需與輸入格式相同
	 * 
	 * @param datetimeStr 日期時間的字串 "20230701" "2023-07-01 14:23:45"
	 * @param inFormat    傳入的日期時間字串的格式 "yyyyMMdd" "yyyy-MM-dd HH:mm:ss"
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String datetimeStr, String inFormat) throws ParseException {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(inFormat);
		return DATE_FORMAT.parse(datetimeStr);
	}

	/**
	 * 日期字串轉換DB的datetime格式，日期字串必需與輸入格式相同
	 * 
	 * @param strDateTime 日期字串 "20230701" or "2023-07-01 14:23:45"
	 * @param inFormat    輸入的日期字串格式 "yyyyMMdd" or "yyyy-MM-dd HH:mm:ss"
	 * @return DB的datetime
	 * @throws ParseException
	 */
	public static Timestamp strToTimestamp(String dateTimeStr, String inFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(inFormat);
		Date date = sdf.parse(dateTimeStr);
		Timestamp timestamp = new Timestamp(date.getTime());

		return timestamp;
	}

	/**
	 * Date型態轉成Timestamp(DB的datetime格式)
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * Timestamp轉成指定格式的字串
	 * 
	 * @param timestamp java.sql.timestamp的日期物件(DB的datetime格式)
	 * @param inFormat  指定的格式，例如yyyyMMdd 或 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String timestampToStr(Timestamp timestamp, String inFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(inFormat);
		return sdf.format(timestamp);
	}

	// 將csv的日期時間格式轉為timestamp
	public static Timestamp csvDateStrToTimestamp(String ans_time) {
		// 解析字符串为 LocalDateTime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d a h:mm:ss", Locale.CHINESE);
		LocalDateTime localDateTime = LocalDateTime.parse(ans_time, formatter);

		// 转换为 Timestamp
		return Timestamp.valueOf(localDateTime);

	}
}
