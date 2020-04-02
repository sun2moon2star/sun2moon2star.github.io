/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * YYYYMMDD X.Xxxxxxxx(xxx)   G999.99.9 新規作成
 */
package rocky.base.common.transfer.impl;

import java.util.Map;

import jp.co.archTest.rest.ps.common.util.XmlBean;

/**
 * XXXX_http.xmlによって定義されるルートノードの対応するクラス.
 * <P>
 * XXXX_http.xmlのルートノードに対応するクラスです。
 * 複数定義されている子ノードをマップに所有します。
 * </P>
 * <P>
 * Copyright (C) 2008 TEC INFORMATION SYSTEMS Corporation. All Rights Reserved.
 * </P>
 * 
 * @version $id$
 */
public class HttpNetworkClient implements XmlBean {
    /**
     * デフォルトシリアルヴァージョン.
     */
    private static final long serialVersionUID = 1L;


    /**
     * 子ノードのオブジェクトを管理するマップ.
     */
    private Map < String, HttpService > services;



    /**
     * デフォルトコンストラクタ.
     */
    public HttpNetworkClient() {
    }



    /**
     * 子ノードを管理するマップのセッター.
     * 
     * @param servicemap
     *            子ノードを管理するマップ
     */
    public void setServices(Map < String, HttpService > servicemap) {
        this.services = servicemap;
    }



    /**
     * 子ノードを管理するマップから子ノードの情報クラスを取得.
     * 
     * @param key
     *            マップのキー
     * @return 取得した子ノード情報クラス
     * @throws java.lang.NullPointerException
     */
    public HttpService get(String key) {
        return services.get(key);
    }
}
