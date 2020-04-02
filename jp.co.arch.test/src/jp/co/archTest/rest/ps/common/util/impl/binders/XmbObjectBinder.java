/*
 *　------------+---------------+-----------+------------------------------------
 *  DATE        NAME(Inc)       GUIDE       GUIDANCE
 *　------------+---------------+-----------+------------------------------------
 *  2008/03/13   LIWH(bbaOSFT)   G001.00.0   新規作成
 *  2008/09/19   Yamada.N(TIS)   G001.01.1   System.out.println出力をコメント
 *　------------+---------------+-----------+------------------------------------
 */
package jp.co.archTest.rest.ps.common.util.impl.binders;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.w3c.dom.Node;

import jp.co.archTest.rest.ps.common.util.XmlBinderAbstract;
import jp.co.archTest.rest.ps.common.util.XmlUtils;


/**
 * オブジェクトバインダ.
 * 
 * primitiveタイプと該当するバインダがないクラスの場合、このバインダを使う。
 * @author LIWH(bbaOSFT)
 */
public class XmbObjectBinder extends XmlBinderAbstract {
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
        return Object.class;
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
        Class< ? > clazz = null;
        if (type instanceof Class) {
            clazz = ( Class< ? > ) type;
        } else {
            throw new IOException("Unkonw type:" + type.toString());
        }

        // primitiveタイプのボクシングオブジェクトを取得する。
        if (clazz.isPrimitive()) {
            Object array = Array.newInstance(clazz, 1);
            clazz = Array.get(array, 0).getClass();
        }

        // ノードの値を取得する。
        String value = XmlUtils.getNodeValue(node);
        Object obj = null;

        // valueOfメソッドを使い、オブジェクトを生成してみる。
        try {
            Method valueOf = clazz.getMethod("valueOf",
                    new Class[] { String.class });
            if (valueOf.isAnnotationPresent(java.lang.Deprecated.class)) {
                //System.out.println(valueOf.getName() + " is deprecated.");
            }

            obj = valueOf.invoke(null, value);
            if (obj != null) {
                return obj;
            }
        } catch (Exception e) {
            //System.out.println("No valueof available.");
        }

        // コンストラクタを使い、オブジェクトを生成してみる。
        try {
            Constructor< ? > c = clazz
                    .getConstructor(new Class[] { String.class });
            if (c.isAnnotationPresent(java.lang.Deprecated.class)) {
                //System.out.println(c.getName() + "(String) is deprecated.");
            }

            obj = c.newInstance(value);
            if (obj != null) {
                return obj;
            }
        } catch (Exception e) {
            //System.out.println("No constructor with String.class.");
        }

        // どうやってオブジェクトを生成するか分からない。
        throw new IOException("Can't generate object of " + clazz.getName()
                + " with string \"" + value + "\".");
    }
}
