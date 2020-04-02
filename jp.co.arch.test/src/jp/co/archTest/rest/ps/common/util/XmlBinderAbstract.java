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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.w3c.dom.Node;

/**
 * 抽象バインダ.
 * 
 * XmlBinderインターフェースを実現する抽象バインダ。 このクラスはloader属性
 * とクラスのチェックを実現する。このクラスを継承し、Xmlbinderインターフェース
 * を実現できる。
 * @author LIWH(bbaOSFT)
 */
public abstract class XmlBinderAbstract implements XmlBinder {

    /**
     * loader.
     * 
     * 他のバインダを探すloader。
     */
    private XmlLoader loader_ = null;



    /**
     * loader取得.
     * 
     * このバインダのloaderを取得する。
     * 
     * @return loader.
     * @see XmlBinder#getLoader()
     */
    public XmlLoader getLoader() {
        return loader_;
    }



    /**
     * loader設定.
     * 
     * このバインダのloaderを設定する。
     * 
     * @param loader
     *            指定されたloader。
     * @see XmlBinder#setLoader(XmlLoader)
     */
    public void setLoader(XmlLoader loader) {
        loader_ = loader;
    }



    /**
     * バインドノード.
     * 
     * {@link XmlBinder#bind(Node,Type)}の実装。
     * このメソッドはデフォルトクラスとクラスのマッチをチェック。その後、
     * {@link XmlBinderAbstract#doBind(Node, Type)}を呼び出す。
     * @param node
     *            オブジェクトを生成するノード。
     * @param type
     *            オブジェクト型。
     * @return 生成されたオブジェクト。
     * @throws IOException
     *             エラーが発生した場合。
     * @see XmlBinder#bind(Node, Type)
     */
    public Object bind(Node node, Type type) throws IOException {
        if (loader_ == null) {
            throw new IOException("Binder should be used with loader.");
        }

        if (type == null) {
            // デフォルトクラスを使う。
            type = this.getSupportClass();
        }

        Class< ? > clazz = null;
        if (type instanceof ParameterizedType) {
            Type t = (( ParameterizedType ) type).getRawType();
            clazz = ( Class< ? > ) t;
        } else if (type instanceof Class) {
            clazz = ( Class< ? > ) type;
        } else {
            throw new IOException("Unknow type:" + type.toString());
        }

        // クラスをチェックする。
        if (!clazz.isPrimitive()
                && !this.getSupportClass().isAssignableFrom(clazz)) {
            throw new IOException("Class do not match this binder:"
                    + clazz.getName());
        }

        return doBind(node, type);
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
     */
    protected abstract Object doBind(Node node, Type type) throws IOException;


    /**
     * デフォルトクラス取得.
     * 
     * バインダがサポートするデフォルトクラスを取得する。
     * 
     * @return デフォルトクラス。
     * @see XmlBinder#getSupportClass()
     */
    public abstract Class< ? > getSupportClass();
}
