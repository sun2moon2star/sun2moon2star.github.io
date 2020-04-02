/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20180105 FanMH(bbasoft)    G001.00.0 新規作成
 * 20181106 FanMH(bbasoft)    G002.00.0 結合課題No242対応
 */
package jp.co.archTest.rest.ps.common.csv;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.archTest.rest.ps.common.CommonException;
import jp.co.archTest.rest.ps.common.TECException;


/**
 * CSVファイルの行単位を読込み.
 *
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 *
 * @author yang_JSE
 */
public class CSVFileUtility {

	private static final Logger logger = LoggerFactory.getLogger(CSVFileUtility.class);

    /**
     *CSVファイルの行単位を読込み.
     *単品型ファイルの複数物理行は一論理行の読込み対応。
     *最後文字はカンマの場合は、論理行として認識、次行も読込みする。
     *
     * @param bufferedReader バッファ
     * @return CSVファイルの一行（最後はnull)
     * @throws TECException 例外
     */
    public String readCSVFileLineData(
            BufferedReader bufferedReader) {
        return this.readCSVFileLineData(bufferedReader,  0, false);
    }

    public String readCSVFileLineData(
        BufferedReader bufferedReader,int intCheckVersion) {
        return this.readCSVFileLineData(bufferedReader, intCheckVersion, false);
    }

    public String readCSVFileLineData(
            BufferedReader bufferedReader,int intCheckVersion, boolean conv) {
        String csvLineData = null;
        String csvNextLineData = null;

        Thread.yield();

        try {
            while(true) {
                csvLineData = bufferedReader.readLine();
                if (csvLineData == null || csvLineData.length() > 0){
                    break;
                }
            }
            if (csvLineData != null) {
                while (csvLineData.endsWith(",")) {
                    csvNextLineData  = bufferedReader.readLine();
                    csvLineData =  csvLineData + csvNextLineData;
                }
            }
        } catch (IOException e) {
        }

        if (csvLineData != null && conv){
            csvLineData = convQuotationToBlank(csvLineData);
        }

        return csvLineData;
    }

    /**
     *CSVファイルの行単位を読込み.
     *
     * @param bufferedReader バッファ
     * @param params
     *            XMLオブジェクト
     * @param intCheckVersion
     *            フォーマットバージョン
     * @param conv
     *            クォーテーション文字変換
     * @return CSVファイルの一行
     * @throws TECException 例外
     */
    public String readCSVFileSingleLineData(BufferedReader bufferedReader,
         int intCheckVersion, boolean conv)
         {
        String csvLineData = null;
        String csvConvLineData = null;

        Thread.yield();

        try {
            while (true) {
                csvLineData = bufferedReader.readLine();
                if (csvLineData == null || csvLineData.length() > 0) {
                    break;
                }
            }
        } catch (IOException e) {

        }

        if (csvLineData != null && conv) {
            csvLineData = convQuotationToBlank(csvLineData);
        }

        return csvConvLineData;
    }



    /**
     * クォーテーション文字変換.
     *
     * 文字列内のシングルクォーテーション(')、ダブルクォーテーション(")を半角ブランクに変換します。
     *
     * @param str
     *            文字列
     * @return 変換後の文字列
     */
    private String convQuotationToBlank(String str) {

        if (str == null || "".equals(str)) {
            return str;
        }
        return str.replace("'", " ").replace("\"", " ");
    }


    private String ip = "";
    
    // G002.00.0 Mainte-Start 結合課題No242対応
    /**
     * ネットワーク越しにファイルアクセスする.
     *
     * @param ip
     */
	public Process netUse(String ip, String userId, String password, String sleeptime) {
		this.ip = ip;
		logger.debug("【user】" + userId + " ／【password】" + password);

    	Process process1 = null;
    	try {
    		String netuse = "net use \\\\" + ip + "\\ipc$ \"" + password + "\" /user:\"" + userId + "\"";

    		logger.debug("【net useコマンド】" + netuse);

    		process1 = Runtime.getRuntime().exec("cmd.exe /c "+netuse);
    		
    		// デフォルト１秒
    		int millis = 1000;
    		if (sleeptime != null && !sleeptime.equals("")) {
    			millis = Integer.parseInt(sleeptime);
    		}
    		try {
    			Thread.sleep(millis);
    		} catch (Exception e) {
    			logger.error("", e);
    		}
    	} catch (IOException e) {
    		logger.error("", e);
    		throw new CommonException(9999, "その他エラー（想定外エラー）");
    	}
    	return process1;
    }
	// G002.00.0 Mainte-End

	/**
     * ネットワーク越しにファイルアクセスする.
     *
     */
	public Process delNetUse() {
    	Process process1 = null;
    	try {
    		String netuse = "net use \\\\" + ip + "\\ipc$ " + "/del";
    		process1 = Runtime.getRuntime().exec("cmd.exe /c "+netuse);
    	} catch (IOException e) {
    		logger.error("", e);
    		throw new CommonException(9999, "その他エラー（想定外エラー）");
    	}
    	return process1;
    }

}