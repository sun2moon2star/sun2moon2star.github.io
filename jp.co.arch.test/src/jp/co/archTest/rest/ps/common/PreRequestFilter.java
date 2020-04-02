/**
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20200325 suxl(bbasoft)     G001.00.0 新規作成.
 */
package jp.co.archTest.rest.ps.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreRequestFilter implements ContainerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(PreRequestFilter.class);

	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {

		if(containerRequestContext.getMethod().equals("OPTIONS")){
			return;
		}
		
		// RESTAPI入力時：メソッド名、要求元端末ID、要求元端末名称、引数(JSON or XML文字列)ログ出力追加.
		InputStream input = containerRequestContext.getEntityStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len;
		while ((len = input.read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		
		// 削除しなら、メソッドのオブジェクトパラメータが見つけできません
		InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
		containerRequestContext.setEntityStream(stream1);
		InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
		String requestStr = this.inputStreamToString(stream2);
		
		baos.close();
		
		UriInfo url = containerRequestContext.getUriInfo();
		String paths = url.getPath();
		if (!paths.equals("v1/client/log")) {
			logger.debug("★APIリクエスト受信　URL：" + containerRequestContext.getUriInfo().getRequestUri() + ",引数：" + requestStr);
		}		
	}
	
	/**
	 * InputStream を　Stringへ変換します.
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public String inputStreamToString(final InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	
}