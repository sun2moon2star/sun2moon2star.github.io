/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20171220 Fanmh(bba)        G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.common;


public class Base { 


    /**
     * コンストラクタ.
     */
    public Base() {
       
    }

    /**
     * デバッグログ.
     * 開発者解析用ログ
     * @param message ログ出力するメッセージ文字列
     */
    protected void logDebug(String message) {

    }
    
    /**
     * エラーログ.
     * <P>
     * 運用監視用ログのエラーログを出力します。
     * </P>
     * @param sts エラーコード規約に定義されたエラーコード
     * @param message ログ出力するメッセージ文字列
     */
    protected void logError(String sts, String message) {
    }

    /**
     * 詳細エラーログ.
     * <P>
     * 開発者解析用のエラー詳細ログを出力します。<BR>
     * このメソッドは詳細エラーログの出力のみを行います。<BR>
     * 例外がエラー要因であった場合、運用監視用のエラーログをペアで出力する必要があります。<BR>
     * 出力の片落ちを防ぐためにも特に理由がない限り{@link #logError(String, String, String, Throwable)}を使用してください。
     * </P>
     * @param sts エラーコード規約に定義されたエラーコード
     * @param detailSts 独自エラーコード(M/W,SQLエラーコード等)
     * @param message ログ出力するメッセージ文字列
     * @param exception 発生したExceptionのオブジェクト
     */
    protected void logErrDetail(String sts, String detailSts,
                                    String message, Throwable exception) {
    }
}
