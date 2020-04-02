/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080415 K.Adachi(TJ)      G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.common.csv;

import java.util.Iterator;


/**
 * CSV形式文字列データを解析する機能を持つインターフェースです.
 * <P>
 * CSV形式文字列データを解析する機能を持つインターフェースです。
 * 外部よりCSV形式の文字列を取り込み、CSV要素に分解する機能を持っています。
 * また、このほかに、CSV形式文字列を個々のCSV要素に分解するためのセパレート文字の指定、
 * クラス内の要素数の取得が可能です。
 * </P>
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved
 * </P>
 * 
 * @author K.Adachi(TJ)
 */
public interface CSVIterator extends Iterator < Object > {
    /**
     * CSVIteratorクラスフィールドのセパレート文字を返します.
     * <P>
     * CSVIteratorクラスフィールドのセパレート文字を返します。
     * </P>
     * 
     * @return セパレート文字
     */
    public char getSeparator();



    /**
     * クラス内部CSV要素数を返します.
     * <P>
     * クラス内部CSV要素数を返します。
     * </P>
     * 
     * @return 要素数
     */
    public int countElement();



    /**
     * クラス内部CSV形式文字列データを順方向にたどり、次の要素を返します.
     * <P>
     * クラス内部CSV形式文字列データを順方向にたどり、次の要素を返します。
     * </P>
     * 
     * @return 要素
     */
    public String next();



    /**
     * 次の要素の有無を確認し、結果を返します.
     * <P>
     * 次の要素の有無を確認しし、存在する場合はtrue、存在しない場合はfalseを返します。
     * </P>
     * 
     * @return 次の要素の有無
     */
    public boolean hasNext();



    /**
     * 非サポート処理例外をスローします.
     * <P>
     * 使用しないメソッドです。具体的処理を実装せず、ただ非サポート処理例外をスローします。
     * </P>
     */
    public void remove();
}