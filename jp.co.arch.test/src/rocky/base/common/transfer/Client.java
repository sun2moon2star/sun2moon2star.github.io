/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080324 K.Adachi(TJ)      G001.00.0 新規作成
 * 20080409 Yamada.N(TIS)     G001.01.1 connectメソッド引数不正の修正
 *                                      copyrightをTIS->TECへ変更
 */
package rocky.base.common.transfer;

import java.util.Map;

import rocky.base.common.transfer.impl.Request;
import rocky.base.common.transfer.impl.Response;

/**
 * 通信業務におけるクライアント機能のインターフェースです。.
 * <P>
 * 通信業務におけるクライアント機能のインターフェースです。
 * サーバーへの接続要求、サーバーとの接続の遮断要求、サーバーへの要求データの送信、
 * サーバーからの応答データの受信を行います。
 * </P>
 * 
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved
 * </P>
 * 
 * @version K.Adachi(TJ)
 */
public interface Client {
    /**
     * サーバーへ接続を要求します。.
     * <P>
     * サーバーへ接続を要求します。
     * </P>
     * @param fileName 外部XMLファイル
     * @param serverInf 接続情報Map
     * @throws Exception 例外
     */
    public void connect(String fileName, Map<String, String> serverInf)
        throws Exception;

    /**
     * サーバーとの接続の遮断要求を行います。.
     * <P>
     * サーバーとの接続の遮断を要求します。
     * </P>
     * 
     * @throws Exception 例外
     */
    public void disconnect() throws Exception;

    /**
     * 要求データを送信し、応答データを受信します。.
     * 
     * @param req
     *            Requestクラスのインスタンス
     * @return Responseクラスのインスタンス
     * @throws Exception 例外
     */
    public Response send(Request req) throws Exception;
    
    /**
     * 店舗コードをセットします。
     */
    public void setStoreCode(String storeCd);

}
