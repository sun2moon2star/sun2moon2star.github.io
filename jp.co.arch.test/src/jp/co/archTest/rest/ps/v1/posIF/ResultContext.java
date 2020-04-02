/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20190115 FanMH(bba)        G001.00.0 新規作成
 */

package jp.co.archTest.rest.ps.v1.posIF;

/**
 * PosIfPushサーバ用要求データクラス.<BR>
 * 
 * Pushサーバにて使用する要求データ用クラス<BR>
 * 
 * Pushサーバ機能において、API機能、Request機能等で使用される要求データ用スーパークラスです。
 * 
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * 
 * @author J.Ochiai_TJ
 */
public class ResultContext {

	/**
	 * 要求結果ステータス（０：正常 -1：異常）.
	 */
	private String status = "";

	/**
	 * 要求結果詳細ステータス（statusプロパティの値が-1の場合に詳細コードが格納されます。）.<BR>
	 */
	private String statusCode = "";

	/**
	 * 
	 * 要求結果ステータスを返します（０：正常 -1：異常）.<BR>
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * 要求結果ステータスを設定します（０：正常 -1：異常）.<BR>
	 * 
	 * @param statusValue
	 *            要求結果ステータス
	 */
	public void setStatus(String statusValue) {
		this.status = statusValue;
	}

	/**
	 * 
	 * 要求結果詳細ステータスを返します.<BR>
	 * 
	 * @return statusCode 要求結果詳細ステータス
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * 
	 * 要求結果詳細ステータスを設定します（statusが‐1（異常の場合に設定されます。)).<BR>
	 * 
	 * @param statusCodeValue
	 *            要求結果詳細ステータス。
	 */
	public void setStatusCode(String statusCodeValue) {
		this.statusCode = statusCodeValue;
	}

}
