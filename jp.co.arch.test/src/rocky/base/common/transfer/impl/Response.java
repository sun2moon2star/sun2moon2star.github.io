/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080304 K.Adachi  (TJ)    G001.00.0 新規作成
 * 20080409 Yamada.N(TIS)     G001.01.1 STATUSSTRキー定義もれ追加
 * 20080628 CAOQM(bbasoft)    G003.02.0 機能追加（変更No.4）
 * 20080714 Yamada.N(TIS)     G003.03.0 setFilenames/getFilenames追加
 * 20080716 K.Miyazawa(TIS)   G003.04.0 Resposeクラスの利用側でHTTP通信後に応答の
 *                                      データ部を読み出すことができないエラー対応
 * 20080730 K.Miyazawa(TIS)   G003.05.0 streamData_がnullの場合の対処を追加
 *                                      
 */
package rocky.base.common.transfer.impl;

import java.io.BufferedInputStream;
// G003.04.0 ST
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
// G003.04.0 ED
import java.io.InputStream;
import java.util.HashMap;

/**
 * サーバーからの応答データを管理するクラスです。.
 * <P>
 * サーバーからの応答データを管理するクラスで、サーバーから返された必要なヘッダ情報の設定･取得、 サーバーからの応答データの設定・取得を行います。
 * </P>
 * <p>
 * なお、サーバーから返されたヘッダ情報を取得する場合は、取得するヘッダ項目のハッシュキーを static指定してください。
 * </p>
 * 
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * 
 * @author K.Adachi(TJ)
 */
public class Response {

    /**
     * TECLANヘッダのステータス情報を取得するためのハッシュマップのキーです。.
     * <P>
     * TECLANヘッダのステータス情報を取得するためのハッシュマップのキー"STATUS"です。
     * </P>
     */
    public static final String STATUS = "STATUS";

    /**
     * TECLANヘッダのA/N情報を取得するためのハッシュマップのキーです。.
     * <P>
     * TECLANヘッダのA/N情報を取得するためのハッシュマップのキー"STATUSCODE"です。
     * </P>
     */
    public static final String STATUSCODE = "STATUSCODE";

    /**
     * TECLANヘッダのA/N情報を取得するためのハッシュマップのキーです。.
     * <P>
     * TECLANヘッダのA/N情報を取得するためのハッシュマップのキー"STATUSSTR"です。
     * </P>
     */
    public static final String STATUSSTR = "STATUSSTR";

    /**
     * 応答データのHTTPヘッダにおけるHOST項目の情報を取得するためのハッシュマップのキーです。.
     * <P>
     * サーバーから受信した応答データのHTTPヘッダにおけるHOST項目の情報を取得するためのハッシュマップのキー"HOST"です。
     * </P>
     * 
     */
    public static final String HOST = "HOST";

    /**
     * 応答データのHTTPヘッダにおけるCONTENT_TYPE項目の情報を取得するためのハッシュマップのキーです。.
     * <P>
     * サーバーから受信した応答データのHTTPヘッダにおけるHOST項目の情報を取得するためのハッシュマップのキー"CONTENT_TYPE"です。
     * </P>
     * 
     */
    public static final String CONTENT_TYPE = "CONTENT_TYPE";

    /**
     * 応答データのHTTPヘッダにおけるCONTENT_LENGTH項目の情報を取得するためのハッシュマップのキーです。.
     * <P>
     * サーバーから受信した応答データのHTTPヘッダにおけるHOST項目の情報を取得するためのハッシュマップのキー"CONTENT_LENGTH"です。
     * </P>
     * 
     */
    public static final String CONTENT_LENGTH = "CONTENT_LENGTH";

    /**
     * 応答データのHTTPヘッダにおけるX_FORMAT_HOST項目の情報を取得するためのハッシュマップのキーです。.
     * <P>
     * サーバーから受信した応答データのHTTPヘッダにおけるHOST項目の情報を取得するためのハッシュマップのキー"X_FORMAT_HOST"です。
     * </P>
     * 
     */
    public static final String X_FROM_HOST = "X_FROM_HOST";

    /**
     * 応答データのHTTPヘッダにおけるX_STORE_CODE項目の情報を取得するためのハッシュマップのキーです。.
     * <P>
     * サーバーから受信した応答データのHTTPヘッダにおけるHOST項目の情報を取得するためのハッシュマップのキー"X_STORE_CODE"です。
     * </P>
     * 
     */
    public static final String X_STRORE_CODE = "X_STORE_CODE";

    /**
     * 応答データのHTTPヘッダにおけるX_DATE_TIME項目の情報を取得するためのハッシュマップのキーです。.
     * <P>
     * サーバーから受信した応答データのHTTPヘッダにおけるHOST項目の情報を取得するためのハッシュマップのキー"X_DATE_TIME"です。
     * </P>
     * 
     */
    public static final String X_DATE_TIME = "X_DATE_TIME";

    /**
     * 応答データのHTTPヘッダにおけるX_COMMAND項目の情報を取得するためのハッシュマップのキーです。.
     * <P>
     * サーバーから受信した応答データのHTTPヘッダにおけるHOST項目の情報を取得するためのハッシュマップのキー"X_COMMAND"です。
     * </P>
     * 
     */
    public static final String X_COMMAND = "X_COMMAND";

    /**
     * 応答データのHTTPヘッダにおけるX_REQUEST_ID項目の情報を取得するためのハッシュマップのキーです。.
     * <P>
     * サーバーから受信した応答データのHTTPヘッダにおけるHOST項目の情報を取得するためのハッシュマップのキー"X_REQUEST_ID"です。
     * </P>
     * 
     */
    public static final String X_REQUEST_ID = "X_REQUEST_ID";
    

    /**
     * HTTPヘッダ情報のキー、"X-RegNo"を指定するためのString型変数です.
     */
    public static final String X_REGNO = "X-RegNo";

    /**
     * TECLANヘッダ情報を取得するためのハッシュマップです。.
     * <P>
     * TECLANヘッダ情報を取得するためのハッシュマップです。 サーバーからの応答データにおけるTECLANヘッダ情報の内、
     * クライアント側に必要な情報が格納されています。
     * </P>
     */
    private HashMap<String, Object> hash_ = new HashMap<String, Object>();

    /**
     * サーバーからの応答データが格納されたストリームです。.
     * <P>
     * サーバーからの応答データが格納されたストリームです。
     * </P>
     */
    private BufferedInputStream bis_;

    // G003.01.0 ST
    /**
     * サーバーからの応答データが格納されたストリームです。.
     */
    private BufferedInputStream[] biss_;
    // G003.01.0 ED

    // G003.03.0 ST
    /**　FTP mget時の取得ファイル名. */
    private String[] files_;
    // G003.03.0 ED

    // G003.04.0 ST
    /**
     * ストリームに設定されたデータを内部保持します。.
     * <P>
     * ストリームに設定されたデータを内部保持します。
     * </P>
     */
    private byte[] streamData_;
    // G003.04.0 ED



    /**
     * キーを指定して取得する必要のあるTECLANヘッダ情報をハッシュマップに設定します。.
     * <P>
     * キーを指定してサーバーより受信したヘッダデータをハッシュマップhash_に格納します。
     * </P>
     * 
     * @param strkey
     *            SocketClientクラスが指定するキー
     * @param obj
     *            SocketClientクラスが入力するヘッダデータ
     */
    public void addParameter(String strkey, Object obj) {
        hash_.put(strkey, obj);
    }



    /**
     * キーを指定して必要なヘッダ情報をハッシュマップより取得します。.
     * <P>
     * キーを指定してハッシュマップhash_より、サーバーから受信したヘッダデータを取得します。
     * </P>
     * 
     * @param strkey
     *            ユーザが指定するキー
     * @return ユーザが取得するオブジェクト型ヘッダデータ
     */
    public Object getParameter(String strkey) {
        return hash_.get(strkey);
    }



    /**
     * 応答データを取得します。.
     * <P>
     * サーバーより受信した応答データが格納された入力ストリームをResponseクラスより取得します。
     * </P>
     * 
     * @return 取得する応答データが格納された入力ストリーム
     */
    public InputStream getStream() {
        // G003.04.0 return bis_;
        // G003.04.0 ST
        //（setStreamの修正を受け内部保持データを返すように修正）
        // G003.05.0 ST
        //（streamData_がnullの場合には空の場合の対処を追加）
        //return new ByteArrayInputStream(this.streamData_);
        //streamData_がnullの場合には空の場合の対処を追加
        if (this.streamData_ == null) {
            return new ByteArrayInputStream(new byte[0]);
        } else {
            return new ByteArrayInputStream(this.streamData_);
        }
        // G003.05.0 ED
        // G003.04.0 ED
    }



    /**
     * 応答データを設定します。.
     * <P>
     * サーバーより受信した応答データが格納されたストリームをResponseクラスのフィールドに設定します。
     * </P>
     * 
     * @param is Responseクラスに設定する入力ストリーム
     * @throws IOException IOException
     */
    // G003.04.0 ST
    //public void setStream(InputStream is) {
    //    bis_ = new BufferedInputStream(is);
    //}
    // G003.04.0 ED
    // G003.04.0 ST
    public void setStream(InputStream is) throws IOException {
        //（ストリームが外部でクローズのされる可能性があるため、データを内部保持させる対処とした）
        byte[] bs = new byte[256];

        try {
            //InputStream
            bis_ = new BufferedInputStream(is);

            //バイト配列作成テンポラリ領域
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int size = 0;
            //入力データをすべて読み込む
            while ((size = bis_.read(bs)) != -1) {
                baos.write(bs, 0, size);
            }
            streamData_ = new byte[baos.size()];
            //内部バイト配列に設定
            streamData_ = baos.toByteArray();
        } finally {
            if (bis_ != null) {
                bis_.close();
            }
        }
    }
    // G003.04.0 ED

    

    // G003.01.0 ST
    /**
     * 応答データを取得します。.
     * <P>
     * サーバーより受信した応答データが格納された入力ストリームアレイをResponseクラスより取得します。
     * </P>
     * 
     * @return biss_ 取得する応答データが格納された入力ストリームアレイ.
     */
    public InputStream[] getStreams() {
        return biss_;

    }



    /**
     * 応答データを設定します。.
     * <P>
     * サーバーより受信した応答データが格納されたストリームアレイをResponseクラスのフィールドに設定します。
     * </P>
     * 
     * @param iss
     *            Responseクラスに設定する入力ストリームアレイ.
     */
    public void setStreams(InputStream[] iss) {
        biss_ = new BufferedInputStream[iss.length];
        for (int i = 0; i < iss.length; i++) {
            biss_[i] = new BufferedInputStream(iss[i]);
        }
    }
    // G003.01.0 ED

    // G003.03.0 ST
    /**
     * mget時の取得ファイル名格納.
     * 
     * @param files ファイル名を格納した配列
     */
    public void setFileNames(String[] files) {
        files_ = files;
    }
    // G003.03.0 ED

    // G003.03.0 ST
    /**
     * mget時のファイル名取得.
     * FTPでのmget時のファイル名を取得します。
     * 格納配列はgetStreamsの配列と連動します。
     * @return ファイル名を格納した文字列配列
     */
    public String[] getFileNames() {
        return files_;
    }
    // G003.03.0 ED
}
