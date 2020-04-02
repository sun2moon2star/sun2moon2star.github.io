/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080229 M.Hirano(TJ)      G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 数値ユーティリティ.
 *
 * 数値に関する操作を行うための簡易メソッド群を提供します。
 * これはユーティリティとして定義されたクラスでインスタンス化することは
 * できません。メソッドはすべて静的にアクセスする必要があります。
 * <P>
 * 書式フォーマット変換するにあたり、数値スタイルクラス({@link NumStyle})に定義された
 * 書式コードを指定することが出来ます。
 * </P>
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * @author M.Hirano(TJ)
 */
public final class NumUtils {

    /**
     * コンストラクタ.
     */
    private NumUtils() {
        super();
    }



    /**
     * 文字シーケンスが数値形式か否か評価.<BR>
     *
     * 指定文字シーケンスが数値として妥当性のあるものか評価します。
     * 書式指定した場合は数値形式であっても指定書式に該当しなければ無効と評価
     * されます。<BR>
     *
     * 書式は"#,##0.00"などのパターンを指定します。
     * 詳しくは、{@link java.text.DecimalFormat}のパターンを参照願います。
     * 
     * @param value 評価対象の文字シーケンス
     * @param formats 書式パターン
     * @return 有効な数値の場合 true
     */
    public static boolean accept(CharSequence value, String... formats) {
        if (value == null || value.length() <= 0) {
            return false;
        }
        String str = value.toString();
        try {
            parseNumber(str);

        } catch (NumberFormatException e) {
            return false;
        }


        if (formats == null || formats.length <= 0) {
            return true;
        }
        for (String format : formats) {
            if (format == null) {
                continue;
            }
            try {
                NumStyle.valueOf(format).parse(value);
                return true;

            } catch (Exception e) {
                doVoid();
            }
        }
        return false;
    }



    /**
     * 数値形式の値の加算.<BR>
     * 
     * 引数で設定された２項以上の数値を加算し、
     * 結果をlong型で返します。
     *
     * @param values 数値形式の値
     * @return 加算後の値（long）
     */
    public static long add(Object... values) {
        return ( long ) addf(values);
    }



    /**
     * 数値形式の値の加算.<BR>
     * 引数で設定された２項以上の数値を加算し、結果を
     * double型で返します。
     * 
     * @param values 数値形式の値
     * @return 加算後の値（double）
     * @throws java.lang.IllegalArgumentException
     */
    public static double addf(Object... values) {
        if (values == null || values.length < 2) {
            throw new IllegalArgumentException(
                    "指定した引数(２項必要)が不正です。");
        }
        BigDecimal result = new BigDecimal(0);
//        if (values != null) {
        for (int i = 0; i < values.length; i++) {
            result = result.add(toNumber(values[i], true));
        }
//        }
        return result.doubleValue();
    }



    /**
     * 数値形式の値の減算.<BR>
     *
     * 第１引数から第２引数以降の値を減算し、
     * 結果をlong型で返します。
     *
     * @param values 数値形式の値
     * @return 減算後の値（long）
     */
    public static long sub(Object... values) {
        return ( long ) subf(values);
    }



    /**
     * 数値形式の値の減算.
     *
     * 第１引数から第２引数以降の値を減算し、
     * 結果をdouble型で返します。
     *
     * @param values 数値形式の値
     * @return 減算後の値（double）
     * @throws java.lang.IllegalArgumentException
     */
    public static double subf(Object... values) {
        if (values == null || values.length < 2) {
            throw new IllegalArgumentException(
                    "指定した引数(２項必要)が不正です。");
        }
        BigDecimal result = new BigDecimal(0);
//        if (values != null) {
        result = result.add(toNumber(values[0], true));
        for (int i = 1; i < values.length; i++) {
            result = result.subtract(toNumber(values[i], true));
        }
//        }
        return result.doubleValue();
    }


    /**
     * ２項の数値形式の値の比較.<BR>
     * 
     * 第１引数(lValue)を左辺、第２引数(rValue)を右辺として
     * ２項の数値の比較を行います。
     * 両者が同一値の場合、０を返します。
     * 両者が不一致の場合は、左辺－右辺の差分値を返します。<BR>
     * 引数にnullが設定された場合、IllegalArgumentExceptionを
     * throwします。
     * また、内部で引数値をdouble型にcast出来なかった場合、
     * NumberFormatExceptionをthrowします。
     * 
     * @param lValue 左辺側の数値形式の値
     * @param rValue 右辺側の数値形式の値
     * @return 左辺－右辺の差を返す
     * @throws java.lang.IllegalArgumentException
     */
    public static double compare(Object lValue, Object rValue) {
        if (lValue == null || rValue == null) {
            throw new IllegalArgumentException(
                    "指定した引数(null)が不正です。");
        }
        return toDouble(lValue, true) - toDouble(rValue, true);
    }


    /**
     * ２項の数値形式の値の比較.<BR>
     * 第１引数(lValue)を左辺、第２引数(rValue)を右辺として
     * ２項の数値の比較を行います。
     * 両者が同一値の場合、trueを返します。
     * 両者が不一致の場合は、falseを返します。<BR>
     * 引数にnullが設定された場合、IllegalArgumentExceptionを
     * throwします。
     * また、内部で引数値をdouble型にcast出来なかった場合、
     * NumberFormatExceptionをthrowします。
     *
     * @param lValue 左辺側の数値形式の値
     * @param rValue 右辺側の数値形式の値
     * @return 左辺＝右辺の場合 true を返す
     */
    public static boolean equals(Object lValue, Object rValue) {
        return compare(lValue, rValue) == 0;
    }


    /**
     * フォーマット.
     *
     * 書式パターンを指定して数値形式の値をフォーマットします。
     *
     * @param value 数値形式の値
     * @param pattern 書式パターン
     * @return 書式化後の数値文字列
     */
    public static String format(Object value, String pattern) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "指定した引数(null)が不正です。");
        }
        return NumStyle.valueOf(pattern).format(value);
    }


    /**
     * フォーマット.
     *
     * 書式コードを指定して数値形式の値をフォーマットします。
     * 数値の有効桁数を指定する場合は、桁数+書式コードで指定します。<BR>
     * 例）<BR>
     * <PRE>
     * <B>Integer型の10000を３桁カンマ編集する場合</B><BR>
     * 有効桁は５桁
     * String ret = NumUtils.format(intVal, 5 + NumStyle.COMMA);
     * String型retには"10,000"が格納されます。
     * 
     * <B>Integer型の10000を￥記号付で３桁カンマ編集する場合</B><BR>
     * 書式コードを組み合わせます。
     * String ret = NumUtils.format(intVal, 5 + NumStyle.COMMA + NumStyle.CURR_L);
     * String型retには"￥10,000"が格納されます。
     * </PRE>
     *
     * @param value 数値形式の値
     * @param nfCode 書式コード
     * @return 書式化後の数値文字列
     */
    public static String format(Object value, double nfCode) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "指定した引数(null)が不正です。");
        }
        return NumStyle.valueOf(nfCode).format(value);
    }



    /**
     * 数値形式の値のint変換.
     *
     * <pre>
     * 不正な引数が指定された場合は以下の例外がスローされます。
     * ・ClassCastException - 引数の型が Number や CharSequece 以外
     * ・IllegalArgumentException - 引数の値が null値
     * </pre>
     * 引数の値が数値形式でない場合は「-1」が返されます。
     *
     * @param value 数値形式の値
     * @return 型変換後のint値
     */
    public static int toInt(Object value) {
        return ( int ) toDouble(value);
    }



    /**
     * 数値形式の値のlong変換.
     *
     * <pre>
     * 不正な引数が指定された場合は以下の例外がスローされます。
     * ・ClassCastException - 引数の型が Number や CharSequece 以外
     * ・IllegalArgumentException - 引数の値が null値
     * </pre>
     * 引数の値が数値形式でない場合は「-1」が返されます。
     *
     * @param value 数値形式の値
     * @return 型変換後のlong値
     */
    public static long toLong(Object value) {
        return ( long ) toDouble(value);
    }



    /**
     * 数値形式の値のdouble変換.
     *
     * <pre>
     * 不正な引数が指定された場合は以下の例外がスローされます。
     * ・ClassCastException - 引数の型が Number や CharSequece 以外
     * ・IllegalArgumentException - 引数の値が null値
     * </pre>
     * 引数の値が数値形式でない場合は「-1」が返されます。
     *
     * @param value 数値形式の値
     * @return 型変換後のdouble値
     */
    public static double toDouble(Object value) {
        return toDouble(value, false);
    }


    /**
     * 数値形式の値のdouble変換.
     *
     * <pre>
     * 不正な引数が指定された場合は以下の例外がスローされます。
     * ・ClassCastException - 引数の型が Number や CharSequece 以外
     * ・IllegalArgumentException - 引数の値が null値
     * 引数の値が数値形式でない場合は次のいずれか
     * ・throwError == true  : NumberFormatException をスロー
     * ・throwError == false :「-1」を返す。
     * </pre>
     *
     * @param value 数値形式の値
     * @param throwError 数値形式が正しくない場合に例外をスローするか否か
     * @return 型変換後のdouble値
     * @throws java.lang.IllegalArgumentException
     * @throws java.lang.ClassCastException
     */
    private static double toDouble(Object value, boolean throwError) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "指定した引数(null)が不正です。");
        }
        try {
            if (Number.class.isInstance(value)) {
                return (( Number ) value).doubleValue();

            } else if (CharSequence.class.isInstance(value)) {
                return parseDouble(( CharSequence ) value);

            }

            throw new ClassCastException(value.getClass().getName());

        } catch (NumberFormatException e) {
            if (throwError) {
                throw e;
            }
            return -1;
        }
    }


    /**
     * 数値形式の値のNumber(BigDecimal)変換.
     * <pre>
     * 不正な引数が指定された場合は以下の例外がスローされます。
     * ・ClassCastException - 引数の型が Number や CharSequece 以外
     * ・IllegalArgumentException - 引数の値が null値
     * </pre>
     * 引数の値が数値形式でない場合は「-1」が返されます。
     *
     * @param value 数値形式の値
     * @return 型変換後のBigDecimal値
     */
    public static BigDecimal toNumber(Object value) {
        return toNumber(value, false);
    }


    /**
     * 数値形式の値のNumber(BigDecimal)変換.
     * <pre>
     * 不正な引数が指定された場合は以下の例外がスローされます。
     * ・ClassCastException - 引数の型が Number や CharSequece 以外
     * ・IllegalArgumentException - 引数の値が null値
     * 引数の値が数値形式でない場合は次のいずれか
     * ・throwError == true  : NumberFormatException をスロー
     * ・throwError == false :「-1」を返す。
     * </pre>
     *
     * @param value 数値形式の値
     * @param throwError 数値形式が正しくない場合に例外をスローするか否か
     *
     * @return 型変換後のBigDecimal値
     * @throws java.lang.IllegalArgumentException
     * @throws java.lang.ClassCastException
     */
    private static BigDecimal toNumber(Object value, boolean throwError) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "指定した引数(null)が不正です。");
        }
        try {
            // BigDecimal型はそのまま返す
            if (BigDecimal.class.isInstance(value)) {
                return ( BigDecimal ) value;

            // BigInteger型は変換して返す
            } else if (BigInteger.class.isInstance(value)) {
                return new BigDecimal(( BigInteger ) value);

            // Number型は一度文字列化して変換
            // Number#doubleValue()からBigDecimal化しないこと！
            // …精度違いから誤差が生じるため
            } else if (Number.class.isInstance(value)) {
                return new BigDecimal((( Number ) value).toString());

            // 文字シーケンスは解析結果を返す
            } else if (CharSequence.class.isInstance(value)) {
                return parseNumber(( CharSequence ) value);
            }

            throw new ClassCastException(value.getClass().getName());

        } catch (NumberFormatException e) {
            if (throwError) {
                throw e;
            }
            return new BigDecimal(-1);
        }
    }

    /**
     * 文字列のint解析.
     *
     * <pre>
     * 不正な引数が指定された場合は以下の例外がスローされます。
     * ・IllegalArgumentException - 引数の値が null値
     * ・NumberFormatException - 引数の値が 整数形式でない場合
     * </pre>
     *
     * @param value 解析対象の文字列
     * @return 型変換後のint値
     */
    public static int parseInt(CharSequence value) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "指定した引数(null)が不正です。");
        }
        return Integer.parseInt(value.toString());
    }



    /**
     * 文字列のlong解析.
     *
     * <pre>
     * 不正な引数が指定された場合は以下の例外がスローされます。
     * ・IllegalArgumentException - 引数の値が null値
     * ・NumberFormatException - 引数の値が LONG整数形式でない場合
     * </pre>
     *
     * @param value 解析対象の文字列
     * @return 型変換後のlong値
     */
    public static long parseLong(CharSequence value) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "指定した引数(null)が不正です。");
        }
        return Long.parseLong(value.toString());
    }



    /**
     * 文字列のdouble解析.
     *
     * <pre>
     * 不正な引数が指定された場合は以下の例外がスローされます。
     * ・IllegalArgumentException - 引数の値が null値
     * ・NumberFormatException - 引数の値が double形式でない場合
     * </pre>
     * @param value 解析対象の文字列
     * @return 型変換後のdouble値
     */
    public static double parseDouble(CharSequence value) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "指定した引数(null)が不正です。");
        }
        return Double.parseDouble(value.toString());
    }


    /**
     * 文字列のNumber解析.
     *
     * <pre>
     * 不正な引数が指定された場合は以下の例外がスローされます。
     * ・IllegalArgumentException - 引数の値が null値
     * ・NumberFormatException - 引数の値が 数値形式でない場合
     * </pre>
     *
     * @param value 解析対象の文字列
     * @return 型変換後のNumber(BigDecimal)値
     */
    public static BigDecimal parseNumber(CharSequence value) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "指定した引数(null)が不正です。");
        }
        return new BigDecimal(value.toString());
    }


    /**
     * 空メソッド.
     *
     * CheckStyleの「Empty Block」警告を回避するためのメソッドです。
     * 何もせずスタックを無駄に作るのみです。
     */
    private static void doVoid() {
    }
}
