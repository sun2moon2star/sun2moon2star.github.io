/*
 *　------------+---------------+-----------+------------------------------------
 *  DATE        NAME(Inc)       GUIDE       GUIDANCE
 *　------------+---------------+-----------+------------------------------------
 *  2008/03/12  LIWH(bbaOSFT)   G000.00.0   新規作成
 *  YYYY/MM/DD  XXXXX(xxx)      G000.00.0   修正情報
 *　------------+---------------+-----------+------------------------------------
 */
package jp.co.archTest.rest.ps.common.util.impl.binders;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.w3c.dom.Node;

import jp.co.archTest.rest.ps.common.util.XmlBean;
import jp.co.archTest.rest.ps.common.util.XmlBinder;
import jp.co.archTest.rest.ps.common.util.XmlBinderAbstract;
import jp.co.archTest.rest.ps.common.util.XmlUtils;

/**
 * XｍｌBeanバインダ.
 * 
 * DOMノードをXｍｌBeanとその実装のオブジェクトに変換するバインダ。
 * 
 * @author LIWH(bbaOSFT)
 */
public class XmbBeanBinder extends XmlBinderAbstract {

    /**
     * デフォルトクラス取得.
     * 
     * バインダがサポートするデフォルトクラスを取得する。
     * 
     * @return デフォルトクラス。
     * @see XmlBinder#getSupportClass()
     */
    public Class< ? > getSupportClass() {
        return XmlBean.class;
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
    protected Object doBind(Node node, Type type) throws IOException {
        Object bean = null;
        Class< ? > clazz = ( Class< ? > ) type;

        if (type == this.getSupportClass()) {
            throw new IOException("XmlBean is interface, can't be instanced.");
        }

        try {
            // ビーンを作成する。
            bean = clazz.newInstance();

            // プロパティをバインドする。
            bindProperties(node, bean);

            // フィールドをバインドする。
            bindFields(node, bean);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

        return bean;
    }



    /**
     * プロパティ.
     * 
     * プロパティをバインドする。
     * 
     * @param node
     *            DOMノード。
     * @param bean
     *            生成されたオブジェクト。
     * @throws Exception
     *             エラーが発生した場合。
     */
    private void bindProperties(Node node, Object bean) throws Exception {
        BeanInfo info = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] props = info.getPropertyDescriptors();

        for (PropertyDescriptor prop : props) {
            // get proper DOM node for the property
            String name = prop.getName();
            Node child = XmlUtils.getChildNodeByName(node, name);
            if (child == null) {
                continue;
            }

            // check property's setter
            Method setter = prop.getWriteMethod();
            if (setter == null) {
                continue;
            }

            // get proper binder and bind the property
            XmlBinder binder = getLoader().getBinder(prop.getPropertyType());
            setter.invoke(bean, binder.bind(child, setter
                    .getGenericParameterTypes()[0]));
        }
    }



    /**
     * フィールド.
     * 
     * フィールドをバインドする。
     * 
     * @param node
     *            DOMノード
     * @param bean
     *            生成されたオブジェクト。
     * @throws Exception
     *            エラーが発生した場合。
     */
    private void bindFields(Node node, Object bean) throws Exception {
        Field[] fields = bean.getClass().getFields();
        for (Field field : fields) {
            // skip members that not public
            if (!Modifier.isPublic(field.getModifiers())) {
                continue;
            }

            // get proper DOM node for the property
            Node child = XmlUtils.getChildNodeByName(node, field.getName());
            if (child == null) {
                continue;
            }

            // skip members that already have values
            if (field.get(bean) != null) {
                continue;
            }

            // bind the field
            XmlBinder binder = getLoader().getBinder(field.getType());
            field.set(bean, binder.bind(child, field.getGenericType()));
        }
    }
}
