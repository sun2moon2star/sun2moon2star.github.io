/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * YYYYMMDD X.Xxxxxxxx(xxx)   G999.99.9 新規作成
 * 20080509 S.Yoshimura(TJ)   G001.01.0 接続情報引数省略化対応
 */
package rocky.base.common.transfer.impl;

import jp.co.archTest.rest.ps.common.util.XmlBean;

/**
 * XXXX_http.xmlによって定義される「services」ノードの子ノードに対応するクラス.
 * <P>
 * XXXX_http.xmlによって定義される「services」ノードの子ノードに対応するクラスです。
 * 外部XML定義ファイルで規定されているHTTPヘッダに必要な
 * 「LLR」、「電文種別」、「機種別ID」、「FFU」、「PR」項目の情報をフィールドに所有します。
 * Sersar2により、上掲の5項目は自動でフィールドに設定されます。
 * </P>
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC SYSTEMS Corporation. All Rights Reserved.
 * </P>
 * 
 * @author S.Yoshimura(TJ)
 */
public class HttpService implements XmlBean {
    /**
     * デフォルトシリアルヴァージョン.
     */
    private static final long serialVersionUID = 1L;


    /**
     * HTTPヘッダ「Content-Type」項目.
     */
    private String type;


    /**
     * HTTPヘッダ「X-Command」項目.
     */
    private String command;


    /**
     * HTTPヘッダ「X-FileName」項目.
     */
    private String filename;


    /**
     * 接続先サーブレット名.
     */
    private String url;


    /**
     * 接続プロトコル.
     */
    private String protocol;


    /**
     * 接続メソッド.
     */
    private String method;


    //G001.01.0 Add-start
    /**
     * 接続先サーバホスト名.
     */
    private String hostname;
    
    
    /**
     * 通信ポート番号.
     */
    private String port;
    
    
    /**
     * 通信タイムアウト値（ミリ秒）.
     */
    private String timeout;
    //G001.01.0 Add-end
    
    
    /**
     * フィールドにContent-Typeをセットします.
     * 
     * @param content
     *            セットする値
     */
    public void setType(String content) {
        type = content;
    }



    /**
     * フィールドにX-Commandをセットします.
     * 
     * @param content
     *            セットする値
     */
    public void setCommand(String content) {
        command = content;
    }



    /**
     * フィールドにX-FileNameをセットします.
     * 
     * @param content
     *            セットする値
     */
    public void setFilename(String content) {
        filename = content;
    }



    /**
     * フィールドに接続先サーブレット名をセットします.
     * 
     * @param content
     *            セットする値
     */
    public void setUrl(String content) {
        url = content;
    }



    /**
     * フィールドに接続プロトコルをセットします.
     * 
     * @param content
     *            セットする値
     */
    public void setProtocol(String content) {
        protocol = content;
    }



    /**
     * フィールドに接続メソッドをセットします.
     * 
     * @param content
     *            セットする値
     */
    public void setMethod(String content) {
        method = content;
    }


    //G001.01.0 Add-start　XML設定ファイルに子ノード追加の為
    /**
     * フィールドに接続先サーバホスト名をセットします.
     * 
     * @param content
     *            セットする値
     */
    public void setHostname(String content) {
        hostname = content;
    }

    
    
    /**
     * フィールドに通信ポート番号をセットします.
     * 
     * @param content
     *            セットする値
     */
    public void setPort(String content) {
        port = content;
    }

    
    
    /**
     * フィールドに通信タイムアウト値をセットします.
     * 
     * @param content
     *            セットする値
     */
    public void setTimeout(String content) {
        timeout = content;
    }
    //G001.01.0 Add-End
    
    
    /**
     * フィールドのContent-Typeを取得します.
     * 
     * @return フィールドの値
     */
    public String getType() {
        return type;
    }



    /**
     * フィールドのX-Commandを取得します.
     * 
     * @return フィールドの値
     */
    public String getCommand() {
        return command;
    }



    /**
     * フィールドのX-FileNameを取得します.
     * 
     * @return フィールドの値
     */
    public String getFilename() {
        return filename;
    }



    /**
     * フィールドの接続先サーブレット名を取得します.
     * 
     * @return フィールドの値
     */
    public String getUrl() {
        return url;
    }



    /**
     * フィールドの接続プロトコルを取得します.
     * 
     * @return フィールドの値
     */
    public String getProtocol() {
        return protocol;
    }



    /**
     * フィールドの通信メソッドを取得します.
     * 
     * @return フィールドの値
     */
    public String getMethod() {
        return method;
    }



    //G001.01.0 Add-start　XML設定ファイルに子ノード追加の為
    /**
     * フィールドの接続先サーバホスト名を取得します.
     * 
     * @return フィールドの値
     */
    public String getHostname() {
        return hostname;
    }



    /**
     * フィールドの通信ポート番号を取得します.
     * 
     * @return フィールドの値
     */
    public String getPort() {
        return port;
    }



    /**
     * フィールドの通信タイムアウト値を取得します.
     * 
     * @return フィールドの値
     */
    public String getTimeout() {
        return timeout;
    }
    //G001.01.0 Add-end
}
