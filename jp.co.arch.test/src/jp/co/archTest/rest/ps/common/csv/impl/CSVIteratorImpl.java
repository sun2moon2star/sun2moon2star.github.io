/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080415 K.Adachi(TJ)      G001.00.0 新規作成
 * 20080602 S.Yoshimura(TJ)   G001.01.1 受入時不具合対応
 * 20080609 S.Yoshimura(TJ)   G001.02.1 静的チェックエラー修正
 * 20080613 S.Yoshimura(TJ)   G001.03.1 静的チェックエラー修正
 *                                      インスタンス変数定義を規約に合わせる
 */
package jp.co.archTest.rest.ps.common.csv.impl;

import java.util.NoSuchElementException;

import jp.co.archTest.rest.ps.common.csv.CSVIterator;


/**
 * CSV形式文字列データを解析するクラスです.
 * <P>
 * CSV形式文字列データを解析するクラスです。
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
public class CSVIteratorImpl implements CSVIterator {
    /**
     * CSV形式文字列１行分のデータ.
     * 
     */
    private String source_;


    /**
     * CSV行データの要素のインデックス.
     * 
     */
    private int currentPosition_;


    /**
     * CSV行データの末端の要素のインデックス.
     */
    private int maxPosition_;


    /**
     * セパレート文字.
     * 
     */
    private char sepletter_;



    /**
     * CSV形式文字列データ解析機能クラスのコンストラクタ.
     * <P>
     * CSV文字列解析クラスのコンストラクタです。引数にCSV形式の１行分のString値を指定します。
     * CSVデータの区切り文字には、デフォルト設定の「,（コンマ）」が指定されます。
     * </P>
     * <P>
     * 解析対象の文字列が空であったり、引数にNULL値が指定されている場合は
     * 引数不正例外がスローされます。
     * </P>
     * @param line
     *            CSV行データ
     */
    public CSVIteratorImpl(String line) {
        if (line == null) {
            throw new IllegalArgumentException("引数の値が不正（null）です。");
        } else {    //　G001.02.1 静的チェックエラー 不要なinstanceof評価の削除
            if (line.length() < 1) {
                throw new IllegalArgumentException("引数の文字列の内容が空です。");
            }
            this.source_ = line;
            this.sepletter_ = CSVConst.DEFAULT_SEPARATOR;
            this.maxPosition_ = line.length();
        }
    }



    /**
     * セパレート文字を指定できるCSV形式文字列データ解析機能クラスのコンストラクタ.
     * <P>
     * CSV文字列解析クラスのコンストラクタです。引数にCSV形式の１行分のString値を指定します。
     * CSVデータの区切り文字には、指定したchar値が指定されます。
     * </P>
     * <P>
     * 解析対象の文字列が空であったり、引数にNULL値が指定されたりしている場合、
     * あるいは、セパレート文字がクォート文字と重複する場合は引数不正例外がスローされます。
     * </P>
     * 
     * @param line
     *            CSV行データ
     * @param separator
     *            セパレート文字
     */
    public CSVIteratorImpl(String line, char separator) {
        if (line == null) {
            throw new IllegalArgumentException("引数の値が不正（null）です。");
        } else {  //　G001.03.1 静的チェックエラー 不要なinstanceof評価の削除
            if (line.length() < 1) {
                throw new IllegalArgumentException("引数の文字列の内容が空です。");
            } else if (separator == CSVConst.ENQUOTE_CHARACTOR) {
                throw new IllegalArgumentException(
                        "指定したセパレート文字がクォート文字と重複しています。");
            }
            this.source_ = line;
            this.sepletter_ = separator;
            this.maxPosition_ = line.length();
        }
    }



    /**
     * CSVのセパレート文字を返します。.
     * <P>
     * CSV形式ファイルの要素分割の際に用いるセパレート文字を返します。
     * </P>
     * 
     * @return セパレート文字
     */
    public char getSeparator() {
        return this.sepletter_;
    }



    /**
     * CSV形式文字列の次の要素を返します.
     * <P>
     * CSV形式文字列内の次の要素をString値で返します。
     * </P>
     * @return 次の項目
     */
    public String next() {
        // ">=" では末尾の項目を正しく処理できない。
        // 末尾の項目が空（カンマで1行が終わる）場合、例外が発生して
        // しまうので。
        if (currentPosition_ > maxPosition_) {
            throw new NoSuchElementException(toString() + "#next");
        }
        int st = currentPosition_;
        currentPosition_ = nextComma(currentPosition_);

        StringBuffer strb = new StringBuffer();
        while (st < currentPosition_) {
            char ch = source_.charAt(st++);
            if (ch == '"') {
                // "が単独で現れたときは何もしない
                //G001.01.1 先頭要素が""のときの処理不正対応
                if ((st + 1 < currentPosition_)
                        && (source_.charAt(st) == '"')) {
                    strb.append(ch);
                    st++;
                }
            } else {
                strb.append(ch);
            }
        }
        currentPosition_++;
        return new String(strb);
    }



    /**
     * CSVデータの次の要素の有無を確認し、結果を返します。.
     * <P>
     * CSV行データをセパレート文字で分割し、順方向にたどって次の要素が存在するかを確認します。
     * 次要素が存在する場合はtrue、存在しない場合はfalseを返します。
     * </P>
     * 
     * @return 次の要素の有無
     */
    public boolean hasNext() {
        return (nextComma(currentPosition_) <= maxPosition_);
    }



    /**
     * 非サポート処理例外をスローします.
     * <P>
     * 非サポート処理例外をスローします。
     * Iteratorインターフェースの実装につき、存在するメソッドですが利用されません。
     * </P>
     */
    public void remove() {
    //G001.01.1 メッセージ追加
        throw new UnsupportedOperationException("removeメソッドは処理されません。");
    }



    /**
     * 次のカンマがある位置を返します.
     * <P>
     * 現在のインデックスから次の最初のカンマが存在するインデックスをint値で返します。
     * カンマが残っていない場合は次のカンマのインデックスが文字列の末端のインデックスとなります。
     * また最後の項目が空の場合も次のカンマのインデックスが文字列の末端となります。。
     * </P>
     * 
     * @param index
     *            検索を開始する位置
     * @return 次のカンマがある位置。カンマがない場合は、文字列の 長さの値となる。
     */
    private int nextComma(int index) {
        boolean doEnquote = false;
        //G001.01.1 maintenance-start
        if (index < maxPosition_ && '"' == source_.charAt(index)) {
            doEnquote = !doEnquote;
            index++;
        }
        //G001.01.1 maintenance-end
        while (index < maxPosition_) {
            char ch = source_.charAt(index);
            if (!doEnquote && ch == sepletter_) {
                break;
            } else if ('"' == ch) {
                doEnquote = !doEnquote;
            }
            index++;
        }
        return index;
    }



    /**
     * CSV形式文字列データ中のCSV要素数を返します.
     * <P>
     * CSV形式文字列データをセパレート文字で分割し、生成された要素の数を返します。
     * </P>
     * 
     * @return 含まれている項目の数
     */
    public int countElement() {
        int i = 0;
        int ret = 1;
        while ((i = nextComma(i)) < maxPosition_) {
            i++;
            ret++;
        }
        return ret;
    }


}