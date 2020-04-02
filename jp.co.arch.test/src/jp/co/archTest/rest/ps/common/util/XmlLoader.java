/*
 *　------------+---------------+-----------+------------------------------------
 *  DATE        NAME(Inc)       GUIDE       GUIDANCE
 *　------------+---------------+-----------+------------------------------------
 *  2008/03/11  LIWH(bbaOSFT)   G000.00.0   新規作成
 *  YYYY/MM/DD  XXXXX(xxx)      G000.00.0   修正情報
 *　------------+---------------+-----------+------------------------------------
 */
package jp.co.archTest.rest.ps.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.w3c.dom.Node;

/**
 * XMLファイル変換.
 * 
 * XMLをJavaBeanに変換するインターフェース。
 * @author LIWH(bbaOSFT)
 */
public interface XmlLoader {

    /**
     * XML変換.
     * 
     * 指定されたパスに対応するXMLファイルからBeanを生成する。
     * 
     * @param path
     *            XMLファイルのパス。
     * @return 生成されたBeanオブジェクト。
     * @exception IOException
     *                I/Oエラーが発生した場合。
     */
    public Object load(String path) throws IOException;



    /**
     * XML変換.
     * 
     * 指定されたファイルオブジェクトからBeanを生成する。
     * 
     * @param file
     *            XMLファイルオブジェクト。
     * @return 生成されたBeanオブジェクト。
     * @throws IOException
     *             I/Oエラーが発生した場合。
     */
    public Object load(File file) throws IOException;



    /**
     * XML変換.
     * 
     * 指定されたInputStreamからBeanを生成する。
     * 
     * @param is
     *            XMLのInputStream。
     * @return 生成されたBeanオブジェクト。
     * @throws IOException
     *             I/Oエラーが発生した場合。
     */
    public Object load(InputStream is) throws IOException;



    /**
     * XML変換.
     * 
     * 指定されたDOMノードオブジェクトからBeanを生成する。
     * 
     * @param node
     *            XMLのDOMノードオブジェクト。
     * @return 生成されたBeanオブジェクト。
     * @throws IOException
     *             I/Oエラー発生した場合。
     */
    public Object load(Node node) throws IOException;



    /**
     * バインダ取得.
     * 
     * 指定されたクラスに該当するバインダを探す。
     * 
     * @param type
     *            指定されたクラス。
     * @return 指定されたクラスに該当するバインダ。
     */
    public XmlBinder getBinder(Type type);



    /**
     * バインダ追加.
     * 
     * loaderに新しいバインダを追加する。
     * 
     * @param binder
     *            追加されるバインダ。
     * @return バインダ。
     */
    public XmlBinder addBinder(XmlBinder binder);
}
