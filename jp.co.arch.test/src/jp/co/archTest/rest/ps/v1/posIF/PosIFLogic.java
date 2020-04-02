/**
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20190115 FanMH(bbasoft)    G001.00.0 POSIF処理結果を確認します。
 * 20190327 Sunwc(bbasoft)    G002.00.0 仮想POS4 は『強制精算』が実行されない対応
 */
package jp.co.archTest.rest.ps.v1.posIF;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import rocky.base.common.transfer.impl.Response;

public class PosIFLogic {

    /**
     * HTTPコマンドNo 
     * HOLDリセット8101.
     */
    public static final String COMMAND_8101 = "8101";

    /**
     * HTTPコマンドNo 
     * 精算通知8119.
     */
    public static final String COMMAND_8119 = "8119";

    // G001.00.0 Add-Start POSIF処理結果を確認します。
	private static final Logger logger = LoggerFactory.getLogger(PosIFLogic.class);
	// G001.00.0 Add-End POSIF処理結果を確認します。

    /**
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
	public static ResultContext posIfRequest(Integer stroeCode, Integer regiNo, String hostName, String commandNo)
			throws NoSuchBeanDefinitionException {
		HttpDriver httpDriver = new HttpDriver();
		Map<String, String> in = new HashMap<>();
		// 店舗コード999999
		in.put(Response.X_STRORE_CODE, String.format("%06d", stroeCode));
		// HTTPコマンドNo 
		in.put(Response.X_COMMAND, commandNo);
		// レジNo
		in.put(Response.X_REGNO, regiNo.toString());
		
		// G001.00.0 Mainte-Start POSIF処理結果を確認します。
		ResultContext result = new ResultContext();
        //結果Status
        String status = PosifConstants.STATUS_NG;
        //結果StatusCode
        String statusCode = PosifConstants.STATUSCODE_NG;

		try {
			// HTTP送信します.
			Response res = httpDriver.send(hostName, in, 30000, String.format("%06d", stroeCode));

			//結果の確認
			// G002.00.0 Mainte-Start 仮想POS4 は『強制精算』が実行されない対応
            if (res.getParameter(Response.STATUS) == null || ( String ) res.getParameter(Response.STATUS) == "N") {
            	// 
                status = PosifConstants.STATUS_NG;
                //STATUSがNの場合にはHTTP通信レベルでのエラーのためSTATUSCODEを取得
				if (res.getParameter(Response.STATUSCODE) != null
						&& !res.getParameter(Response.STATUSCODE).equals("")) {
					statusCode = (String) res.getParameter(Response.STATUSCODE);
				}    
            // G002.00.0 Mainte-End 仮想POS4 は『強制精算』が実行されない対応
            } else {
                //HTTPレベルの通信は正常の場合（statusがNでない）
                //X-Statusの取得
                status
                    = ( String ) res.getParameter(PosifConstants.KEY_X_STATUS);
                if (Integer.valueOf(status) == PosifConstants.POSIF_OK) {
                    // 結果の格納（00）
                    status = PosifConstants.STATUS_OK;
                    statusCode = String.valueOf(PosifConstants.POSIF_OK);
                } else if (Integer.valueOf(status) == PosifConstants.POSIF_NG) {
                    // 結果の格納（01）
                    status = PosifConstants.STATUS_NG;
                }
            }
		} catch (Exception e) {
            logger.error("POSIF送信でエラー発生しました. ", e);
		} finally {
            // 結果の格納
			result.setStatus(status);
			result.setStatusCode(statusCode);
        }
		
		return result;
        // G001.00.0 Mainte-End
	}
}
