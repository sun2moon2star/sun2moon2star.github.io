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
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import jp.co.archTest.rest.ps.common.util.XmlBinder;
import jp.co.archTest.rest.ps.common.util.XmlBinderAbstract;
import jp.co.archTest.rest.ps.common.util.XmlUtils;



/**
 * オブジェクトバインダ.
 * 
 * DOMノードをリストオブジェクトに変換するバインダ。
 * @author LIWH(bbaOSFT)
 */
public class XmbListBinder extends XmlBinderAbstract {
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
        return List.class;
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
            etype = ptype.getActualTypeArguments()[0];
        } else {
            etype = Object.class;
        }

        // create list
        List<Object> list = new ArrayList<Object>();

        // add elements
        List<Element> elements = XmlUtils.getChildElements(node);
        XmlBinder binder = getLoader().getBinder(etype);
        for (Element elm : elements) {
            list.add(binder.bind(elm, etype));
        }

        return list;
    }
}
