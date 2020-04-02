/*
 *　------------+---------------+-----------+------------------------------------
 *  DATE        NAME(Inc)       GUIDE       GUIDANCE
 *　------------+---------------+-----------+------------------------------------
 *  2008/03/11  LIWH(bbaOSFT)   G000.00.0   新規作成
 *  YYYY/MM/DD  XXXXX(xxx)      G000.00.0   修正情報
 *　------------+---------------+-----------+------------------------------------
 */
package jp.co.archTest.rest.ps.common.util.impl.binders;

import java.io.IOException;
import java.lang.reflect.Type;

import org.w3c.dom.Node;

import jp.co.archTest.rest.ps.common.util.XmlBinderAbstract;



/**
 * ノードバインダ.
 * 
 * ノードを簡単に返すだけ。
  * @author LIWH(bbaOSFT)
*/
public class XmbNodeBinder extends XmlBinderAbstract {
    /**
     * デフォルトクラス取得.
     * 
     * バインダがサポートするデフォルトクラスを取得する。
     * 
     * @return デフォルトクラス。
     * @see XmlBinderAbstract#getSupportClass()
     */
    @Override
    public Class< ? > getSupportClass() {
        return Node.class;
    }



    /**
     * オブジェクト生成.
     * 
     * オブジェクトを生成する。
     * 
     * @param node
     *            オブジェクトを生成するノード。
     * @param type
     *            オブジェクト型。
     * @return 生成されたオブジェクト。
     * @throws IOException
     *             エラーが発生した場合。
     * @see XmlBinderAbstract#doBind(Node, Type)
     */
    @Override
    protected Object doBind(Node node, Type type) throws IOException {
        return node;
    }
}
