package jp.co.archTest.rest.ps;

import javax.ws.rs.ApplicationPath;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import jp.co.archTest.rest.ps.common.DateConfigurator;
import jp.co.archTest.rest.ps.common.PreRequestFilter;
import jp.co.archTest.rest.ps.common.ResponseFilter;

//@ApplicationPath("/apiL")
public class WebApi extends ResourceConfig {

	static protected Logger logger = Logger.getLogger(WebApi.class);
	
	public WebApi() {
		logger.info("■初期処理開始");

		// APIエンドポイント登録
		packages(this.getClass().getPackage().getName() + ".v1");
		logger.info("APIエンドポイント登録完了");

		// JSONプロバイダ登録
		register(DateConfigurator.class);
		register(JacksonJsonProvider.class);
		register(PreRequestFilter.class);
		register(ResponseFilter.class);
		register(MultiPartFeature.class);  
		logger.info("JSONプロバイダ登録登録完了");

		logger.info("■初期処理終了");
	}
}
