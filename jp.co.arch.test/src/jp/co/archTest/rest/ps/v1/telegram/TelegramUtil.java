/**
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20180523 GuanLi(bbasoft)  G101.00.0 標準版対応
 * 
 */
package jp.co.archTest.rest.ps.v1.telegram;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.archTest.rest.ps.common.CommonException;
import jp.co.archTest.rest.ps.v1.telegram.base.Header;
import jp.co.archTest.rest.ps.v1.telegram.response.ResponseData;
import jp.co.archTest.rest.ps.v1.telegram.response.TelegramResponse;

public class TelegramUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(TelegramUtil.class);

	public static void main(String[] avgs) throws Exception {
			//試験使い		
	}

	/**
	 * 汎用操作
	 * 
	 * @param paraInfo　パラメータ情報
	 * @return xmlTelegram　
	 * @throws Exception
	 */
	public static TelegramResponse posCmnOperation(/*ParameterInfo paraInfo*/) throws Exception {

//		String strSend = CMD1000011ReqData.CreateTelegram(paraInfo);
//				
//		logger.debug("IP=" + paraInfo.getServerIP() + ":" + paraInfo.getPort());
//		logger.debug("SEND=" + strSend);
//		SocketClient sc = new SocketClient(paraInfo.getServerIP(), paraInfo.getPort());
//	
//		String strReceive = sc.SendAndRecieve(strSend);
//		logger.debug("RECEIVE=" + strReceive);
//
//		TelegramResponse res = parse(strReceive);
//		return res;
		return null;
	}
	
	private static TelegramResponse parse(String strXml) throws DocumentException {
		if (strXml == null || strXml.length() == 0) {
			throw new CommonException(999, "XMLエラー");
		}

		TelegramResponse resp = new TelegramResponse();

		Document document = DocumentHelper.parseText(strXml);
		Element root = document.getRootElement();
		String rootName = root.getName();

		if (!"PosMessageResponse".equals(rootName)) {
			throw new CommonException(999, "XMLパースエラー");
		}

		Iterator<Element> iterator = root.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			String nodeName = e.getName();
			if ("header".equals(nodeName)) {
				Header h = new Header();
				h.parse(e);
				resp.setHeader(h);
			} else if ("data".equals(nodeName)) {
				ResponseData data = new ResponseData();
				data.parse(e);
				resp.setData(data);
			}
		}
		return resp;
	}	
}
