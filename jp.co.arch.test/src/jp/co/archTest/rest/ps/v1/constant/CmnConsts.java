/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20180608 ***(bbasoft)    G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.v1.constant;

public class CmnConsts {

	/****************端末種別-START********************/
    /*****0:カードＰＯＳ、1:カートモニタ、2:ハンドPOS、3：会計機*****/
	/*************************************************/
	// カードＰＯＳ
	public static final int KART_POS = 0;
	// カートモニタ
	public static final int KART_MOINTER = 1;
	// ハンドPOS
	public static final int HANLD_POS = 2;
	// 会計機
	public static final int ACCOUNTER = 3;
	/******************端末種別-END********************/
	
	
	/****************端末状態-START*******************/
    /*****0:なし,1:待機,2:登録中,3:会計中*****************/
	/*************************************************/
	// なし
	public static final int TM_STS_NO = 0;
	// 待機
	public static final int TM_STS_WAITE = 1;
	// 登録中
	public static final int TM_STS_DOING = 2;
	// 会計中
	public static final int TM_STS_ACCOUNT = 3;
	/****************端末状態-END********************/

	
	/****************取引ステータス-START*****************/
    /*********0:正常、1:完了(閉め)、2：取消(一括取消)********/
	/*************************************************/
	// 正常
	public static final int DEAL_STS_OK = 0;
	// 完了(閉め)
	public static final int DEAL_STS_CLOSE = 1;
	// 取消(一括取消)
	public static final int DEAL_STS_CANCEL = 2;
	/****************取引ステータス-END********************/

	/****************トレーニングモード-START****************/
    /*********0：通常モード    1：トレーニングモード  ***************/
	/*************************************************/
	// 通常モード
	public static final String TRAINING_MODE_NORM = "0";
	// トレーニングモード
	public static final String TRAINING_MODE_TRAINING = "1";
	/****************トレーニングモード-END********************/
	
	/*************仮想POSサインオン／サインオフ-START*********/
    /**************0：サインオフ    1：サインオン ****************/
	/*************************************************/
	// 0：サインオフ
	public static final int POS_SINOFF = 0;
	// 1：サインオン
	public static final int POS_SINON = 1;
	/****************トレーニングモード-END********************/
	
	/*************販促情報区分-START*********/
    /**************0：ベース    1：リンク ****************/
	/*************************************************/
	// 0：ベース
	public static final int DISTINGUISH_BASE = 0;
	// 1：リンク 
	public static final int DISTINGUISH_LINK = 1;
	/****************販促情報区分-END********************/
	
	/*************ログインSTS-START*********************/
    /**************0：ログオフ    1：ログイン ******************/
	/*************************************************/
	// 0：ログオフ  
	public static final int LOGIN_STS_OFF = 0;
	// 1：ログイン
	public static final int LOGIN_STS_ON = 1;
	/****************販促情報区分-END********************/
	
	/*************会計タイプSTS-START*********************/
    /****0：データ転送タイプ／1：会計券タイプ******/
	/*************************************************/
	// voucher：会計券タイプ
		public static final String ACCOUNTING_TYPE_VOUCHER = "0";
	// transfer：データ転送タイプ
	public static final String ACCOUNTING_TYPE_TRANSFER = "1";	
	/****************販促情報区分-END********************/
	
	/*************商品属性タイプ-START*********************/
    /****０:GP/部門/ｸﾗｽ/商品(POS)ｺｰﾄﾞ １:GP/部門/商品(POS)ｺｰﾄﾞ ２:部門/商品(POS)ｺｰﾄﾞ ３:ＧＰ/クラス/商品(ＰＯＳ)ｺｰﾄﾞ  ４：部門/クラス/商品（POS）コード******/
	/*************************************************/
	// ０:GP/部門/ｸﾗｽ/商品(POS)ｺｰﾄﾞ
	public static final String GOODS_ATTRIBUTE_TYPE_0 = "0";
	// １:GP/部門/商品(POS)ｺｰﾄﾞ 
	public static final String GOODS_ATTRIBUTE_TYPE_1 = "1";
	 // ２:部門/商品(POS)ｺｰﾄﾞ ﾞ 
	public static final String GOODS_ATTRIBUTE_TYPE_2 = "2";
	// １:GP/部門/商品(POS)ｺｰﾄﾞ 
	public static final String GOODS_ATTRIBUTE_TYPE_3 = "3";
	// １:GP/部門/商品(POS)ｺｰﾄﾞ 
	public static final String GOODS_ATTRIBUTE_TYPE_4 = "4";
	/****************販促情報区分-END********************/

	/*************20禁Popupタイプ追加-START*********************/
    /**** 0：取引別１回だけ表示／1：20禁商品で毎回表示    ******/
	/********************************************************/
	// ０:GP/部門/ｸﾗｽ/商品(POS)ｺｰﾄﾞ
	public static final String ADULT_POPUP_TYPE_ONCE = "0";
	// １:GP/部門/商品(POS)ｺｰﾄﾞ 
	public static final String ADULT_POPUP_TYPE_ALWAYS = "1";
	/****************20禁Popupタイプ追加-END********************/
	
	/****************端末ID-START********************/
	// 客層専用
	public static final String TERMINAL_ID = "000";
	/****************端末ID-END********************/
	
	/****************処理タイプ-START********************/
	// 5: バーコード不正の処理タイプ
	public static final Integer BARCODE_PROCTYPE = 5;
	// 6:商品登録エラーの処理タイプ
	public static final Integer ERRCODE_PROCTYPE = 6;
	/****************処理タイプ-END********************/
	
	/****************強制HOLD解除フラグ-START********************/
	// 0:許可
	public static final String VPOS_HOLD_FLAG_0 = "0";
	// 1:禁止
	public static final String VPOS_HOLD_FLAG_1 = "1";
	/****************強制HOLD解除フラグ-END********************/
}
