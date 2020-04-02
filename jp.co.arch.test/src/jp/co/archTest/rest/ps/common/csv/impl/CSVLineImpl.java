/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080415 K.Adachi(TJ)      G001.00.0 新規作成
 * 20080509 K.Adachi(TJ)      G001.01.1 インタフェースの未実装不具合対応
 *                            G001.02.1 addItem(String[])メソッド欠如不具合対応
 * 20080602 S.Yoshimura(TJ)   G001.03.1 受入不具合対応
 *                                      ・クラス変数separatorのstatic定義を削除
 *                                      ・addItem(List)をaddItemList(List)に変更
 *                                      ・エンクォート有無の明示化
 *                                      ・CSVElementインスタンス生成時のエンクォート有無の明示化
 *                                      ・コメント、定義変数名の誤植対応
 * 20080613 S.Yoshimura(TJ)   G001.04.1 インスタンス変数定義を規約に合わせる
 *                                      静的チェックエラー対応 
 * 20081022 Yamada.N(TIS)     G001.05.0 文字列にｴｽｹｰﾌﾟｼｰｹﾝｽが含まれている場合は強制ｴﾝｸｫｰﾄする
 * 20100129 Yamada.N(TIS)     G001.06.0 強制でｴﾝｸｫｰﾄしないﾒｿｯﾄﾞ(getItemNoEn)を追加
 */
package jp.co.archTest.rest.ps.common.csv.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jp.co.archTest.rest.ps.common.csv.CSVIterator;
import jp.co.archTest.rest.ps.common.csv.CSVLine;


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
 * ※getLine系メソッドの動作について
 * {@link CSVLineImpl#getLine()},{@link CSVLineImpl#getLineEn()},
 * {@link CSVLineImpl#getLineNoEn()}の出力結果は
 * {@link CSVElement#doEnquote}指定との組合わせによって以下のようになります。<BR>
 * 例）\123,456 と設定した値を取出す場合
 * <TABLE BORDER="1" CELLSPACING="0" CELLPADDING="0">
 *     <TR BGCOLOR="#CCCCFF"><TH>メソッド</TH><TH>doEnquote<BR>true</TH>
 *     <TH>doEnquote<BR>false</TH></TR>
 *     <TR><TD>getLine</TD><TD>"\\123,456"</TD><TD>"\123,456"</TD></TR>
 *     <TR><TD>getLineEn</TD><TD>"\\123,456"</TD><TD>"\\123,456"</TD></TR>
 *     <TR><TD>getLineNoEn</TD><TD>"\123,456"</TD><TD>\123,456</TD></TR>
 * </TABLE>
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * 
 * @author K.Adachi(TJ)
 */
public class CSVLineImpl implements CSVLine {
    /**
     * 「年年年年月月日日」を指定するための文字列です.
     * <P>
     * Date値をクラス内リストに取り込む際の、日付データの文字列化処理において、 日付データが準拠する文字列形式です。
     * </P>
     */
    private static final String YYYYMMDD = "yyyyMMdd";


    /**
     * セパレート文字です.
     */
    private char separator_; //G001.03.1 maintenance static定義を削除


    /**
     * 日付スタイルフォーマットを決定するインスタンスです.
     * 
     */
    private SimpleDateFormat sdf_ = new SimpleDateFormat(CSVLineImpl.YYYYMMDD);


    /**
     * CSVElementを保持するクラス内リストです.
     * 
     */
    private ArrayList < CSVElement > elements_;



    /**
     * CSVLineImplのインスタンスを作成します。.
     * <P>
     * CSVLineImplのインスタンスを作成します。 インスタンス作成の際、クラス内のフィールドにCSVElementを格納するための
     * リストを新しく生成します。 セパレート文字は、標準の「,（コンマ）」を利用します。
     * </P>
     */
    public CSVLineImpl() {
        elements_ = new ArrayList < CSVElement >();
        //G001.03.1 maintenance　static off
        this.separator_ = CSVConst.DEFAULT_SEPARATOR;
    }



    /**
     * セパレート文字を指定するコンストラクタです.
     * <P>
     * セパレート文字を指定してCSVLineImplのインスタンスを取得します。
     * インスタンス作成の際、クラス内のフィールドにCSVElementを格納するための リストを新しく生成します。
     * </P>
     * 
     * @param newseparator
     *            セパレート文字
     */
    public CSVLineImpl(char newseparator) {
        elements_ = new ArrayList < CSVElement >();
        //G001.03.1 maintenance　static off
        this.separator_ = newseparator;

    }



    /**
     * CSVLineクラスのフィールドのセパレート文字を返します.
     * <P>
     * CSVLineクラスのフィールドに設定されているセパレート文字を返します。
     * 任意のセパレート文字を設定する場合は、CSVLineImplをインスタンス化する際、 引数にchar型のセパレート文字を指定してください。
     * </P>
     * 
     * @return セパレート文字
     */
    public char getSeparator() {
        //G001.03.1 maintenance　static off
        return this.separator_;
    }



    /**
     * 引数に指定された文字列を末尾に追加します.
     * <P>
     * 引数に指定された文字列を末尾に追加します。 エンクォート処理をFALSEに指定したaddItem(Object,
     * boolean)メソッドを実行します。
     * </P>
     * 
     * @param item
     *            追加する文字列
     */
    public void addItem(Object item) {
        addItem(item, false);
    }



    /**
     * 強制的にエンクォートするか指定した上で、引数に指定された文字列を末尾に追加します.
     * <P>
     * 引数に指定した文字列をエンクォートするか指定した上で、文字列を末尾に追加します。
     * 引数に指定したオブジェクトの型がString、Number、Dateのいずれも実装しない場合は、 引数不正例外がスローされます。
     * </P>
     * <P>
     * 引数がNULL値である場合は、空のString値をメソッドで新規に用意し、 CSVElementでラップしてクラス内リストに保持します。
     * </P>
     * 
     * @param item
     *            追加する文字列
     * @param enquote
     *            trueの場合、文字列はエンクォートされます
     */
    public void addItem(Object item, boolean enquote) {
        // String型、Number型、Date型インターフェースを実装するオブジェクトのみ保持
        // 引数のオブジェクトがnullの場合、空StringをCSVElementでラップし、
        // クラス内リストに追加する
        if (item == null) {
            String empty = "";
            elements_.add(new CSVElement(empty, enquote));
        } else if (item instanceof String || item instanceof Number
                || item instanceof Date) { //G001.04.1
            // Date型の場合、指定した書式フォーマットに整形してString型とする
            if (item instanceof Date) {
                item = sdf_.format(item);
            }
            elements_.add(new CSVElement(item, enquote));
        } else {
            throw new IllegalArgumentException("指定した引数の型が不正です。");
        }
    }


    //G001.03.1 addItem→addItemListメソッド名変更
    //<変更事由>addItem(Object)を参照してしまうため。
    /**
     * 引数で指定されたリストに含まれるすべての項目を末尾に追加します。.
     * <P>
     * 引数で指定されたリストよりCSV要素を取得し、String型は特に処理を加えず、
     * Date型、Number型の時はString値に変換し、CSVElementでラップして CSVLineクラス内リストの末尾に追加します。
     * </P>
     * <P>
     * また、Date型の場合はCSVLineクラスのフィールドに保持されている固定の 日付形式データに基づいて文字列化されます。
     * </P>
     * <P>
     * 引数に指定したリストが空である場合、あるいはリスト内の項目のクラスが
     * String、Date、Number型のいずれのクラス、インターフェースも実装していない ものである場合は引数異状例外がスローされます。
     * </P>
     * <P>
     * 引数がNULL値である場合は、空のString値をメソッドで新規に用意し、 CSVElementでラップしてクラス内リストに保持します。
     * </P>
     * 
     * @param list
     *            リスト型のCSVデータ要素群
     * @throws java.lang.IllegalArgumentException
     */
    public void  addItemList(List < Object > list) {
        // 引数がNULLの場合、空StringをCSVElementでラップしてクラス内リストに格納
        if (list == null || list.isEmpty()) {
            elements_.add(new CSVElement(""));
        } else {
            ArrayList < Object > newlist = new ArrayList < Object >(list);
            String item = null;
            Object o = null;
            for (int i = 0; i < newlist.size(); i++) {
                o = newlist.get(i);

                // リストの内容がnullの場合、空文字をCSVElementでラップし、クラス内リストに保存
                //G001.04.1 maintenance-start 静的チェックエラー修正
                if (o == null) {
                    elements_.add(new CSVElement(""));
                } else if (o instanceof Date) {
                    // Date型の場合、指定した書式フォーマットに整形してString型とする
                    item = sdf_.format(o);
                    //G001.03.1 maintenance エンクォート有無の明示化
                    elements_.add(new CSVElement(item, true));
                } else if (o instanceof String) {
                    // String型の場合、強制的にエンクォートする
                    elements_.add(new CSVElement(o.toString(), true));
                } else if (o instanceof Number) {
                    // Number型の場合、文字列に変換してリストに加える
                    item = newlist.get(i).toString();
                    //G001.03.1　maintenance エンクォート有無の明示化
                    elements_.add(new CSVElement(item, false));
                } else {
                    //G001.03.1 メッセージ変更
                    throw new IllegalArgumentException(
                            "指定した引数のListにCSVに変換できない型があります。");
                }
                //G001.04.1 maintenance-end
            }
        }

    }


    // G002.01.1 Add-Start 必須メソッド欠如の不具合対応
    /**
     * 引数で指定した文字列配列を要素に分解し、クラス内リストに追加します.
     * <P>
     * 引数で指定した文字列配列の文字列をクラス内リストにCSV要素として追加します。 すべての文字列はエンクォートされます。
     * </P>
     * 
     * @param array
     *            文字列配列型のデータ要素群
     * 
     */
    public void addItem(String[] array) {
        if (array == null || array.length < 1) {
            elements_.add(new CSVElement("", true));
        } else {
            for (int i = 0; i < array.length; i++) {
                elements_.add(new CSVElement(array[i], true));
            }
        }
    }
    // G002.01.1 Add-End



    /**
     * 引数に指定されたIteratorに含まれるすべての項目を末尾に追加します。.
     * <P>
     * 引数に指定されたIteratorが保持するCSV要素を抽出してクラス内リストに保持します。
     * Iteratorの内容が空の場合は引数異状例外がスローされます。
     * </P>
     * 
     * @param csvIterator
     *            CSV要素を保持するIterator
     */
    public void addIterator(CSVIterator csvIterator) {
        if (csvIterator == null) {
            elements_.add(new CSVElement(""));
        } else {
            int i = 0;
            while (csvIterator.hasNext()) {
                i++;
                String item = csvIterator.next();
                elements_.add(new CSVElement(item));
            }
            if (i < 1) {
                throw new IllegalArgumentException("指定されたIteratorの内容が空です。");
            }
        }
    }



    /**
     * 引数に指定されたCSV形式文字列を要素に分解し、クラス内リストに保持します.
     * <P>
     * 引数に指定されたCSV形式String値を、CSVLineインスタンス化時に指定したセパレート文字に
     * 基づいて要素に分解し、クラス内リストに保持します。
     * </P>
     * <P>
     * 正しいCSVデータの書式に基づいた文字列だけでなく、正規のCSVデータの書式に基づかない
     * 文字列であっても、要素に分解してクラス内リストに保持します。 クォーテーションやセパレート文字の配置は、EXCELなど、正規のCSVデータ形式に
     * 則ったものを参照して文字列に加えてください。
     * </P>
     * 
     * @param csvline
     *            CSV形式文字列
     * @throws java.lang.IllegalArgumentException
     */
    public void addCSVLine(String csvline) {
        if (csvline == null) {
            elements_.add(new CSVElement(""));
            // throw new IllegalArgumentException("指定した引数が不正（null）です。");
        } else {
            CSVIteratorImpl iterator = new CSVIteratorImpl(csvline, separator_);
            while (iterator.hasNext()) {
                String item = iterator.next();
                elements_.add(new CSVElement(item));
            }
        }
    }



    /**
     * 1行分のCSV形式のデータを返します.
     * <P>
     * CSVLineクラス内リストのCSVElementをStringに加え、１行分のCSV形式のデータとして 呼び出し元に返します。
     * </P>
     * <P>
     * エンクォート処理をTRUEとして登録した要素はエンクォートし、 エンクォート処理をFALSEとして登録した要素は追加時の文字形式のまま
     * 呼び出し元にString値で返します。
     * </P>
     * 
     * @return １行のCSV形式のデータ
     */
    public String getLine() {

        StringBuffer list = new StringBuffer();
        for (int n = 0; n < elements_.size(); n++) {
            CSVElement element = ( CSVElement ) elements_.get(n);
            String item = element.getItem();
            list.append(item);
            if (elements_.size() - 1 != n) {
                //G001.03.1 maintenance separator可変
                list.append(separator_);
            }
        }
        elements_.clear();
        return new String(list);
    }



    /**
     * すべての要素をエンクォートして1行分のCSV形式のデータを返します.
     * <P>
     * 強制的にエンクォートしながらCSVLineクラス内リストのCSVElementを
     * Stringに加え、１行分のCSV形式のデータとして呼び出し元に返します。 エンクォート処理をFALSEとして登録した要素に対しても強制的に
     * エンクォート処理を加えます。
     * </P>
     * 
     * @return １行のCSV形式のデータ
     */
    public String getLineEn() {
        StringBuffer list = new StringBuffer();
        for (int n = 0; n < elements_.size(); n++) {
            CSVElement element = ( CSVElement ) elements_.get(n);
            String item = element.getItemEn();
            list.append(item);
            if (elements_.size() - 1 != n) {
                //G001.03.1 maintenance separator可変
                list.append(separator_);
            }
        }
        elements_.clear();
        return new String(list);
    }

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
    public String getLineNoEn() {

        StringBuffer list = new StringBuffer();
        for (int n = 0; n < elements_.size(); n++) {
            CSVElement element = ( CSVElement ) elements_.get(n);
            // G001.06.0 ST
            String item = null;
            if (element.doEnquote) {
                item = element.getItemNoEn();
            } else {
                item = element.getRawItem();
            }
            // G001.06.0 ED
            /* G001.06.0 DEL
             * String item = element.getRawItem();
             */
            list.append(item);
            if (elements_.size() - 1 != n) {
                list.append(separator_);
            }
        }
        elements_.clear();
        return new String(list);
    }


    /**
     * クラス内に保持されたCSVデータをリスト型で返します.
     * <P>
     * クラス内に保持されたCSVElementをリスト型で返します.
     * </P>
     * 
     * @return リスト型のCSVデータ
     */
    public List < String > getList() {
        // クラス内のCSVLineIteratorを用いて要素を戻し用リストに格納、リストを返す
        List < String > list = new ArrayList < String >();
        CSVLineIterator lineIterator = new CSVLineIterator(elements_);
        while (lineIterator.hasNext()) {
            list.add(lineIterator.next());
        }
        return list;

    }



    /**
     * クラス内で保持するCSV要素を文字配列で返します。.
     * <P>
     * クラス内で保持するCSV要素を文字配列で返します。
     * クラス内クラスCSVLineIteratorによってクラス内リストのCSVElementを取得し、 文字型配列に個々の要素を格納して返します。
     * </P>
     * 
     * @return CSV要素の文字配列
     */
    public String[] getStrings() {
        String[] array = new String[elements_.size()];
        CSVLineIterator lineIterator = new CSVLineIterator(elements_);
        int index = 0;
        while (lineIterator.hasNext()) {
            array[index] = lineIterator.next();
            index++;
        }
        return array;
    }



    /**
     * CSVLineに保持されているCSVElementの数を返します.
     * 
     * @return CSVLineに含んでいる項目の数
     */
    public int size() {
        return elements_.size();
    }



    /**
     * 単一の要素をString値で返します.
     * <P>
     * CSVデータの指定されたインデックスの要素をString値で返します。要素に対してエンクォート処理は加えられません。
     * </P>
     * 
     * @param n
     *            項目の番号 [0 ～ size()-1]
     * @return n番目の文字列。エンクォートはしない。
     * @throws java.lang.IndexOutOfBoundsException
     */
    public String getItem(int n) {
        CSVElement element = ( CSVElement ) elements_.get(n);
        return element.getRawItem();
    }



    /**
     * クラス内のリストから、指定されたインデックスの項目を削除します. クラス内のリストから、指定されたインデックスの項目を削除します。
     * 
     * @param n
     *            項目の番号 [0 ～ size()-1]
     * @throws java.lang.ArrayIndexOutOfBoundsException
     */
    public void removeItem(int n) {
        elements_.remove(n);
    }



    /**
     * CSVLineクラス内リストに保持されているCSVElementをIteratorで返します.
     * <P>
     * CSVLineクラス内リストに保持されているCSVElementをIteratorで返します。
     * </P>
     * 
     * @return このCSVLineに含まれている文字列のリスト
     */
    public Iterator < String > elements() {
        return new CSVLineIterator(elements_);
    }

    /**
     * CSVLineに含まれるそれぞれの項目を保持するインナークラス。.
     */
    private class CSVElement {  //G001.04.1 静的チェックエラー対応 public->private
        /**
         * 要素の文字列.
         */
        private String element;


        /**
         * エンクォート処理の有無指定.
         */
        private boolean doEnquote;



        /**
         * CSVElementのコンストラクタ.
         * <P>
         * CSVElementのコンストラクタです。 引数にクラス内リストに格納するオブジェクトを指定します。
         * エンクォート無しで設定されます。
         * </P>
         * 
         * @param item
         *            アイテム
         */
        CSVElement(Object item) {
            this(item, false);
        }



        /**
         * CSVElementのコンストラクタ.
         * <P>
         * 引数にクラス内リストに格納するオブジェクトと、エンクォート処理指定を指定します。
         * </P>
         * 
         * @param item
         *            アイテム
         * @param enquote
         *            エンクォート処理有無指定
         */
        CSVElement(Object item, boolean enquote) {
            // nullが引数に指定された場合、空Stringをフィールドに保持します
            if (item == null) {
                String empty = "";
                this.element = empty;
            } else {
                this.element = item.toString();
            }
            this.doEnquote = enquote;
        }



        /**
         * 指定されていれば、エンクォートを行う。.
         * 
         * @return エンクォートされた文字列
         */
        public String getItem() {
            //G001.03.1 staticクラスに変更
            return CSVLineImpl.enquote(element, separator_, doEnquote);
        }



        /**
         * 強制的にエンクォートを行う。.
         * 
         * 
         * @return エンクォートされた文字列
         */
        public String getItemEn() {
            //G001.03.1 staticクラスに変更
            return CSVLineImpl.enquote(element, separator_, true);
        }

        /**
         * 強制的にエンクォートを行わない。.
         * 
         * 
         * @return エンクォートされない文字列
         */
        public String getItemNoEn() {
            // G001.06.0
            return CSVLineImpl.enquote(element, separator_, false);
        }


        /**
         * エンクォートは一切しない。.
         * 
         * @return エンクォートしていない文字列
         */
        public String getRawItem() {
            return element; //G001.03.1
        }
    }

    /**
     * elements_()メソッドで返される Iteratorクラス.
     */
    static class CSVLineIterator implements Iterator < String > {
        /**
         * ArrayListクラスインスタンス.
         */
        private List < CSVElement > list;


        /**
         * CSVの要素番号.
         * 
         */
        private int index;



        /**
         * CSVLineIteratorのコンストラクタ.
         * <P>
         * CSVLineIteratorのコンストラクタ。引数にCSVElementを格納したリストを指定します。
         * </P>
         * 
         * @param items
         *            Listクラスインスタンス
         * @throws java.lang.RuntimeException
         */
        CSVLineIterator(List < CSVElement > items) {
            if (items == null) {
                throw new RuntimeException("CSVLineクラス内リストが存在しません。");
            }
            list = items;
            index = 0;
        }



        /**
         * 次の要素を取得します.
         * 
         * @return 取得した要素
         * @throws java.lang.ClassCastException
         * @throws java.lang.NullPointerException
         */
        public String next() {
            index++;
            CSVElement element = list.get(index - 1);
            return element.getRawItem();
        }



        /**
         * 残りの要素の有無を確認します.
         * 
         * @return 確認結果
         */
        public boolean hasNext() {
            return index < list.size();
        }



        /**
         * 非サポート処理例外をスローします.
         * <P>
         * 当クラスにおいて、Iterator.remove()メソッドの動作はサポートしていません。
         * </P>
         */
        public void remove() {
            throw new UnsupportedOperationException();

        }
    }



    /**
     * 「"」「,」を含む文字列をCSV形式で出力できるようエンクォート処理を加えて返します.
     * <P>
     * 指定した文字列が「"」(ダブルクォーテーション)か「,」（コンマ）を含んでいるときには文字列全体を「"」でエンクォートし、
     * 「"」を「""」に置き換えます。また文字列が「"」と「,」 のどちらも含んでいないときは、 文字列にエンクォート処理を加えずそのまま返します。
     * </P>
     * 
     * @param item
     *            処理したい文字列
     * @param separator
     *            セパレート文字
     * @return エンクォート処理後の文字列
     */
    public static String enquote(String item, char separator) {
        return enquote(item, separator, false);
    }



    /**
     * エンクォート処理の有無を指定したうえで、指定した文字列をCSV形式で出力できるようエンクォート処理を加えて返します.
     * <P>
     * "enquote"が"true"のときは、文字列に対して強制的にエンクォート処理を加えます。
     * "false"のときは、文字列に「"」か「,」が含まれている際には文字列全体を「"」でエンクォートし、 「"」を「""」に置き換えます。
     * </P>
     * <P>
     * エンクォート処理がFALSEに設定された上で、「"」と「,」のどちらも文字列に含まれていないときは、
     * 文字列にエンクォート処理を加えずそのまま返します。 文字列が空、長さがゼロの文字列だった場合、空の文字列をそのまま返します。
     * </P>
     * 
     * @param item
     *            処理したい文字列
     * @param separator
     *            セパレート文字
     * @param enquote
     *            trueなら強制的にエンクォートする
     * @return item を処理した文字列
     */
    public static String enquote(String item, char separator, boolean enquote) {
        //G001.03.1 delete separator_ = sepletter;

        // nullが引数に指定された場合、空Stringを返します
        //G001.03.1 maintenance-start エンクォート有無の対応
        if (item == null || "".equals(item)) {
            if (enquote) {
                return CSVConst.ESCAPE_ENQUOTE;
            } else {
                return "";
            }
        }
        //G001.03.1 delete-end

        //G001.03.1 maintenance 定数定義への置き換え
        /* G001.05.0 DEL
         * if (item.indexOf(CSVConst.ENQUOTE_CHARACTOR) < 0
         *         && item.indexOf(separator) < 0 && !enquote) {
         *     return item;
         * }
         */

        // G001.05.0 ST
        if (item.indexOf(CSVConst.ENQUOTE_CHARACTOR) < 0
                && item.indexOf(separator) < 0
                && !enquote
                && !enquoteCheck(item)) {
            return item;
        }
        // G001.05.0 ED

        // StringBufferのサイズは、最も異常な場合を想定した。
        // 文字列 """"" をエンクォートして出力するようなときのこと。
        StringBuffer sb = new StringBuffer(item.length() * 2 + 2);
        sb.append(CSVConst.ENQUOTE_CHARACTOR);
        for (int index = 0; index < item.length(); index++) {
            char ch = item.charAt(index);
            if (CSVConst.ENQUOTE_CHARACTOR == ch) {
                sb.append(CSVConst.ESCAPE_ENQUOTE);
            } else {
                sb.append(ch);
            }
        }
        sb.append(CSVConst.ENQUOTE_CHARACTOR);  //G001.03.1 定数定義へ置き換え

        return new String(sb);
    }

    /**
     * エスケープシーケンスチェック.
     * 文字列中にエスケープシーケンスが含まれているかチェックする。
     * @param item 検索文字列
     * @return エスケープシーケンス含む:true　含まない:false
     */
    private static boolean enquoteCheck(String item) {
        boolean ret = true;

        if (item.indexOf(CSVConst.ESCAPE_ENTER) < 0
                && item.indexOf(CSVConst.ESCAPE_BACK) < 0
                && item.indexOf(CSVConst.ESCAPE_PAGE) < 0
                && item.indexOf(CSVConst.ESCAPE_TOP) < 0
                && item.indexOf(CSVConst.ESCAPE_TAB) < 0
                && item.indexOf(CSVConst.ESCAPE_BACKSLASH) < 0
                && item.indexOf(CSVConst.ESCAPE_SQUATE) < 0
                && item.indexOf(CSVConst.ESCAPE_DQUATE) < 0
                && item.indexOf(CSVConst.ESCAPE_NULL) < 0) {
            ret = false;
        }
        return ret;
    }

}