/*
 *　------------+---------------+-----------+------------------------------------
 *  DATE        NAME(Inc)       GUIDE       GUIDANCE
 *　------------+---------------+-----------+------------------------------------
 *  2008/03/13  LIWH(bbaOSFT)   G000.00.0   新規作成
 *  YYYY/MM/DD  XXXXX(xxx)      G000.00.0   修正情報
 *　------------+---------------+-----------+------------------------------------
 */
package jp.co.archTest.rest.ps.common.util.impl.binders;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import jp.co.archTest.rest.ps.common.util.XmlBinder;
import jp.co.archTest.rest.ps.common.util.XmlBinderAbstract;
import jp.co.archTest.rest.ps.common.util.XmlUtils;



/**
 * マップバインダ.
 * 
 * DOMノードをマップオブジェクトに変換するバインダ。
 * 
  * @author LIWH(bbaOSFT)
*/
public class XmbMapBinder extends XmlBinderAbstract {
    /**
     * Name of the attribute contain the map's key.
     */
    private static final String KEY_NAME = "key";

    /**
     * デフォルトクラス取得.
     * 
     * バインダがサポートするデフォルトクラスを取得する。
     * 
     * @return デフォルトクラス。
     * @see XmlBinder#getSupportClass()
     */
    @Override
    public Class< ? > getSupportClass() {
        return Map.class;
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
        Type etype = null;

        // get element type
        if (type instanceof ParameterizedType) {
            ParameterizedType ptype = ( ParameterizedType ) type;
            etype = ptype.getActualTypeArguments()[1];
        } else {
            etype = Object.class;
        }

        XmlBinder binder = getLoader().getBinder(etype);

        Map<String, Object> map = new HashMap<String, Object>();

        // check node
        if (node == null || node.getNodeType() != Node.ELEMENT_NODE) {
            return map;
        }

        // get key
        Element elm = ( Element ) node;
        String key = elm.getAttribute(KEY_NAME);

        // bind map
        List<Element> elements = XmlUtils.getChildElements(elm);
        String mapKey = null;
        for (Element child : elements) {
            if (key != null && key.length() > 0) {
                mapKey = child.getAttribute(key);
            } else {
                mapKey = child.getNodeName();
            }

            map.put(mapKey, binder.bind(child, etype));
        }

        return map;
    }
}
