/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080415 K.Adachi(TJ)      G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.common.csv.impl;

/**
 * CSV変換機能クラスが使用する定数を保持するクラスです.
 * <P>
 * CSV変換機能クラスが使用する定数を保持するクラスです。 クォート文字、セパレート文字、エンクォート文字のエスケープ表記を定数でフィールドに所有します。
 * </P>
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * 
 * @author K.Adachi(TJ)
 */
public final class CSVConst {
    /**
     * エンクォート文字定数です。. エンクォートの際に用いる文字定数「"」（ダブルクォーテーション）です。
     */
    public static final char ENQUOTE_CHARACTOR = '"';


    /**
     * 標準の、CSVデータセパレート文字です。.
     * <P>
     * 標準の、CSVデータを要素に分割するためのセパレート用文字定数「,」(コンマ)です。
     * </P>
     */
    public static final char DEFAULT_SEPARATOR = ',';


    /**
     * エンクォート文字のエスケープ表記です。.
     * <P>
     * エンクォート文字のエスケープ表記「\"\"」（（バックスラッシュ＋二重引用符）＊２）です。
     * </P>
     */
    public static final String ESCAPE_ENQUOTE = "\"\"";

    /** エスケープシーケンス(\b １文字分戻る). */
    public static final String ESCAPE_BACK = "\b";
    /** エスケープシーケンス(\f ページ送り). */
    public static final String ESCAPE_PAGE = "\f";
    /** エスケープシーケンス(\n 改行、復帰). */
    public static final String ESCAPE_ENTER = "\n";
    /** エスケープシーケンス(\r 同じ行の先頭に戻る). */
    public static final String ESCAPE_TOP = "\r";
    /** エスケープシーケンス(\t 水平タブ). */
    public static final String ESCAPE_TAB = "\t";
    /** エスケープシーケンス(\\ \を表示). */
    public static final String ESCAPE_BACKSLASH = "\\";
    /** エスケープシーケンス\' 'を表示). */
    public static final String ESCAPE_SQUATE = "\'";
    /** エスケープシーケンス(\" "を表示). */
    public static final String ESCAPE_DQUATE = "\"";
    /** エスケープシーケンス(\0 ヌル). */
    public static final String ESCAPE_NULL = "\0";

    /**
     * コンストラクタ.
     */
    private CSVConst() {

    }
}