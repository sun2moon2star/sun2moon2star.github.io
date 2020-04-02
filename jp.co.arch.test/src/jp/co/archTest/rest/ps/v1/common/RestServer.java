/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 *  
 */
package jp.co.archTest.rest.ps.v1.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

//http://etc9.hatenablog.com/entry/20100204/1265528529から丸パクリしたクラス
public class RestServer {

    
    /**
	 * 实现对REST服务的请求
	 * 
	 * @param urlStr
	 * @param urlParam
	 * @return
	 * @throws java.io.IOException
	 */
	public static String sendGet(String urlStr, Map<Object, Object> urlParam) throws IOException {
		if (!urlParam.isEmpty()) {
			urlStr += "?";
			// 定义一个迭代器，并将MAP值的集合赋值
			for (Map.Entry<Object, Object> map : urlParam.entrySet()) {
				urlStr += map.getKey() + "=" + map.getValue() + "&";
			}
			urlStr = urlStr.substring(0, urlStr.length() - 1);
		}
		// 实例一个URL资源
		URL url = new URL(urlStr);
		// 实例一个HTTP CONNECT
		HttpURLConnection connet = (HttpURLConnection) url.openConnection();
		connet.setRequestMethod("GET");
		connet.setRequestProperty("Charset", "UTF-8");
		connet.setRequestProperty("Content-Type", "application/json");
		connet.setConnectTimeout(60*1000);// 连接超时 单位毫秒
		connet.setReadTimeout(60*1000);// 读取超时 单位毫秒
		if (connet.getResponseCode() != 200) {
			System.out.println("请求异常" + urlStr);
			return "";
		}
		// 将返回的值存入到String中
		InputStreamReader in=new InputStreamReader(connet.getInputStream(),"UTF-8");
		BufferedReader brd = new BufferedReader(in);
		StringBuffer sb = new StringBuffer();
		String line;

		while ((line = brd.readLine()) != null) {
			System.out.println(line);
			sb.append(line);
		}
		brd.close();
		connet.disconnect();
		return sb.toString();
	}
    
    /**
	 * 指定されたURLにPOSTメソッドを送信する要求
	 * 
	 * @param url 要求されたURLを送る
	 * @param param パラメータを要求する
	 * @return 応答結果
	 */
	public static String sendPost(String url, String param) {
		
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		String line;
		StringBuffer sb = new StringBuffer();
		
		try {
			URL realUrl = new URL(url);
			
			// URLとの接続を開く(打开和URL之间的连接)
			URLConnection conn = realUrl.openConnection();
			
			// ここで、共通する要求属性設定要求フォーマットが設定される(设置通用的请求属性 设置请求格式)
			conn.setRequestProperty("contentType", "utf-8");
			conn.setRequestProperty("content-type", "application/json");
			
			// タイムアウト時間を設ける(设置超时时间) ６０秒
			conn.setConnectTimeout(60*1000);
			conn.setReadTimeout(60*1000);
			
			// POSTリクエストの送信には、以下の2行を設定する必要がある(发送POST请求必须设置如下两行)
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			// URLConnectionオブジェクトに対応する出力ストリームを取得する(获取URLConnection对象对应的输出流)
			out = new PrintWriter(conn.getOutputStream());
			
			// 要求パラメータの送信(发送请求参数)			
			out.print(param);
			
			// flush出力ストリームのバッファ(flush输出流的缓冲)
			out.flush();	
			
			// BufferedReader入力ストリームを定義してURLを読み出す応答を受信フォーマットに設定する
			// 定义BufferedReader输入流来读取URL的响应 设置接收格式
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
		} catch (Exception e) {
			System.out.println("POSの送信に異常が生じる!" + e);
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
    
}
