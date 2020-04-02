/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20190115 FanMH(bba)        G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.v1.posIF;

/**
 * POSI/F内で共有する定数を宣言した定数クラスです.
 * 
 * <P>
 * POSI/F内で共通に利用する定数を宣言したクラスです。
 * </P>
 * 
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * 
 * @author J.Ochiai_TJ
 */
public final class PosifConstants {

    // 処理戻り値
    /**
     * POS I/F内処理結果OK時の値です.
     * 
     */
    public static final String STATUS_OK = "00";

    /**
     * 処理結果エラー時の値です.
     * 
     */
    public static final String STATUS_NG = "01";
    
    /**
     * POS I/F内処理結果NG時の値です.
     * 
     */
    public static final String STATUSCODE_NG = "-1";
    
    /**
     * POS I/F内処理結果OK時の値です.
     * 
     */
    public static final int POSIF_OK = 0;

    /**
     * POS I/F内処理結果NG時の値です.
     * 
     */
    public static final int POSIF_NG = -1;
    
    /**
     * Request情報のMap用キーX_STATUSのString型定数です.
     * 
     */
    public static final String KEY_X_STATUS = "X_STATUS";


    /**
     * POSIF内定数を宣言したクラスはインスタンス化できません.
     */
    private PosifConstants() {

    }

}
