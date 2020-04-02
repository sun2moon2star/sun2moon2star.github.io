/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080415 K.Adachi(TJ)      G001.00.0 新規作成
 * 20080602 S.Yoshimura(TJ)   G001.01.0 addItem→addItemListへの名称変更
 */
package jp.co.archTest.rest.ps.common.csv;

import java.util.Iterator;
import java.util.List;


/**
 * CSV形式の1行分のデータ内容を保持するインターフェースです.
 * <P>
 * CSV形式の1行分のデータ内容を保持するインターフェースです。 addItem、addIterator、addCSVLineメソッドによって
 * 単一の文字列や数値だけでなく、複数の値、 CSV形式の文字列、文字配列、リスト、日付データをクラス内に保持することができます。
 * </P>
 * <P>
 * 加えて、CSV形式の文字列の書き出し、書き出す際に加えるエンクォート処理の有無の指定、
 * CSV形式文字列をCSV要素に分解する際のセパレート文字の設定・取得、
 * クラス内リストに保持したCSV要素をリスト、文字配列、Iteratorに格納した上での取得機能があります。
 * CSV形式の文字列を書き出すと、クラス内のCSV要素はクリアされます。
 * </P>
 * <P>
 * また、クラス内リストにおける特定のインデックスを指定したCSV要素の削除も可能です。
 * </P>
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * 
 * @author K.Adachi(TJ)
 */
public interface CSVLine {
    /**
     * CSVLineクラスのフィールドのセパレート文字を返します.
     * <P>
     * CSVLineクラスのフィールドに設定されているセパレート文字を返します。
     * </P>
     * 
     * @return セパレート文字
     */
    public char getSeparator();



    /**
     * 指定したObjectをクラス内リストの末尾に追加します.
     * <P>
     * 指定したObjectをクラス内リストの末尾に追加します。
     * </P>
     * 
     * @param item
     *            末尾に追加するObject
     * 
     */
    public void addItem(Object item);



    /**
     * エンクォート処理を指定した上で、指定したObjectをクラス内リストの末尾に追加します.
     * <P>
     * エンクォート処理を指定した上で、指定したObjectをクラス内リストの末尾に追加します。
     * </P>
     * 
     * @param item
     *            末尾に追加するObject
     * 
     * @param enquote
     *            エンクォート処理の有無
     * 
     */
    public void addItem(Object item, boolean enquote);

    /**
     * 指定したリストより要素を抽出し、クラス内リストに追加します.
     * <P>
     * 指定したリストより要素を抽出し、クラス内リストに追加します。
     * </P>
     * 
     * @param list
     *            CSV要素を保持するリスト
     * 
     */
    public void addItemList(List < Object > list);



    /**
     * 指定した文字配列の文字列を個々にCSV要素化し、クラス内リストに追加します.
     * <P>
     * 指定した文字配列の文字列を個々にCSV要素化し、クラス内リストに追加します。
     * </P>
     * 
     * @param array
     *            CSV要素を保持する文字配列
     * 
     */
    public void addItem(String[] array);



    /**
     * 引数に指定されたCSV形式文字列を要素に分解し、クラス内リストに保持します.
     * <P>
     * 引数に指定されたCSV形式String値を、CSVLineインスタンス化時に指定したセパレート文字に
     * 基づいて要素に分解し、クラス内リストに保持します。
     * </P>
     * 
     * @param csvline
     *            CSV形式文字列
     */
    public void addCSVLine(String csvline);



    /**
     * CSVLineクラス内リストに保持されているCSV要素からCSV形式文字列を作成して返します.
     * <P>
     * CSVLineクラス内リストに保持されているCSV要素からCSV形式文字列を作成して返します。
     * </P>
     * 
     * @return 要素データを保持している文字配列
     * 
     */
    public String getLine();



    /**
     * すべての要素をエンクォートして1行分のCSV形式のデータを返します.
     * <P>
     * 強制的にエンクォートしながらCSVLineクラス内リストのCSVElementを
     * Stringに加え、１行分のCSV形式のデータとして呼び出し元に返します。
     * </P>
     * 
     * @return １行のCSV形式のデータ
     */
    public String getLineEn();

    /**
     * すべての要素をエンクォートせず1行分のCSV形式のデータを返します.
     * <P>
     * エンクォート指定に関わらずエンクォートしないで
     * CSVLineクラス内リストのCSVElementを
     * Stringに加え、１行分のCSV形式のデータとして呼び出し元に返します。
     * </P>
     * 
     * @return １行のCSV形式のデータ
     */
    public String getLineNoEn();


    /**
     * クラス内に保持されたCSVデータをリスト型で返します.
     * <P>
     * クラス内に保持されたCSVElementをリスト型で返します.
     * </P>
     * 
     * @return リスト型のCSVデータ
     */
    public List < String > getList();



    /**
     * クラス内で保持するCSV要素を文字配列で返します。.
     * <P>
     * クラス内で保持するCSV要素を文字配列で返します。
     * クラス内クラスCSVLineIteratorによってクラス内リストのCSVElementを取得し、 文字型配列に個々の要素を格納して返します。
     * </P>
     * 
     * @return CSV要素の文字配列
     */
    public String[] getStrings();



    /**
     * CSVLineクラス内リストに保持されているCSV要素数を返します.
     * <P>
     * CSVLineクラス内リストに保持されているCSV要素数を返します。
     * </P>
     * 
     * @return CSVデータの要素数
     * 
     */
    public int size();



    /**
     * CSVLineクラス内リストの指定したインデックスのCSV要素をString値で返します.
     * <P>
     * CSVLineクラス内リストの指定したインデックスのCSV要素をString値で返します。
     * </P>
     * 
     * @param n
     *            インデックス
     * @return インデックスの該当する要素
     * 
     */
    public String getItem(int n);



    /**
     * CSVLineクラス内リストの指定したインデックスのCSV要素を削除します.
     * <P>
     * CSVLineクラス内リストの指定したインデックスのCSV要素を削除します。
     * </P>
     * 
     * @param n
     *            インデックス
     * 
     */
    public void removeItem(int n);



    /**
     * CSVLineクラス内リストに保持されているCSV要素を格納したIteratorを返します.
     * <P>
     * CSVLineクラス内リストに保持されているCSV要素を格納したIteratorを返します。
     * </P>
     * 
     * @return CSV要素を保持するIterator
     * 
     */
    public Iterator < String > elements();

}