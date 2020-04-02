/**
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20180904 ***(bbasoft)    G001.00.0 
 * 
 */
package jp.co.archTest.rest.ps.v1.listener;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import jp.co.archTest.rest.ps.WebApi;

public class InitDataListener implements InitializingBean, ServletContextAware {

	static protected Logger logger = Logger.getLogger(WebApi.class);

	@Override
	public void setServletContext(ServletContext arg0) {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//モバコン起動時、処理操作
		
	}
}
