/*
 * --------+-------------------+---------+---------------
 *  DATE   |NAME(Inc)          |GUIDE    |GUIDANCE
 * --------+-------------------+---------+---------------
 * 20181108 FanMH(bbasoft)      G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.v1.fmt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ServiceUtil.<br>
 * 
 * <P>
 * Copyright (C) 2018 TOSHIBA TEC Corporation. All Rights Reserved.
 * <P>
 * 
 * @author fanmh(bbasoft)
 */
public class Util {

	
	/**
	 * Integerバリューを取得します
	 * @param value データ
	 * @return Integerバリュー
	 */
	public static Integer getIntegerValue(String value){
		Integer ret = null;
		if (value != null && !value.equals("")) {
			ret = Integer.parseInt(value);
		}

		return ret;
	}

	/**
	 * Dateバリューを取得します
	 * @param value データ
	 * @return Dateバリュー
	 * @throws ParseException 
	 */
	public static Date getDateValue(String value) throws ParseException{
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		Date ret = null;
		if (value != null && !value.equals("")) {
			ret = sDateFormat.parse(value);
		}
		return ret;
	}

	/**
	 * NULL判断
	 * 
	 * NULL又は""の場合、TRUE
	 * 
	 * @param value データ
	 * @return 判断結果
	 */
	public static boolean isNull(String value) {
		boolean ret = (value == null || value.equals("") || value.equals("null"));
		return ret;
	}

	/**
	 * NULL判断
	 * 
	 * NULL又は""の場合、TRUE
	 * 
	 * @param value データ
	 * @return 判断結果
	 */
	public static boolean isNull(Integer value) {
		boolean ret = (value == null);
		return ret;
	}

	/**
	 * NULL判断
	 * 
	 * NULL又は""の場合、TRUE
	 * 
	 * @param value データ
	 * @return 判断結果
	 */
	public static boolean isNull(Date value) {
		boolean ret = (value == null);
		return ret;
	}
}
