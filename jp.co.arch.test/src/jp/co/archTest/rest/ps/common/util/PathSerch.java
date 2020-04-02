/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080625 Yamada.N(TIS)     G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.common.util;
/**
 * リソースパス検索.<BR>
 * 
 * 指定されたリソースファイルを検索ルールに従って検索します。
 * 検索ルールは以下の通りです。<BR>
 * <BR>
 * <PRE>
 * １）resources/base.properties定義検索<BR>
 *     base.propertiesは必ずresources直下に配置します。
 *
 *    ｂ）共通定義の検索
 *     "TestFile.txt"キー定義を検索します。
 *    "TestFile.txt"キー定義が存在した場合、対応する値を返します。
 *        --------------------------------------
 *     TestFile.txt=C:/TestFile_1.txt
 *                      ~~~~~~~~~~~~~~~~~~
 *        --------------------------------------
 *        
 * ２）resources/パッケージ下の検索<BR>
 *  １）に於て、base.propertiesに定義が存在しなかった場合、resources/パッケージ下を検索します。
 *   共通パッケージ下の検索
 *    resources/rocky/base/common下から順次上位パッケージへ遡ってTestFile.txtが存在するか検索します。
 *     resources/rocky/base/common/TestFile.txtが存在するか?
 *         存在しなければ、
 *     resources/rocky/base/TestFile.txtが存在するか?
 *         存在しなければ、
 *     resources/rocky/TestFile.txtが存在するか?
 * </PRE>
 * 上記検索でファイルが見つからなかった場合はNullを返します。
 *
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * @author Yamada.N_TIS
 */
public interface PathSerch {
    /**
     * パス検索.<BR>
     * リソースファイルをパス検索ルールに従って検索します。
     * 指定したファイル名が見つかった場合は、パス付でファイル名を返します。
     * 
     * @param pkgName 呼出し元クラスのパッケージ名
     * @param fileName ファイル名
     * @return ファイルパス 存在しない場合はNull
     */
    public String serch(String pkgName, String fileName);
}
