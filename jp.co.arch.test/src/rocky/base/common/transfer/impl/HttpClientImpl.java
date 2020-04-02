/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080305 K.Adachi   (TJ)   G001.00.0 新規作成
 *                            G001.01.0 HTTPヘッダ情報取得方法変更要請対応
 * 20080509 S.Yoshimura(TJ)   G001.02.0 接続情報引数省略化対応
 *                            G001.03.1　内部クラスBaseImplの破棄とgetInfoメソッドの移行
 *                            G001.04.1 disconnectメソッドに変数filenameのリフレッシュを追加
 * 20080609 S.Yoshimura(TJ)   G001.05.1 TECNetworkException Throwableの追加
 * 20080612 Yamada.N(TIS)     G001.06.1 静的ﾁｪｯｸｴﾗｰ対応
 * 20080617 Yamada.N(TIS)     G001.07.0 setResHeadersﾒｿｯﾄﾞをprivateからprotectedへ変更
 * 20080829 Yamada.N(TIS)     G001.08.0 APｻｰﾊﾞによりHTTPﾚｽﾎﾟﾝｽのHEADERに
 *                                      Content-Lengthがｾｯﾄされてこない場合があるため、
 *                                      ﾚｽﾎﾟﾝｽﾃﾞｰﾀにContent-Lengthがｾｯﾄされていない
 *                                      場合はResponseｸﾗｽへｾｯﾄを行わない様に修正
 */
package rocky.base.common.transfer.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rocky.base.common.transfer.Client;


/**
 * HTTPによって通信業務を行うクラスです。.
 * 
 * <P>
 * HTTP通信を行うクラスです。 connectメソッドによって、外部XMLファイルをDocument変数化してフィールドに設定し、
 * サーバーとの接続のための情報を取得します。 disconnectメソッドでは、フィールド変数の初期化を行います。
 * sendメソッドでは、まずRequestクラスより取得してきたクライアントIDを用いて
 * フィールドに設定した外部XMLファイルの情報を読み、HTTPヘッダを作成します。
 * つぎに同じくRequestクラスから要求データを取得し、HTTPヘッダと合わせてPostMethodインスタンスに
 * 格納し、HTTPサーバーのサーブレットへアクセスします。
 * </P>
 * <P>
 * サーバーからの応答データの受信後、受信したHTTPヘッダより必要な情報を取得し、Responseクラスの
 * インスタンスに設定します。また、応答データも加えてInputStreamとしてResponseインスタンスに
 * 設定します。サーバーとの通信結果コードに合わせて、Responseインスタンスに結果ステータスを 設定します。
 * </P>
 * 
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * 
 * @author S.Yoshimura(TJ)
 */
public class HttpClientImpl implements Client { // parasoft-suppress METRICS.NOF "問題がない為"
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientImpl.class);
	
    /**
     * XMLドキュメントの属性ノード、"hostname"を指定するためのString型定数です。.
     * 
     */
	public static final String HOSTNAME = "hostname";

    /**
     * XMLドキュメントの属性ノード、"port"を指定するためのString型定数です。.
     * 
     */
    private static final String PORT = "port";


    /**
     * XMLドキュメントの属性ノード、"timeout"を指定するためのString型定数です。.
     * 
     */
    private static final String TIMEOUT = "timeout";


    /**
     * HTTPヘッダ情報のキー、"HOST"を指定するためのString型変数です。.
     */
    private static final String HOST = "HOST";


    /**
     * HTTPヘッダ情報のキー、"Content-Type"を指定するためのString型変数です。.
     */
    private static final String CONTENT_TYPE = "Content-Type";


    /**
     * HTTPヘッダ情報のキー、"Content-Length"を指定するためのString型変数です。.
     */
    private static final String CONTENT_LENGTH = "Content-Length";


    /**
     * HTTPヘッダ情報のキー、"X-From-Host"を指定するためのString型変数です。.
     */
    private static final String X_FROM_HOST = "X-From-Host";


    /**
     * HTTPヘッダ情報のキー、"X-StoreCode"を指定するためのString型変数です。.
     */
    private static final String X_STORECODE = "X-StoreCode";


    /**
     * HTTPヘッダ情報のキー、"X-Date-Time"を指定するためのString型変数です。.
     */
    private static final String X_DATE_TIME = "X-Date-Time";


    /**
     * HTTPヘッダ情報のキー、"X-Command"を指定するためのString型変数です。.
     */
    private static final String X_COMMAND = "X-Command";


    /**
     * HTTPヘッダ情報のキー、"X-Request-Id"を指定するためのString型変数です。.
     */
    private static final String X_REQUEST_ID = "X-Request-Id";


    /**
     * HTTPヘッダ情報のキー、"X-FileName"を指定するためのString型変数です。.
     */
    private static final String X_FILE_NAME = "X-FileName";


    /**
     * HTTPヘッダの"X-Date-Time"項目に対応するフォーマットです。.
     * 取得した年月日時分秒ミリ秒をHTTPヘッダの"X-Date-Time"項目に対応するフォーマットで指定するString型変数です。
     * 「1999-12-12 12:12:12:122」のように表示されるよう指定します。
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";


    // /**
    // * HTTPヘッダの項目定数です。. HTTPヘッダの項目定数です。
    // */
    // private static final int ITEM_SUM = 9;


    /**
     * サーバーとの通信結果がNGの際にクライアントに返すステータスです。.
     * 
     */
    private static final String NAK = "N";


    /**
     * サーバーとの通信結果がOKの際にクライアントに返すステータスです。.
     */
    private static final String ACK = "A";


    /**
     * サーバーとの通信の結果、警告が生じた際にクライアントに返すステータスです。.
     */
    private static final String WAK = "W";


    // /**
    // * 外部XMLの"method"属性ノードの値"get"を指すString型変数です。.
    // */
    // private static final String GET_METHOD = "get";
    //
    //
    // /**
    // * 外部XMLの"method"属性ノードの値"post"を指すString型変数です。.
    // */
    // private static final String POST_METHOD = "post";

    /**
     * 外部XML定義ファイル名.
     */
    private String filename;


    /**
     * HTTPサーバーと通信を行うためのクッキーです。.
     * 
     */
    private Cookie[] cookies_;


    /**
     * 接続先ホスト名です。.
     */
    private String hostName_;


    /**
     * 接続先ポート番号です。.
     */
    private String portNo_;


    /**
     * HTTPのタイムアウト時間です。.
     */
    private String timeOut_;


    /**
     * 接続先Servlet名です。.
     * 
     */
    private String nameServlet_;


    /**
     * 外部XML定義ファイルのデータタイプキー.
     * 
     */
    private String key;


    /**
     * HTTPサーバーとの通信方法.
     * 
     */
    //private String wayToSend_;


    /**
     * HTTPサーバーとの通信プロトコルです。.
     * 
     */
    private String protocolToSend_;

    /**
     * 店舗コード。.
     */
    private String storeCode_;

    /**
     * HttpNetworkClientオブジェクト.
     * 
     */
    private HttpNetworkClient hclient;



    /**
     * サーバーへ接続するために必要な情報を外部XMLファイルより取得し、フィールドに設定します。.
     * <P>
     * まず、サーバーへの接続に必要な情報を持つ外部XMLを取得し、フィールドのDocument変数へ格納します。
     * 次にDocumentより接続に必要な情報を取得し、フィールドの変数に確保します。
     * 
     * serverInfoで設定される情報は省略が可能。その場合は、外部XMLより取得されます。//G001.02.0
     * </P>
     * 
     * @param xmlFile
     *            接続情報を読み込む外部XMLファイル
     * @param serverInfo
     *            クライアントからの接続情報が保存されたMap実装オブジェクト
     * @throws TECNetworkException
     *             TEC通信例外
     * 
     */
    public void connect(String xmlFile, Map < String, String > serverInfo)
        throws TECNetworkException {
        checkMap(serverInfo);
        checkXMLFile(xmlFile);
        filename = xmlFile + "_http.xml";
        getConnectInfo(serverInfo);

    }



    /**
     * クライアントからのMapインスタンスが正しく引数に指定されているか判定します.
     * <P>
     * クライアントからのMapインスタンスが引数に正しく指定されているか判定し、
     * nullが指定されている場合はTEC通信例外を上位メソッドにスローします。
     * </P>
     * 
     * @param serverInfo
     *            クライアントからの接続情報が保存されたMap実装クラス
     * @throws TECNetworkException
     *             TEC通信例外
     */
    private void checkMap(Map < String, String > serverInfo)
        throws TECNetworkException {
        if (serverInfo == null) {
            throw new TECNetworkException(MsgAgainstException.E_ARGUMENT,
                    "指定した引数の形式（null）が不正です。");
        }
    }



    /**
     * 外部XMLファイルが正しく指定されているか判定します。.
     * <P>
     * 外部XMLファイルが正しく指定されているか判定し、nullである場合は例外を上位メソッドにthrowします。
     * </P>
     * 
     * @param xmlFile
     *            クライアントが指定したXMLファイル
     * @throws TECNetworkException
     *             TEC通信例外
     */
    private void checkXMLFile(String xmlFile) throws TECNetworkException {
        if (xmlFile == null) {
            throw new TECNetworkException(
                    MsgAgainstException.E_XML_NOT_POINTED,
                    "外部定義ファイルが指定されていません。");
        }

    }



    /**
     * 接続のための情報を外部XMLファイルより取得します。.
     * <P>
     * 引数に指定したIDの接続のための情報を外部XMLより取得します。 指定されたＩＤを検索した結果、1件も該当するＩＤが存在しなかった場合、
     * 上位メソッドに対してTECNetworkExceptionをthrowします。
     * </P>
     * 
     * @param serverInfo
     *            クライアントからの接続情報が保存されたMap実装クラス
     * @throws TECNetworkException
     *             TEC通信例外
     * 
     * @throws java.lang.NullPointerException
     */
    private void getConnectInfo(Map < String, String > serverInfo)
        throws TECNetworkException {
        // G001.01.0 Add-Start 外部定義ファイルより情報を取得する手順からの変更に伴う
        hostName_ = serverInfo.get(HOSTNAME);
        timeOut_ = serverInfo.get(TIMEOUT);
        portNo_ = serverInfo.get(PORT);
        // G001.01.0 Add-End

        // G001.01.0 Del
        // <<削除内容>>
        // 外部XML定義ファイルをDocumentとしてフィールドに保存する処理
        // 外部XML定義ファイルより接続先情報、タイムアウト時間情報を取得する処理
        // <<削除事由>>
        // S2コンテナ、およびrocky.base.common.util.impl.XmlLoaderへの切り替え
    }



    /**
     * フィールド変数をリフレッシュします。.
     * <P>
     * フィールド変数をリフレッシュします。
     * </P>
     */
    public void disconnect() {
        // 各変数のリフレッシュ作業
        hostName_ = null;
        portNo_ = null;
        timeOut_ = null;
        nameServlet_ = null;
        protocolToSend_ = null;
        cookies_ = null;
        //G001.04.1 add 
        // connect()前のsend()エラーとdisconnect()直後のsend()エラーが異なる為
        filename = null;
        storeCode_ = null;
    }

    /**
     * 店舗コードをセットします。
     */
    public void setStoreCode(String storeCd) {
    	storeCode_ = storeCd;
    }

    /**
     * サーバーと通信を行います。.
     * <P>
     * サーバーへの接続要求、サーバーとの接続遮断要求、サーバーへの要求データ送信、サーバーからの応答データ受信を行います。
     * </P>
     * 
     * @param req
     *            要求データを管理するRequestクラスのインスタンス
     * @return 応答データを管理するResponseクラスのインスタンス
     * @throws TECNetworkException
     *             例外
     */
    public Response send(Request req) throws TECNetworkException {
        BufferedInputStream bis = null;
        ByteArrayInputStream bais = null;
        InputStreamRequestEntity reqEnt = null;

        PostMethod pmethod = null;

        HttpClient client = null;
        HttpClientParams hcp = new HttpClientParams();
        HttpState hs = new HttpState();
        Header[] httpHeader = new Header[9];
        int dataLength = 0;
        int status = 0;
        Response res = new Response();

        try {
            // サーバーへ送信するデータの設定
            // Requestよりデータを取得し、PostMethodが取得する
            bais = getInputStream(req);

            dataLength = getDataLength(bais);

            reqEnt = getReqEnt(bais);

            // httpHeader配列にHTTPへッダ情報を格納
            httpHeader = setupHeader(req, dataLength);

            String remoteHost = protocolToSend_ + "://" + hostName_ + ":"
                    + portNo_ + nameServlet_;
            
            
            pmethod = new PostMethod(remoteHost);

            // Http通信のタイムアウト時間を設定
            hcp.setSoTimeout(Integer.valueOf(timeOut_));

            // PutServletを使用したPOSTメソッドによるファイルのUL
            client = new HttpClient(hcp);

            // 2回目以降のsendの際、CookieをHttpClientに設定
            // Cookieのない1回目の送信では、Cookie、HttpStateを設定しない
            client = setCookies(client, hs);

            // PostMethodにHTTPヘッダを追加
            pmethod = setHeaders(pmethod, httpHeader);

            // PostMethodに要求データエンティティを追加
            pmethod.setRequestEntity(reqEnt);
            
            String logMessage = remoteHost + "\t\n";
    		for (Header headr : httpHeader) {
    			logMessage = logMessage  + "  " + headr.toString();
            }
            logger.info("POSIF要求:" + logMessage);
            

            // サーバーへ要求データを送信
            status = client.executeMethod(pmethod);

            // サーバーからのCookie情報をフィールドに設定
            cookies_ = client.getState().getCookies();

            // 取得した応答データよりヘッダ情報を抜き出し、Responseクラスに設定
            setResHeaders(pmethod, res, status);

            bis = new BufferedInputStream(pmethod.getResponseBodyAsStream());

            // 応答データをResponseクラスに設定
            res.setStream(bis);
        } catch (ConnectException e) {
            //G001.05.1 Throwableの追加
        	logger.error("接続要求が拒絶されました。", e);
            throw new TECNetworkException(
                    MsgAgainstException.E_CONNECT_REFUSED, "接続要求が拒絶されました。", e);
        } catch (NoRouteToHostException e) {
            //G001.05.1 Throwableの追加
        	logger.error("指定したリモートホストへのルートを確立できません。", e);
            throw new TECNetworkException(MsgAgainstException.E_NO_ROUTE,
                    "指定したリモートホストへのルートを確立できません。", e);
        } catch (SocketTimeoutException e) {
            //G001.05.1 Throwableの追加
        	logger.error("ソケットのタイムアウト時間に達しました。", e);
            throw new TECNetworkException(MsgAgainstException.E_SOCKET_TIMEOUT,
                    "ソケットのタイムアウト時間に達しました。", e);
        } catch (SocketException e) {
            //G001.05.1 Throwableの追加
        	logger.error("指定したリモートホストへの接続が拒絶されました。", e);
            throw new TECNetworkException(
                    MsgAgainstException.E_CONNECT_REFUSED,
                    "指定したリモートホストへの接続が拒絶されました。", e);
        } catch (IllegalArgumentException e) {
            //G001.05.1 Throwableの追加
        	logger.error("指定した引数の形式が不正です。", e);
            throw new TECNetworkException(MsgAgainstException.E_ARGUMENT,
                    "指定した引数の形式が不正です。", e);
        } catch (IOException e) {
            //G001.05.1 Throwableの追加
        	logger.error("入出力・通信処理が不正です。", e);
            throw new TECNetworkException(MsgAgainstException.E_IO,
                    "入出力・通信処理が不正です。", e);
        } catch (Exception e) {
            //G001.05.1 Throwableの追加
        	logger.error(e.getMessage() , e);
            throw new TECNetworkException(MsgAgainstException.E_RUNTIME, e
                    .getMessage(), e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                doNothing();
            }
        }
        return res;
    }



    /**
     * Requestクラスのインスタンスより要求データ用入力ストリームを取得します。.
     * <P>
     * Requestクラスより要求データを取得し、入力ストリームとして上位メソッドに返します。
     * </P>
     * 
     * @param req
     *            要求データを取得してくるRequestクラスのインスタンス
     * @return 要求データ用入力ストリーム
     */
    private ByteArrayInputStream getInputStream(Request req) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = req.getStream();
        byte[] reqEntAry = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(reqEntAry);

        return bais;
    }



    /**
     * 要求データが格納されたストリームを送信用エンティティでラップします。.
     * 要求データが格納されたストリームをInputStreamRequestEntityクラスインスタンスでラップし、 上位メソッドに返します。
     * 
     * @param in
     *            要求データが格納された入力ストリーム
     * @return 送信用エンティティ
     */
    private InputStreamRequestEntity getReqEnt(ByteArrayInputStream in) {
        return new InputStreamRequestEntity(in);
    }



    /**
     * 要求データのデータ長をストリームより取得します。. 要求データが格納された入力ストリームのデータ長を取得します。
     * 
     * @param in
     *            要求データが格納された入力ストリーム
     * @return 算出した要求データ長
     */
    private int getDataLength(ByteArrayInputStream in) {
        return in.available();

    }



    /**
     * 要求データのヘッダ配列を作成し、上位メソッドに返します。.
     * <P>
     * HTTPヘッダの情報を、基底共通クラス、外部XML定義ファイルより取得。 可変長配列に設定し、ヘッダ配列を整形して上位メソッドに返します。
     * ヘッダ項目「X-StoreCode」については、 本メソッドを呼び出す前に、rocky.base.common.base.Baseクラスの
     * ローカル情報のマップに格納しておいてください。
     * </P>
     * 
     * @param req
     *            クライアントからのヘッダデータをフィールドに所有するRequestクラスのインスタンス
     * @param dataLength
     *            クライアントからの要求データ長
     * @return 作成したHTTPヘッダ
     * @throws TECNetworkException
     *             入出力例外
     */
    protected Header[] setupHeader(Request req, int dataLength)
        throws TECNetworkException {
        // G001.01.0 Maintenance
        // メソッドのアクセス修飾子をprivateからprotectedへ変更
        // Headerを可変リストに格納し、それからHeader配列で返すように変更

        //getInfo(req);   //G001.03.1 Maintenance 内部クラスBaseImplのメソッドから本クラスのメソッドに変更

        // G001.02.0　Add-Start 
        // Connectメソッドにより接続情報が設定されていない場合、XML設定ファイルより取得する。
        // <<関連事項>>
        // hclient.get(key)の戻り値 HttpServiceのメソッド追加.
        // getHostname/getPort/getTimeout
        if (hostName_ == null) {
            hostName_ = hclient.get(key).getHostname();
        }
        if (portNo_ == null) {
            portNo_ = hclient.get(key).getPort();
        }
        if (timeOut_ == null) {
            timeOut_ = hclient.get(key).getTimeout();
        }
        // G001.02.0　Add-End 

        // フィールドに接続情報を設定
        nameServlet_ = hclient.get(key).getUrl();
        protocolToSend_ = hclient.get(key).getProtocol();
        //wayToSend_ = hclient.get(key).getMethod();

        ArrayList < Header > list = new ArrayList < Header >();
        list.add(new Header(HOST, hostName_));
        list.add(new Header(CONTENT_TYPE, hclient.get(key).getType()));
        list.add(new Header(CONTENT_LENGTH, String.valueOf(dataLength)));
        list.add(new Header(X_FROM_HOST, getLocalHostName()));
        list.add(new Header(X_STORECODE, storeCode_));
        list.add(new Header(X_DATE_TIME, getDateTime()));
        list.add(new Header(X_COMMAND, hclient.get(key).getCommand()));
        list.add(new Header(X_REQUEST_ID, getMSec()));
        list.add(new Header(X_FILE_NAME, hclient.get(key).getFilename()));

        Header[] headers = new Header[list.size()];
        for (int index = 0; index < list.size(); index++) {
            headers[index] = list.get(index);
        }
        return headers;
        // G001.01.0 Del
        // <<削除内容>>
        // Requestクラスより取得したキーを元に、外部XML定義ファイルより
        // TECLANヘッダ情報を悉皆的に走査して取得する処理を削除
        // これに伴い、getReqInfoFrXml(NodeList list): HashMapメソッドを
        // 削除した。
        // <<削除事由>>
        // 以前のソースでは外部XML定義ファイルの構成に変更が加えられる際に
        // 実処理部分、ソースに修正を加える必要があったため。
        // AOPのパースペクティブに基づく。

        // G001.01.0 Del
        // <<削除内容>>
        // 1) 固定長のヘッダ配列をインスタンス化し、その配列にヘッダ情報を格納
        // 2) 暫定的な店舗コードを発番
        // <<削除事由>>
        // 1) 固定長配列ではヘッダ情報の新しい項目を追加する際に修正が必要となる
        // 2) 正式の店舗コードを取得する処理を追加したため


    }



    /**
     * Cookieが存在する場合、HTTP通信メソッドにCookieを設定します。.
     * 2回目以降のHTTPサーバーとの通信の際、CookieをHttpClientに設定します。
     * 
     * @param hc
     *            HttpClientクラスのインスタンス
     * @param hs
     *            HttpStateクラスのインスタンス
     * @return Cookieが設定されたHttpClientクラスのインスタンス
     */
    private HttpClient setCookies(HttpClient hc, HttpState hs) {
        // Cookieが存在する場合、HttpStateにCookieを設定し、PostMethodに追加する
        if (cookies_ != null) {
            for (int i = 0; i < cookies_.length; i++) {
                hs.addCookie(cookies_[i]);
            }
            hc.setState(hs);
            System.out.println("クッキー:" + cookies_);
        }
        return hc;
    }



    /**
     * タイムスタンプ用ミリ秒をString型で取得します。.
     * <P>
     * タイムスタンプ用ミリ秒をString型に変換後、上位メソッドに返します。
     * </P>
     * 
     * @return ミリ秒
     */
    public String getMSec() {
        Date d = new Date();
        long msec = d.getTime();
        return String.valueOf(msec);

    }



    /**
     * クライアント側のホスト名をString型で取得します。.
     * <P>
     * クライアント側のホスト名をString型で取得します。
     * </P>
     * 
     * @return クライアント側ホスト名のString型変数
     * @throws TECNetworkException
     *             不在ホスト例外のためのTEC通信例外
     */
    public String getLocalHostName() throws TECNetworkException {
        try {
            return InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            //G001.05.1 Throwableの追加
            throw new TECNetworkException("不正なホスト名が指定されています。",
                    MsgAgainstException.E_UNKNOWN_HOST, e);
        }
    }



    /**
     * 要求送信時の年月日時分秒を取得し、HTTPヘッダ用に成形します。.
     * <P>
     * 要求データの送信処理時の年月日時分秒を取得した後、HTTPヘッダのX-Date-Time項目の形式に合うよう
     * 成形してString型に変換した後、上位メソッドに返します。
     * </P>
     * 
     * @return X-Date-Time用に成形されたタイムスタンプ用String型変数
     */
    public String getDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        return sdf.format(c.getTime());
    }



    /**
     * 送信する要求データにヘッダを追加します。.
     * <P>
     * サーバーへ送信する要求データにヘッダを追加し、上位メソッドに返します。
     * </P>
     * 
     * @param pmet
     *            ヘッダを追加するHttpMethodBase
     * @param headers
     *            HTTPヘッダ
     * @return ヘッダを追加したHttpMethodBase
     */
    private PostMethod setHeaders(PostMethod pmet, Header[] headers) {
        PostMethod pm = pmet;
        Header[] httpheaders = headers;

        for (int i = 0; i < httpheaders.length; i++) {
            pm.addRequestHeader(httpheaders[i]);
        }

        return pm;
    }



    /**
     * サーバーからの応答データのHTTPヘッダ情報をResponseクラスのフィールドに設定します。.
     * <P>
     * サーバーからの応答データのHTTPヘッダ情報をResponseクラスのフィールドに設定します。
     * また、HTTP通信の結果ステータスコード、ステータス情報も合わせてResponseクラスのフィールドに設定します。
     * </P>
     * 
     * @param pm
     *            サーバーからの応答データをラップしたPostMethodクラスのインスタンス
     * @param res
     *            サーバーからの応答データを設定するResponseクラスのインスタンス
     * @param statuscode
     *            HTTP通信共通のサーバーとの通信結果コード
     * @throws TECNetworkException
     *             TEC通信例外
     */
    protected void setResHeaders(PostMethod pm, Response res, int statuscode)
    /*
     *private void setResHeaders(PostMethod pm, Response res, int statuscode)
     */
        throws TECNetworkException {
        res.addParameter(Response.STATUSCODE, statuscode);
        if (statuscode < 400) {
            if (statuscode >= 200 && statuscode < 300) {
                res.addParameter(Response.STATUS, ACK);
            } else if ((statuscode >= 100 && statuscode < 200)
                    || (statuscode >= 300)) {
                res.addParameter(Response.STATUS, WAK);
            } else {
                throw new TECNetworkException(
                        MsgAgainstException.E_HTTP_TRANSFER,
                        "HTTP通信における結果ステータスコードの値が不正です。");
            }
            res.addParameter(Response.HOST, pm.getResponseHeader(HOST)
                    .getValue());
            res.addParameter(Response.CONTENT_TYPE, pm.getResponseHeader(
                    CONTENT_TYPE).getValue());
            /* G001.08.0
             * res.addParameter(Response.CONTENT_LENGTH, pm.getResponseHeader(
             *         CONTENT_LENGTH).getValue());
             */
            // G001.08.0 ST
            Header header = pm.getResponseHeader(CONTENT_LENGTH);
            if (header != null) {
                res.addParameter(Response.CONTENT_LENGTH, header.getValue());
            }
            // G001.08.0 ED
            res.addParameter(Response.X_FROM_HOST, pm.getResponseHeader(
                    X_FROM_HOST).getValue());
            res.addParameter(Response.X_STRORE_CODE, pm.getResponseHeader(
                    X_STORECODE).getValue());
            res.addParameter(Response.X_DATE_TIME, pm.getResponseHeader(
                    X_DATE_TIME).getValue());
            res.addParameter(Response.X_COMMAND, pm
                    .getResponseHeader(X_COMMAND).getValue());
            res.addParameter(Response.X_REQUEST_ID, pm.getResponseHeader(
                    X_REQUEST_ID).getValue());
        } else {
            res.addParameter(Response.STATUS, NAK);
        }
    }
    /*//G001.03.1 Add-Start
    *//**
     * S2コンテナを利用して外部XML定義ファイルよりヘッダ情報を取得します.
     * 
     * @param req
     *            Requestクラス
     * @throws TECNetworkException
     *             TEC通信例外
     *//*
    private void getInfo(Request req) throws TECNetworkException {

        try {
            XmlLoader xmlLoader = new XmlLoaderImpl();
            hclient = ( HttpNetworkClient ) xmlLoader.load(filename);
            key = ( String ) req.getParameter(Request.DATA_TYPE);
        } catch (FileNotFoundException e) {
            //G001.05.1 Throwableの追加
        	logger.error("外部XML定義ファイルが見つかりません。 ファイル: " + filename, e);
            throw new TECNetworkException(
                    MsgAgainstException.E_XML_NOT_FOUND,
                    "外部XML定義ファイルが見つかりません。", e);
        } catch (IOException e) {
            //G001.05.1 Throwableの追加
            throw new TECNetworkException(MsgAgainstException.E_IO, e
                    .getMessage(), e);
        } catch (NullPointerException e) {
            //G001.05.1 Throwableの追加
            throw new TECNetworkException(MsgAgainstException.E_IO,
                    "実行時例外です。", e);
        }


    }*/
    //G001.03.1 Add-End

    //G001.03.1 Del 
    //<<削除内容>>
    // 内部クラスBaseImplの破棄。
    //<<削除事由>>
    // 本クラス自体がBaseの派生クラスのため、内部クラスであるBaseの派生クラスBaseImplは不要のため破棄。
    // 内部クラスで持つgetInfoメソッドは本クラスのprivateメソッドとして定義します。

    /**
     * 何もしないメソッドです。.
     * <P>
     * 何もしないメソッドです。形式的例外処理部分のチェックスタイル対策に利用します。
     * </P>
     */
    private static void doNothing() {
    }

}
