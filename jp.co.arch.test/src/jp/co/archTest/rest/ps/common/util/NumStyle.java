/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080229 M.Hirano(TJ)      G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;

/**
 * 数値スタイル.
 *
 * 数値のフォーマットと解析を行うためのクラスです。
 *
 * {@link java.text.DecimalFormat}機能を縮小化し、３桁カンマ区切りやゼロ埋め
 * などの業務で多用する数値形式の扱いに特化して設計されました。
 *
 * <B>◆書式のコード化</B><BR>
 * 数値スタイルでは、次のとおり２の乗数の整数値でコード化した値を書式コードとして、
 * 本機能が定数として提供します。
 * 有効桁数とともに、この書式コードを指定するシンプルなＡＰＩを提供します。
 * （下位１６ビットは有効桁数指定用）<BR>
 * <PRE>
 * +----------+------+------------------------+-------------------------+
 * |          |      |                        |           例            |
 * |書式コード|  値  |   内容                 +-----------+-------------+
 * |(定数名)  |      |                        | 実数      | 書式化後    |
 * +----------+------+------------------------+-----------+-------------+
 * |ZERO_L    |1<<16 |前置ゼロパディング      | 123       | 00000123    |
 * |ZERO_R    |1<<17 |後置ゼロパディング      | 123.4     | 123.40      |
 * |COMMA     |1<<17 |３桁カンマ区切り。      | 1234567.89| 1,234,567.89|
 * |SIGN_L    |1<<18 |負符号を前置表記        | -1234     | -1234       |
 * |SIGN_R    |1<<19 |負符号を後置表記        | -1234     | 1234-       |
 * |SIGN_B    |1<<20 |負符号と数値の間に空白  | -1234     | 1234 -      |
 * |CURR_L    |1<<21 |通貨記号を前置表記      | 1234      | \1234       |
 * |CURR_R    |1<<22 |通貨記号を後置表記      | 1234      | 1234\       |
 * |CURR_B    |1<<23 |通貨記号と数値の間に空白| 1234      | \ 1234      |
 * |ROUND_UP  |1<<24 |下位桁あふれの切り上げ  | 12345.123 | 12345.13    |
 * |ROUND_DW  |1<<25 |下位桁あふれの切り捨て  | 12345.456 | 12345.45    |
 * |ROUND_HF  |1<<27 |下位桁あぐれの四捨五入  | 12345.789 | 12345.79    |
 * +----------+------+------------------------+-----------+-------------+
 * </PRE>
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * @author M.Hirano(TJ)
 */
public final class NumStyle implements Cloneable { // parasoft-suppress METRICS.NOF "問題がない為"

    /**
     * 整数部をゼロパディングする書式コード.
     */
    public static final int ZERO_L = 1 << 16;


    /**
     * 小数部をゼロパディングする書式コード.
     */
    public static final int ZERO_R = 1 << 17;


    /**
     * ３桁カンマ区切りの書式コード.
     */
    public static final int COMMA = 1 << 17;


    /**
     * 負数値の場合に符号を前置表記する書式コード.
     */
    public static final int SIGN_L = 1 << 19;


    /**
     * 負数値の場合に符号を後置表記する書式コード.
     */
    public static final int SIGN_R = 1 << 20;


    /**
     * 負数値の場合に符号と数値部の間に△（空白）を表記する書式コード.
     */
    public static final int SIGN_B = 1 << 21;


    /**
     * 負数値の場合に符号＋△を前置表記する書式マクロ.
     */
    public static final int SIGN_LB = SIGN_L | SIGN_B;


    /**
     * 負数値の場合に△＋符号を後置表記する書式マクロ.
     */
    public static final int SIGN_RB = SIGN_R | SIGN_B;


    /**
     * 通貨記号を前置表記する書式コード.
     */
    public static final int CURR_L = 1 << 22;


    /**
     * 通貨記号を後置表記する書式コード.
     */
    public static final int CURR_R = 1 << 23;


    /**
     * 通貨記号と数値部の間に△（空白）を表記する書式コード.
     */
    public static final int CURR_B = 1 << 24;


    /**
     * 通貨記号＋△を前置表記する書式マクロ.
     */
    public static final int CURR_LB = CURR_L | CURR_B;


    /**
     * △＋通貨記号を後置表記する書式マクロ.
     */
    public static final int CURR_RB = CURR_R | CURR_B;


    /**
     * 下位桁あふれの場合に切上げる丸めオプション.
     */
    public static final int ROUND_UP = 1 << 25;

    /**
     * 下位桁あふれの場合に切捨てる丸めオプション.
     */
    public static final int ROUND_DW = 1 << 26;


    /**
     * 下位桁あふれの場合に四捨五入する丸めオプション.
     * 省略時はこのオプションが適用されます。
     */
    public static final int ROUND_HF = 1 << 27;



    /**
     * 数値部の長さを抜き出すマスク値.
     */
    protected static final int LEN_MASK = 0xFFFF;


    /**
     * シンボルの配置が前置か評価するためのマスク値.
     */
    private static final int SYMBOL_L = SIGN_L | CURR_L;


    /**
     * シンボルの配置が後置か評価するためのマスク値.
     */
    private static final int SYMBOL_R = SIGN_R | CURR_R;


    /**
     * シンボルと数値の間に△（空白）を表記するか評価するためのマスク値.
     */
    private static final int SYMBOL_B = SIGN_B | CURR_B;


    /**
     * 丸めオプションを抜き出すためのマスク値.
     */
    private static final int ROUND = ROUND_UP | ROUND_DW | ROUND_HF;


    /**
     * 通貨記号の置換変数値.
     */
    private static final char CURR_MARK = '\u00A4';


    /**
     * 拡張定義の日付スタイルを一覧記憶するコンテナ.
     */
    private static Map<String, NumStyle> namedStyles
        = new HashMap<String, NumStyle>();


    /**
     * 数値フォーマッタ.
     */
    private DecimalFormat nf_ = new DecimalFormat();


    /**
     * 丸めオプション.
     */
    private int round_;


    /**
     * 桁数.
     */
    private int[] len_ = new int[] { 0, 0 };

    /**
     * 書式コード指定のコンストラクタ.
     *
     * @param nfCode 書式コード
     */
    private NumStyle(double nfCode) {
        this(parsePattern(nfCode));
        this.round_ = (( int ) nfCode & ROUND);
    }


    /**
     * 書式パターン指定のコンストラクタ.
     *
     * @param pattern 書式パターン
     */
    private NumStyle(String pattern) {
        this.nf_.applyPattern(pattern);

        int group = 0;
        boolean escape = false;
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            if (ch == ';') {
                break;
            }
            if (ch == '\'') {
                escape = !escape;
            }
            if (escape) {
                continue;
            }
            if (ch == '0' || ch == '#') {
                this.len_[group]++;

            } else if (ch == '.') {
                group = 1;
            }
        }
        this.round_ = 0;
    }



    /**
     * 書式コードの解析.
     *
     * 書式コードを解析し書式パターンを生成します。
     * @param pattern 書式コード
     * @return 書式パターン
     */
    private static String parsePattern(double pattern) {
        // double値の整数桁数と小数桁数を得る
        int intValue = ( int ) pattern;
        double decValue = pattern - ( double ) intValue;
        if (0 < decValue) {
            decValue = Double.parseDouble(new DecimalFormat(".0#").format(
                    decValue).substring(1));
        }

        // 長さ部分をマスク
        int intLen = intValue & LEN_MASK;
        int decLen = ( int ) decValue;

        // オプション値を得る
        int opt = intValue - intLen;

        // 桁数未指定は１桁とする
        if (intLen == 0 && decLen == 0) {
            intLen = 1;
        }

        // パターンを生成（まず正数用）
        String format = parsePattern(intLen, decLen, opt);

        // 負数用パターンの解析バッファを初期化
        StringBuilder buf = new StringBuilder(format);

        // 符号指定を解析
//        DecimalFormatSymbols dfs = this.nf_.getDecimalFormatSymbols();
//        parseSymbol(opt & (SIGN_LB | SIGN_RB), dfs.getMinusSign(), buf);
        parseSymbol(opt & (SIGN_LB | SIGN_RB), '-', buf);

        // 正数パターン;負数パターンを返す
        return buf.insert(0, ';').insert(0, format).toString();
    }


    /**
     * 正数用書式コードの解析.
     *
     * {@link #parsePattern(double)}で分解された書式コードをもとに正数用の
     * 書式パターンを生成します。
     *
     * @param intLen 整数桁数
     * @param decLen 小数桁数
     * @param opt 書式コードオプション
     * @return 書式パターン
     */
    private static String parsePattern(int intLen, int decLen, int opt) {
        // 書式生成用のバッファを作成
        StringBuilder buf = new StringBuilder();

        // 整数部の書式を作る
        // 一の位は必ず０表示
        buf.append('0');

        // 十の位以上の書式を作る
        for (int i = 1; i < intLen; i++) {
            if (isHit(opt, COMMA) && 3 <= i && (i % 3) == 0) {
                buf.insert(0, ',');
            }
            if (isHit(opt, ZERO_L)) {
                buf.insert(0, '0');
            } else {
                buf.insert(0, '#');
            }
        }

        // 小数部の書式を追加
        if (0 < decLen) {
            buf.append(".0");
            for (int i = 1; i < decLen; i++) {
                if (isHit(opt, ZERO_R)) {
                    buf.append('0');
                } else {
                    buf.append('#');
                }

            }
        }

        // 通貨の置換文字を追加
        parseSymbol(opt & (CURR_LB | CURR_RB), CURR_MARK, buf);

        return buf.toString();
    }



    /**
     * シンボル書式の解析.
     *
     * 符号サインや通貨記号の書式コードオプションを解析し、シンボル表記用の
     * 書式パターンをバッファの適切な配置に追加します。
     *
     * @param opt 書式コードオプション
     * @param symbol シンボル記号
     * @param buf バッファ
     */
    private static void parseSymbol(
            int opt, char symbol, StringBuilder buf) {
        if (0 < (opt & SYMBOL_L)) {
            if (0 < (opt & SYMBOL_B)) {
                buf.insert(0, ' ');
            }
            buf.insert(0, symbol);

        } else if (0 < (opt & SYMBOL_R)) {
            if (0 < (opt & SYMBOL_B)) {
                buf.append(' ');
            }
            buf.append(symbol);
        }
    }



    /**
     * 整数値のビット評価.
     *
     * @param value 評価対象の整数値
     * @param bit ビット値
     * @return  指定ビット値がオンの場合 true
     */
    private static boolean isHit(int value, int bit) {
        return bit == (value & bit);
    }


    /**
     * 桁数チェック.
     *
     * 書式が示す整数/小数の桁数と照合して、上下のオーバフロー処理を行います。
     * 下位桁のあふれは {@link #setRound(int)} で指定されたオプションに従って
     * 丸め演算されます。
     *
     * @param value チェック対象の数値形式の値
     * @param checkOverflow 上位桁あふれをチェックする場合 true を指定
     * @return 下位桁のあふれを丸めた値
     */
    private BigDecimal parseDigit(Object value, boolean checkOverflow) {
        BigDecimal num = NumUtils.toNumber(value);
        int intMax = this.len_[0];
        int decMax = this.len_[1];
        int round = this.round_;

//        if (decMax < num.scale()) {
//            round = ROUND_HF;
//        }

        switch (round) {
            case ROUND_UP:
                num = num.setScale(decMax, BigDecimal.ROUND_UP);
                break;

            case ROUND_DW:
                num = num.setScale(decMax, BigDecimal.ROUND_DOWN);
                break;

            case ROUND_HF:
                num = num.setScale(decMax, BigDecimal.ROUND_HALF_UP);
                break;

            default:
                break;
//                num = num.setScale(decMax, BigDecimal.ROUND_UNNECESSARY);
        }
        if (checkOverflow) {
            int intLen = new DecimalFormat("#").format(num.abs()).length();
            if (intMax < intLen) {
                throw new ArithmeticException(
                            "overflow " + intMax + " < " + intLen);
            }
        }
        return num;
    }


    /**
     * 数値スタイルの取得.
     *
     * 指定された書式コードで初期化された数値スタイルを返します。
     *
     * @param pattern 書式コード
     * @return 数値スタイル
     */
    public static NumStyle valueOf(double pattern) {
        return new NumStyle(pattern);
    }


    /**
     * 数値スタイルの取得.
     *
     * 指定された書式パターンで初期化された数値スタイルを返します。
     *
     * @param pattern 書式パターン
     * @return 数値スタイル
     */
    public static NumStyle valueOf(String pattern) {
        return new NumStyle(pattern);
    }


    /**
     * 数値スタイルの取得.
     *
     * {@link #bind(String)}メソッドで命名した数値スタイルを再利用する場合、
     * このメソッドで参照します。
     *
     * @param name  名前
     * @return 数値スタイル。存在しない場合は null値
     */
    public static NumStyle lookup(String name) {
        return namedStyles.get(name);
    }


    /**
     * 通貨記号の設定.
     * @param symbol 通貨記号
     * @return 自身を返す
     */
    public NumStyle setCurrency(String symbol) {
        synchronized (this.nf_) {
            DecimalFormatSymbols dfs = this.nf_.getDecimalFormatSymbols();
//            dfs = DecimalFormatSymbols.class.cast(dfs.clone());
            dfs.setCurrencySymbol(symbol);
            this.nf_.setDecimalFormatSymbols(dfs);
        }
        return this;
    }


    /**
     * 負符号の設定.
     * @param symbol 負符号
     * @return 自身を返す
     */
    public NumStyle setMinusSign(char symbol) {
        synchronized (this.nf_) {
            DecimalFormatSymbols dfs = this.nf_.getDecimalFormatSymbols();
            dfs.setMinusSign(symbol);
            this.nf_.setDecimalFormatSymbols(dfs);
        }
        return this;
    }


    /**
     * 丸めオプションの設定.
     *
     * @param round 丸めオプション。３つの定数値（
     * {@link #ROUND_UP}
     * {@link #ROUND_DW}
     * {@link #ROUND_HF}
     * ）のいずれかを指定する
     * @return 自身を返す
     */
    public NumStyle setRound(int round) {
        this.round_ = round;
        return this;
    }

    /**
     * フォーマット.
     *
     * @param value 数値形式の値
     * @return フォーマットされた数値文字列
     */
    public String format(Object value) {
        synchronized (this.nf_) {
            return this.nf_.format(this.parseDigit(value, false));
        }
    }


    /**
     * 妥当性検査.
     *
     * @param value 検査対象の文字列
     * @return 検査ＯＫの場合 true
     */
    public boolean accept(Object value) {
        try {
            this.parse(value);
            return true;

        } catch (NumberFormatException e) {
            return false;

        } catch (ArithmeticException e) {
            return false;
        }
    }


    /**
     * 解析.
     *
     * 数値でない場合は{@link NumberFormatException}が発生します。
     * 桁あふれの場合は{@link ArithmeticException}が発生します。
     *
     * @param value 解析対象の値
     * @return 解析されたNumber
     */
    public Number parse(Object value) { //throws ParseException {
        synchronized (this.nf_) {
 //           System.out.println(this.nf_.parse(value.toString()));
            return this.parseDigit(value, true);
//                    this.nf_.parse(value.toString()), true);
        }
    }




    /**
     * 数値スタイルの複製.
     *
     * @return 複製した数値スタイルオブジェクト
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        try {
            NumStyle copy = ( NumStyle ) super.clone();
            copy.nf_ = DecimalFormat.class.cast(this.nf_.clone());
            copy.len_ = new int[this.len_.length];
            System.arraycopy(this.len_, 0, copy.len_, 0, this.len_.length);
            copy.round_ = this.round_;
//            copy.nf_.applyPattern(this.nf_.toPattern());
            return copy;

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 数値スタイルのバインド.
     *
     * よく使うスタイルを共用する場合、このメソッドで名前を付けてメモリに永続
     * 記憶することができます。再利用する場合は {@link #lookup(String)}で名前
     * を指定して参照します。
     *
     * 同じ名前が既にバインドされている場合は登録が書き換えられます。
     *
     * @param name  名前
     * @return バインドされたスタイル
     * ※命名されて記憶されるものは複製オブジェクトです。
     */
    public NumStyle bind(String name) {
        NumStyle copy = NumStyle.class.cast(this.clone());
        synchronized (namedStyles) {
            namedStyles.put(name, copy);
        }
        return copy;
    }


    /**
     * 数値スタイルのアンバインド.
     *
     * バインドした数値スタイルの名前を解放します。
     * @param name  名前
     */
    public void unbind(String name) {
        synchronized (namedStyles) {
            namedStyles.remove(name);
        }
    }

}
