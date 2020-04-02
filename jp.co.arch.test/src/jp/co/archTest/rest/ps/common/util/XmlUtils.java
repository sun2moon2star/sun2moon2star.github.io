/*
 *　------------+---------------+-----------+------------------------------------
 *  DATE        NAME(Inc)       GUIDE       GUIDANCE
 *　------------+---------------+-----------+------------------------------------
 *  2008/03/14  LIWH(bbaOSFT)   G000.00.0   新規作成
 *  YYYY/MM/DD  XXXXX(xxx)      G000.00.0   修正情報
 *　------------+---------------+-----------+------------------------------------
 */
package jp.co.archTest.rest.ps.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * DOMヘルプ関数.
 * 
 * DOMのヘルプ関数のコレクション。
 * @author LIWH(bbaOSFT)
 */
public final class XmlUtils {
    /**
     * コンストラクタ.
     * 
     * このクラスはインスタンス化できない。
     */
    private XmlUtils() {
    }

    /**
     * DOM ツリー生成.
     * 
     * 指定されたInputStreamを解析し、DOM ツリーオブジェクトを生成する。
     * 
     * @param in
     *            XML情報を含むInputStream。
     * @return DOMツリーオブジェクト。
     * @throws IOException
     *             エラーが発生した場合。
     */
    public static Document parse(InputStream in) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(in);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }



    /**
     * ノード値取得.
     * 
     * 指定されたノードの値を取得する。
     * 
     * @param node
     *            指定されたノード。
     * @return ノードの値。
     */
    public static String getNodeValue(Node node) {
        /*
         * --------------------+--------------+----------------
         *                     | getNodeValue | getTextContent 
         * --------------------+--------------+----------------
         *  Node.ATTRIBUTE_NODE| value        | value 
         *  Node.ELEMENT_NODE  | null         | value
         * --------------------+--------------+----------------
         */
        if (node == null) {
            return null;
        }

        return node.getTextContent();
    }



    /**
     * ELEMENT_NODE型ブノードを取得.
     * 
     * 指定されたノードのELEMENT_NODE型のサブノードを取得する。
     * 
     * @param parent
     *            指定されたノード。
     * @return サブノードのリスト。
     */
    public static List<Element> getChildElements(Node parent) {
        List<Element> list = new ArrayList<Element>();

        if (parent == null) {
            return list;
        }

        Node child = parent.getFirstChild();
        while (child != null) {
            if (Node.ELEMENT_NODE == child.getNodeType()) {
                list.add(( Element ) child);
            }
            child = child.getNextSibling();
        }

        return list;
    }



    /**
     * 名前サブノード取得.
     * 
     * 名前をもとにサブノードを取得する。指定された名前と同じ属性がある場合、属性のノードを返す。
     * それ以外の場合、同じ名前のタグを返す。
     * 
     * @param node
     *            指定されたノード
     * @param name
     *            サブノードの名前。
     * @return サブノード。
     */
    public static Node getChildNodeByName(Node node, String name) {
        if (node == null || name == null || name.trim().equals("")
                || node.getNodeType() != Node.ELEMENT_NODE) {
            return null;
        }

        Element elm = ( Element ) node;

        // 属性をチェックする。
        Node child = elm.getAttributeNode(name);
        if (child != null) {
            return child;
        }

        // サブエレメントノードをチェックする。
        NodeList children = elm.getElementsByTagName(name);
        if (children.getLength() > 1) {
            System.out.println("Should be only one child element tag of "
                    + name);
        }
        return children.item(0);
    }
}
