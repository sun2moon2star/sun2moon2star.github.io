/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080304 K.Adachi  (TJ)   G001.00.0 新規作成
 */
package rocky.base.common.transfer.impl;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * クライアントからの要求を管理するクラスです。.
 * <p>
 * クライアントからの要求を管理するクラスです。クライアントのＩＤ情報をフィールドのハッシュマップへ格納、ハッシュマップからの取得をお行います。
 * また、要求データの取得、設定を行います。
 * </p>
 * 
 * <P>
 * Copyright (C) 2008 TEC INFORMATION SYSTEMS Corporation. All Rights Reserved.
 * </P>
 * 
 */
public class Request {

    /**
     * ハッシュマップのキー、"DATA_TYPE"のString型定数です。.
     * <P>
     * ハッシュマップのキー、"DATA_TYPE"のString型定数です。.
     * </P>
     * 
     */
    public static final String DATA_TYPE = "DATA_TYPE";

    /**
     * TECLANヘッダの形式を格納するハッシュマップインスタンスです。.
     * <P>
     * TECLANヘッダの形式を格納するハッシュマップクラスのインスタンスです。
     * </P>
     */
    private HashMap<String, Object> hash_ = new HashMap<String, Object>();

    /**
     * 要求用送信データが格納された出力ストリームです。.
     * <P>
     * 要求用送信データが格納された出力ストリームです。
     * </P>
     */
    private ByteArrayOutputStream os_;



    /**
     * ハッシュマップに指定されたキーで値を設定します。.
     * <P>
     * 第一引数に指定されたキーにて、ハッシュマップにデータを設定します。
     * </P>
     * 
     * @param strkey
     *            ユーザによって入力されるキー
     * @param obj
     *            ユーザによって入力されるヘッダデータ
     */
    public void addParameter(String strkey, Object obj) {
        hash_.put(strkey, obj);

    }



    /**
     * ハッシュマップより指定されたキーの値を取得します。.
     * <P>
     * ハッシュマップより、入力されたキーの値を取得します。
     * </P>
     * 
     * @param strkey
     *            SocketClientによって入力されるキー
     * @return キーで指定されたデータ
     */
    public Object getParameter(String strkey) {
        return hash_.get(strkey);
    }



    /**
     * フィールドの要求データ用ストリームを返します。.
     * <P>
     * フィールドの要求データ用ストリームを返します。 ストリームがNULLだった場合は、新しくストリームをインスタンス化して 空のストリームを返します。
     * </P>
     * 
     * @return 要求データ
     */
    public ByteArrayOutputStream getStream() {
        if (os_ == null) {
            os_ = new ByteArrayOutputStream();
        }
        return os_;
    }



    /**
     * 引数に指定された出力ストリームをフィールドに設定します。.
     * <P>
     * Requestクラスのフィールドに要求送信データを設定します。
     * </P>
     * 
     * @param os
     *            ユーザから設定される送信データ用出力ストリーム
     */
    public void setStream(ByteArrayOutputStream os) {
        os_ = os;

    }
}
