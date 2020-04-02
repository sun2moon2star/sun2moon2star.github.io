/**
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20200326 suxl(bbasoft)     G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.v1.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * セッション管理テーブルエンティティ.
 * 
 * @author suxl(bbasoft)
 */
@SuppressWarnings("serial")
@Entity(name = "tb_terminal_session_ctrl")
@Table(name = "TB_TERMINAL_SESSION_CTRL")
public class TbTerminalSessionCtrl implements Serializable {

	/**
	 * 端末ID.
	 */
	@Column(name = "TERMINAL_ID")
	private String terminalId = "";

	/**
	 * アクセストークン.
	 */
	@Id
	@Column(name = "TOKEN")
	private String token = "";

	/**
	 * 端末IDを設定します.
	 *
	 * @param value
	 *            端末ID
	 */
	public void setTerminalId(String value) {
		this.terminalId = value;
	}

	/**
	 * 端末IDを取得します.
	 *
	 * @return 端末ID
	 */
	public String getTerminalId() {
		return this.terminalId;
	}

	/**
	 * アクセストークンを設定します.
	 *
	 * @param value
	 *            アクセストークン
	 */
	public void setToken(String value) {
		this.token = value;
	}

	/**
	 * アクセストークンを取得します.
	 *
	 * @return アクセストークン
	 */
	public String getToken() {
		return this.token;
	}
}