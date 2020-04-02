/**
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20180205 FanMH(bbasoft)    G001.00.0 新規作成(RESTAPI入力時：メソッド名、要求元端末ID、要求元端末名称、引数(JSON or XML文字列)ログ出力追加).
 * 20180329 Kobayashi(TEC)    G002.00.0 ログ出力改善
 * 20180827 Li_guilin(bbasoft) G003.00.0 出力ログに識別を追加する
 * 20180903 Li_guilin(bbasoft) G004.00.0 出力ログにキーを追加する
 * 20180904 Li_guilin(bbasoft) G005.00.0 No.153 ログ出力対応
 */
package jp.co.archTest.rest.ps.common;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class ResponseFilter implements ContainerResponseFilter {

	private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

	@Override
	public void filter(ContainerRequestContext containerRequestContext,
			ContainerResponseContext containerResponseContext) throws IOException {
		containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		// G004.00.0  Mainte-Start
        containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, RequestTime ,accept, authorization");
        // G004.00.0  Mainte-End
        containerResponseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        containerResponseContext.getHeaders().add("Access-Control-Max-Age", "1209600");

		if(containerRequestContext.getMethod().equals("OPTIONS")){
			return;
		}

		// G003.00.0  Mainte-Start
		// G005.00.0  Mainte-Start G005.00.0 No.153 ログ出力対応
		Gson gson = new Gson();
		String str = null;
		UriInfo url = null;
		String paths = null;
		try {
			str = gson.toJson(containerResponseContext.getEntity());
			url = containerRequestContext.getUriInfo();
			paths = url.getPath();
		}catch(Exception ex){
			logger.error("レスポンスJSONシリアライズでエラー");
			ex.printStackTrace();
		}

		if (!paths.equals("v1/client/log")) {
			logger.debug("☆APIレスポンス送信"  + "[normal]" + "返却値：" + str);
		}
		// G005.00.0  Mainte-End G005.00.0 No.153 ログ出力対応
	}
}