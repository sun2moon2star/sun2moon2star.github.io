/**
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20190115 FanMH(bbasoft)    G001.00.0 処理終了時、disconnect処理を追加。
 */
package jp.co.archTest.rest.ps.v1.posIF;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rocky.base.common.transfer.Client;
import rocky.base.common.transfer.impl.HttpClientImpl;
import rocky.base.common.transfer.impl.Request;
import rocky.base.common.transfer.impl.Response;
import rocky.base.common.transfer.impl.TECNetworkException;

public class HttpDriver {

    /** HTTPClientConnection用XMLファイル設定情報. */
    public static final String POSIF_HTTP_XML_FILE_NAME = "resources/rocky/base/poscontrol/POSIF";

	private static final Logger logger = LoggerFactory.getLogger(HttpDriver.class);

	/**
    *
    * HTTP通信にて要求を送信します.
    *
    * HttpClientImplを使用してHTTP通信を行います。<BR>
    * ただし、以前要求送信をしておりかつ要求がすでにサーバ側に到達していた場合には<BR>
    * DBより結果内容を取得し、outに格納します。<BR>
    * その際は戻り値はnullを戻します。<BR>
    * 本メソッドの引数以外の情報はHTTPクライアント設定ファイルに記述する必要があります。<BR>
    * HTTPクライアント設定ファイルの要素判定のため、「データ種別」を使用します。<BR>
    * <BR>
    * HTTP通信は、Shift JISで行い、POSTメソッドを使用します。<BR>
    * 入力パラメータを解析し、IN属性の引数群+APIのメソッド名をPOSTメソッドのデータ部に設定します。<BR>
    * </P>
    *
     * @param host
     *            通信先ホスト名（<code>null</code>不可）
     * @param in
     *            IN属性の引数群（<code>null</code>不可）
     * @param timeout
     *            タイムアウト値（ミリ秒）
     * @return Response HTTP通信結果を戻します。
     *          nullの場合にはoutに結果内容が格納されます。 
     * @throws Exception
     *             通信上のエラーを検出した場合、スローする
    */
    public Response send(String host, Map < String, String > in, int timeout, String stroeCd)
        throws Exception {
        Client client = null;
        Response res = null;
        // HTTPCLIENT引数クラス
        Request req = new Request();

        // 送信データ
        ByteArrayOutputStream requestData = new ByteArrayOutputStream();
        try {

            client = new HttpClientImpl();
            client.setStoreCode(stroeCd);

            // グループ排他の有無
            String strGroupCtl = "0";

            // 内部プロトコルヘッダデータの生成
            String stringHeadData = strGroupCtl + "\r";

            // mapは空としてxmlより接続情報を取得
            Map<String, String> info =  new HashMap<String, String>();
            info.put(HttpClientImpl.HOSTNAME, host);
            client.connect(POSIF_HTTP_XML_FILE_NAME,
            		info);
            //送信用XMLファイルID
            req.addParameter(Request.DATA_TYPE, "P" + in.get(Response.X_COMMAND).toString());
            // commandの設定
            req.addParameter(Response.X_COMMAND, in.get(Response.X_COMMAND).toString());
            // storeCodeの設定
            req.addParameter(Response.X_STRORE_CODE, in.get(Response.X_STRORE_CODE).toString());
            // posNoListの設定(CSV形式で設定する)
            req.addParameter(Response.X_REGNO, in.get(Response.X_REGNO).toString());
            // 送信データ、データ部以外格納
            requestData.write(stringHeadData.getBytes());
            // 送信データの設定
            req.setStream(requestData);
            // 送信
            res = client.send(req);

        } catch (TECNetworkException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                //タイムアウトの場合
            	logger.error("HTTP送信でタイムアウトエラー発生しました. commandNo:" + in.get(Response.X_COMMAND).toString(), e);
            	throw e;
            }
        } catch (Exception e) {
            // エラーログ、エラー詳細ログ出力
        	logger.error("HTTP送信でエラー発生しました. commandNo:" + in.get(Response.X_COMMAND).toString(), e);        	
        	throw e;
        } finally {
            client.disconnect();
        }
        return res;
    }
    
    /**
     * Map&lt;String, String&gt;形式をOutputStreamにエンコードします.
     *
     * <P>
     * 入力パラメータからキーと値を取得し、S-JIS形式でシリアライズした出力ストリームを返します。
     * </P>
     *
     * <P>
     * <OL TYPE="1">
     * <LI>文字列バッファを生成します。
     * <LI>入力パラメータのエントリ数分、以下の処理をループします。 <OL TYPE="a">
     * <LI>キー、値を取得します。
     * <LI>キーを文字列バッファに追加します。
     * <LI>"="を文字列バッファに追加します。
     * <LI>値を文字列バッファに追加します。
     * <LI>改行コード"\r\n"を文字列バッファに追加します。 </OL>
     * <LI>文字列バッファをS-JIS形式のbyte型配列に変換します。
     * <LI>byte型配列を出力ストリームに出力します。
     * <LI>ストリームのデータ形式は以下のようになります。<BR>
     * キー=値&lt;改行コード&gt;キー=値&lt;改行コード&gt;・・・ </OL>
     * </P>
     *
     * @param in
     *            入力パラメータ（null不可）
     * @param out
     *            出力ストリーム（null不可）
     * @throws Exception
     *             変換エラーが発生した場合、スローします
     */
    @SuppressWarnings("unchecked")
	public static void encode(Map < String, String > in, OutputStream out)
            throws Exception {

            // 入力パラメータを「キー=値」形式に変換
            StringBuffer sb = new StringBuffer();
            Set< ? > stKey = in.entrySet();
            Iterator< ? > ite = stKey.iterator();
            while (ite.hasNext()) {
                Object o = ite.next();
                Map.Entry< String, String > ent = ( Entry< String, String > ) o;
                String key = ent.getKey();
                String val = ent.getValue();
                sb.append(key);
                sb.append(ScConstants.EQUAL);
                sb.append(val);
                sb.append(ScConstants.CRLF);
            }

            // 文字列をbyte型配列に変換
            String s = new String(sb);
            byte[] b = s.getBytes(ScConstants.SJIS);

            // byte型配列をByteArrayOutputStreamに設定
            out.write(b);
            out.close();
        }
}
