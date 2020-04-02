/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080304 K.Adachi   (TJ)   G001.00.0 新規作成
 */
package rocky.base.common.transfer.impl;

import jp.co.archTest.rest.ps.common.TECException;

/**
 * アプリケーション定義のエラーをログ出力するための例外クラスです。.
 * <P>
 * アプリケーション定義のエラーコードと詳細メッセージを格納します。 LogUtil.errorLog(Exception)の引数として使用すると、
 * 設定したエラーコードに対応したアプリケーション定義のメッセージに 詳細メッセージを付加したログが出力されます。
 * </P>
 */
public class TECNetworkException extends TECException {


    /**
     * デフォルトシリアルヴァージョンです。.
     */
    private static final long serialVersionUID = 1L;



    /**
     * TECNetworkExceptionクラスのコンストラクタです。.
     * 
     * @param msgNo
     *            メッセージコード
     * @param msg
     *            メッセージ
     */
    public TECNetworkException(final String msgNo, final String msg) {
        super(msgNo, msg);
    }



    /**
     * TECNetworkExceptionクラスのコンストラクタです。.
     * 
     * @param msgNo
     *            メッセージコード
     * @param msg
     *            メッセージ
     * @param cause
     *            Exceptionを格納します。
     */
    public TECNetworkException(final String msgNo, final String msg,
            final Throwable cause) {
        super(msgNo, msg, cause);
    }
}