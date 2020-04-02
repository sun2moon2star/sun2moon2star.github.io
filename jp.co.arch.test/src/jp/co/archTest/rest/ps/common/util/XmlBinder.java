/*
 *　------------+---------------+-----------+------------------------------------
 *  DATE        NAME(Inc)       GUIDE       GUIDANCE
 *　------------+---------------+-----------+------------------------------------
 *  2008/03/12  LIWH(bbaOSFT)   G000.00.0   新規作成
 *  YYYY/MM/DD  XXXXX(xxx)      G000.00.0   修正情報
 *　------------+---------------+-----------+------------------------------------
 */

package jp.co.archTest.rest.ps.common.util;

import java.io.IOException;
import java.lang.reflect.Type;
import org.w3c.dom.Node;

/**
 * バインダインターフェース.
 * 
 * ノードを指定されたくラスのオブジェクトまたはバインダの デフォルトクラスのオブジェクト
 * に変換するためのインターフェース。
 * @author LIWH(bbaOSFT)
 */
public interface XmlBinder {

    /**
     * バインドノード.
     * 
     * 指定されたタイプのオブジェクトを生成する。 指定しない場合デフォルトクラスの
     * オブジェクトを生成する。
     * 
     * @param node
     *            オブジェクトを生成するノード。
     * @param type
     *            オブジェクト型。
     * @return 生成されたオブジェクト。
     * @throws IOException
     *             エラーが発生した場合。
     */
    public Object bind(Node node, Type type) throws IOException;



    /**
     * loader取得.
     * 
     * このバインダのloaderを取得する。
     * 
     * @return loader
     */
    public XmlLoader getLoader();



    /**
     * loader設定.
     * 
     * このバインダのloaderを設定する。
     * 
     * @param loader
     *            指定されたloader。
     */
    public void setLoader(XmlLoader loader);



    /**
     * デフォルトクラス取得.
     * 
     * バインダがサポートするデフォルトクラスを取得する。
     * 
     * @return デフォルトクラス。
     */
    public Class< ? > getSupportClass();
}
