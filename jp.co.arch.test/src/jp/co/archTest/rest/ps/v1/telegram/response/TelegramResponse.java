package jp.co.archTest.rest.ps.v1.telegram.response;

import jp.co.archTest.rest.ps.v1.telegram.base.AbsTelegram;
import jp.co.archTest.rest.ps.v1.telegram.base.Header;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * リクエストメッセージ<br>
 * WEBサーバから仮想POSへのリクエストメッセージは次の構成とする
 * 
 * @author xueyb
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TelegramResponse extends AbsTelegram {

	/**
	 * ヘッダ
	 */
	private Header header;

	/**
	 * データ
	 */
	private ResponseData data;

}
