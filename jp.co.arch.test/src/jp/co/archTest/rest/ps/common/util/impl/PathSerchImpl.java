/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20171227 FanMH(bbasoft)     G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.common.util.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jp.co.archTest.rest.ps.common.util.PathSerch;

/**
 * リソースパス検索.<BR>
 * 
 * {@link PathSerch}の実装クラスです。
 *
 * <P>
 * Copyright (C) 2017 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * @author FanMH(bba)
 */
public class PathSerchImpl implements PathSerch {
    /** リソースパス. */
    private static final String RES_ROOT = "resources/";
    /** 基本定義ファイル名. */
    private static final String BASE_DEFFILE = "base.properties";
    /** common. */
    private static final String COMMON = "rocky";


    /**
     * パス検索.<BR>
     * リソースファイルをパス検索ルールに従って検索します。
     * 指定したファイル名が見つかった場合は、パス付でファイル名を返します。
     *
     * @param pkgName パッケージ名
     * @param fileName ファイル名
     * @return パス付ファイル名
     * @see rocky.base.common.util.PathSerch#serch(java.lang.String, java.lang.String, java.lang.String)
     * @throws java.lang.ExceptionInInitializerError
     */
    public String serch(String pkgName, String fileName) {
        // 基本定義ファイルの検索
        // 基本定義ファイルはresources/base.propertiesに固定配置
        String strFile = basePropertySerch(fileName);

        /* 基本定義ファイルがない又はファイル内に定義がない場合
         * パッケージの階層を逆順に検索
         */
        if (strFile == null) {
            strFile = srchProperties(fileName, pkgName);
        }
        return strFile;
    }

    /**
     * base.properties内定義検索.<BR>
     * @param fileName ファイル名
     * @return パス付ファイル名
     */
    private String basePropertySerch(String fileName) {
        InputStream is = null;
        String strFile = null;
        try {
            is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(RES_ROOT + BASE_DEFFILE);
            Properties prop = new Properties();
            prop.load(is);
            if (is != null) {
                strFile = prop.getProperty(fileName);
            }
        } catch (Exception e1) {
            noAction();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) { noAction(); }
            }
        }
        return strFile;
    }

    /**
     * resourceパッケージ下検索.<BR>
     * 指定されたパッケージを順次遡って検索します。
     * resource/s論理店/下を検索し、存在しない場合は
     * resource/rocky/下を検索します。
     * 
     * @param logicalStoreCd 論理店舗コード
     * @param strFileName ファイル名
     * @param strPackage パッケージ情報
     * @return String 検索対象の基本定義ファイルのフルパス情報
     */
    private String srchProperties(String strFileName, String strPackage) {
        // パッケージを分割
        String[] strPac = strPackage.split("\\.");

        String retStr = null;
        retStr = packageSerch(strPac, strFileName);

        return retStr;
    }

    /**
     * resourceパッケージ下検索.<BR>
     * 指定されたパッケージを順次遡って検索します。
     * resource/s論理店/下を検索し、存在しない場合は
     * resource/rocky/下を検索します。
     * 
     * @param strPac 分割されたパッケージ名
     * @param strFileName 検索ファイル名
     * @return パス付ファイル名
     */
    private String packageSerch(String[] strPac, String strFileName) {
        String retStr = null;
        InputStream is = null;

        for (int i = strPac.length; i >= 0; i--) {
            StringBuilder sb = new StringBuilder();
            sb.append(RES_ROOT);

                sb.append(COMMON);

            sb.append("/");
            for (int j = 1; j < i; j++) {
                sb.append(strPac[j]).append("/");
            }
            sb.append(strFileName);
            is = Thread.currentThread().getContextClassLoader()
                                .getResourceAsStream(sb.toString());
            // 指定ファイルが見つかった
            if (is != null) {
                retStr = sb.toString();
                break;
            }
        }

        if (is != null) {
            try {
                is.close();
            } catch (IOException e) { noAction(); }
        }

        return retStr;
    }

    /**
     * ダミーメソッド.
     * 何も処理しない。CheckStyle警告回避対応
     */
    private void noAction() {
    }
}
