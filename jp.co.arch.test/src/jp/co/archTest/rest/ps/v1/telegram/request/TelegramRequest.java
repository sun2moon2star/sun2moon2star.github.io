package jp.co.archTest.rest.ps.v1.telegram.request;

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
public class TelegramRequest extends AbsTelegram {

	/**
	 * ヘッダ
	 */
	private Header header;

	/**
	 * データ
	 */
	private Object data;

}
