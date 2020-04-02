/**
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20200324 suxl(bbasoft)     G001.00.0　新規
 */
package jp.co.archTest.rest.ps.v1.api;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.istack.internal.FinalArrayList;

import jp.co.archTest.rest.ps.common.ErrorMessage;
import jp.co.archTest.rest.ps.common.ResponsData;
import jp.co.archTest.rest.ps.v1.bean.RequestInfo;
import jp.co.archTest.rest.ps.v1.bean.ResponseInfo;
import jp.co.archTest.rest.ps.v1.requertParam.MultiplyInfoRequest;
import jp.co.archTest.rest.ps.v1.service.OcxService;
import jp.co.archTest.rest.ps.v1.service.util.ServiceUtil;

@Path("/v1/ocx")
public class OcxApi extends BaseContrl {

	private static final Logger logger = LoggerFactory.getLogger(OcxApi.class);

	@Autowired
	private OcxService ocxService;

	/**
	 * OCXコントロールの操作（マルチプライヤ）
	 *
	 * @return 処理結果
	 */
	@GET
	@Path("/ocx-multiply")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponsData ocxMultiply1(@QueryParam("data1") int data1, @QueryParam("data2") int data2) {
		logger.debug("OCXコントロールの操作（マルチプライヤ）");
		logger.debug("@param データ１ " + data1);
		logger.debug("@param データ２ " + data2);
		ResponsData resData = new ResponsData();
		try {
			resData.setSuccess(ocxService.getCoxMultiply(data1, data2));
			resData.setResult(true);
		} catch (Exception pex) {
			logger.error("その他エラー（想定外エラー）");
			logger.error(pex.getMessage(), pex);
			resData.setResult(false);
			resData.setFailure(new ErrorMessage(9999, "その他エラー（想定外エラー）"));
		}
		return resData;
	}

	/**
	 * OCXコントロールの操作（マルチプライヤ）
	 * 
	 * @return 処理結果
	 */
	@POST
	@Path("/ocx-multiply1")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponsData ocxMultiply1(@HeaderParam(HttpHeaders.AUTHORIZATION) String token,
			final MultiplyInfoRequest body) {
		logger.debug("OCXコントロールの操作（マルチプライヤ）");
		logger.debug("@param アクセストークン " + token);
		logger.debug("@param データ１ " + body.getMultiplyA());
		logger.debug("@param データ２ " + body.getMultiplyB());
		ResponsData resData = new ResponsData();
		try {
			resData.setSuccess(ocxService.getCoxMultiply1(ServiceUtil.getToken(token),
					body.getMultiplyA(), body.getMultiplyB()));
			resData.setResult(true);
		} catch (Exception pex) {
			logger.error("その他エラー（想定外エラー）");
			logger.error(pex.getMessage(), pex);
			resData.setResult(false);
			resData.setFailure(new ErrorMessage(9999, "その他エラー（想定外エラー）"));
		}
		return resData;
	}
	
	/**
	 * OCXコントロールの操作（arch端末）
	 * 
	 * @return 処理結果
	 */
	@GET
	@Path("/ocx-healthcheck")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponsData HealthCheck() {
		logger.debug("OCXコントロールの操作（arch端末）");
//		logger.debug("@param アクセストークン " + token);
//		logger.debug("@param データ１ " + body.getMultiplyA());
//		logger.debug("@param データ２ " + body.getMultiplyB());
		ResponsData resData = new ResponsData();
		try {
			resData.setSuccess(ocxService.getHealthCheck());
			resData.setResult(true);
		} catch (Exception pex) {
			logger.error("その他エラー（想定外エラー）");
			logger.error(pex.getMessage(), pex);
			resData.setResult(false);
			resData.setFailure(new ErrorMessage(9999, "その他エラー（想定外エラー）"));
		}
		return resData;
	}
}
