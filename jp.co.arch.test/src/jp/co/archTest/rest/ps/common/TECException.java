/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * YYYYMMDD X.Xxxxxxxx(xxx)   G999.99.9 新規作成
 */
package jp.co.archTest.rest.ps.common;

/**
 * アプリケーション定義のエラーをログ出力するための例外<br>
 * アプリケーション定義のエラーコードと詳細メッセージを格納します。<br>
 * LogUtil.errorLog(Exception)の引数として使用すると、 設定したエラーコードに対応したアプリケーション定義のメッセージに
 * 詳細メッセージを付加したログが出力されます。<br>
 * <P>
 * TOSHIBA TEC Corporation. All Rights Reserved
 * </P>
 * 
 * @author t.tsuchiya(TE)
 * @version BSE001.001
 */
public class TECException extends Exception {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * メッセージコード.
     */
    private String msgNo_;

    /**
     * コンストラクタ.
     * 
     * @param msgNo
     *            メッセージコード
     * @param msg
     *            メッセージ
     */
    public TECException(String msgNo, String msg) {
        super(msg);
        this.msgNo_ = msgNo;
    }


    /**
     * コンストラクタ.
     * 
     * @param msgNo
     *            メッセージコード
     * @param msg
     *            メッセージ
     * @param cause
     *            原因
     */
    public TECException(String msgNo, String msg, Throwable cause) {
        super(msg, cause);
        this.msgNo_ = msgNo;
    }
    /**
     * getMsgNo.
     * （処理内容記述）
     * @return getMsgNo
     */
    public String getMsgNo() {
        return this.msgNo_;
    }



}
