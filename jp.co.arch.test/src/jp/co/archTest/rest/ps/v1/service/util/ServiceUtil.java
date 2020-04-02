/*
 * --------+-------------------+---------+---------------
 *  DATE   |NAME(Inc)          |GUIDE    |GUIDANCE
 * --------+-------------------+---------+---------------
 * 20200326 suxl(bbasoft)      G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.v1.service.util;

import jp.co.archTest.rest.ps.common.CommonException;

/**
 * ServiceUtil.<br>
 * 
 * @author suxl(bbasoft)
 */
public class ServiceUtil {

	/**
	 * httpヘッダから認証tokenを取り出す
	 * 
	 * @param authHeaders
	 *            token
	 * @return 認証token
	 */
	public static String getToken(String authHeaders) {
		
		//認証ヘッダをパースしトークンを取り出す
	    if(authHeaders == null || authHeaders.length() == 0) {
	    	throw new CommonException(1,"認証ヘッダなし");
	    }

	    String[] parse = authHeaders.split(" ",0);
	    if(parse.length != 2 || !parse[0].equals("Bearer")) {
	    	throw new CommonException(2, "認証ヘッダにBearerトークンなし");
	    }
	    String token = parse[1];

		return token;
	}
	
	
}
