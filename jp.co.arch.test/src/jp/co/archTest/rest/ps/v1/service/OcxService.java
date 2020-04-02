/**
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |　GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 220200324 suxl(bbasoft)     G001.00.0　新規
 */
package jp.co.archTest.rest.ps.v1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import jp.co.archTest.rest.ps.common.CommonException;
import jp.co.archTest.rest.ps.v1.bean.RequestInfo;
import jp.co.archTest.rest.ps.v1.bean.ResponseInfo;
import jp.co.archTest.rest.ps.v1.dao.TbTerminalSessionCtrlDao;
import jp.co.archTest.rest.ps.v1.entity.TbTerminalSessionCtrl;

/**
 * OCXコントロールの操作
 * 
 */
@Service
public class OcxService {

	private static final Logger logger = LoggerFactory.getLogger(OcxService.class);

	@Autowired
	private TbTerminalSessionCtrlDao terminalSessionCtrlDao;

	/**
	 * OCXコントロールのマルチプライヤ
	 * 
	 * @param data1
	 *            マルチプライヤのデータ1
	 * @param data2
	 *            マルチプライヤのデータ2
	 * @return
	 */
	public String getCoxMultiply(int data1, int data2) {

		String resultData = "";

		try {

			// 初期化
			ComThread.InitMTA(true);

			/**
			 * ProgIDからOCXコントロールを呼び出すと、コンポーネントのProgIDがレジストリのMFCActiveXControl1.
			 * ocx登録済みProgID値に対応し、 レジストリを開いてocxファイル名を検索して対応する値を見つけることができます。
			 * 
			 * HKEY_CLASSES_ROOT\Wow6432Node\CLSID\{9EDD1618-2C53-492F-B20F-
			 * AB3BF2410F08} ActiveXComponent com = new
			 * ActiveXComponent("コンポーネントのProgID") ;
			 */
			ActiveXComponent com = new ActiveXComponent("CLSID:535CE73B-482F-42B6-93AA-AD746181F1D0");

			// CLSIDでOCXコントロールを呼び出します。
			Dispatch disp = (Dispatch) com.getObject();

			/**
			 * Dispatch.call(CLSIDコールOCXコントロール、OCXコントロールのメソッド名、パラメータ【new
			 * Variant(*)】
			 */
			Integer result = Dispatch.call(disp, "Multiply", new Variant(data1), new Variant(data2)).getInt();
			logger.error("ocxに通って、お返事の値は： " + result);

			resultData = result.toString();
		} catch (Exception e) {
			// エラー発生
			e.printStackTrace();
		} finally {
			// リリーススレッド
			ComThread.Release();
		}

		return resultData;
	}

	/**
	 * OCXコントロールのマルチプライヤ
	 * 
	 * @param multiplyInfo
	 *            マルチプライヤのデータ
	 * @return
	 */
	public String getCoxMultiply1(String token, int data1, int data2) {

		String resultData = "";

		try {

			// トークン験証 NzBiNGExYjEtNjcyNC00ZTNlLWIyNmUtNWRmNmUzNjI0Zjll
			TbTerminalSessionCtrl session = terminalSessionCtrlDao.findOne(token);

			// トークンチェックがNGの場合
			if (session == null) {
				throw new CommonException(1001, "トークンチェック失敗します");
			}

			// 初期化
			ComThread.InitMTA(true);

			/**
			 * ProgIDからOCXコントロールを呼び出すと、コンポーネントのProgIDがレジストリのMFCActiveXControl1.
			 * ocx登録済みProgID値に対応し、 レジストリを開いてocxファイル名を検索して対応する値を見つけることができます。
			 * 
			 * HKEY_CLASSES_ROOT\Wow6432Node\CLSID\{9EDD1618-2C53-492F-B20F-
			 * AB3BF2410F08} ActiveXComponent com = new
			 * ActiveXComponent("コンポーネントのProgID") ;
			 */
			ActiveXComponent com = new ActiveXComponent("CLSID:535CE73B-482F-42B6-93AA-AD746181F1D0");

			// CLSIDでOCXコントロールを呼び出します。
			Dispatch disp = (Dispatch) com.getObject();

			/**
			 * Dispatch.call(CLSIDコールOCXコントロール、OCXコントロールのメソッド名、パラメータ【new
			 * Variant(*)】
			 */
			Variant[] var = new Variant[2];
			var[0] = new Variant(data1, true);
			var[1] = new Variant(data2, true);
			Integer result = Dispatch.call(disp, "Multiply2", var).getInt();

			logger.error("ocxに通って、お返事の値は： " + result);

			resultData = result.toString();
		} catch (Exception e) {
			// エラー発生
			e.printStackTrace();
		} finally {
			// リリーススレッド
			ComThread.Release();
		}

		return resultData;
	}

	/**
	 * OCX_arch端末
	 * 
	 * @return
	 */
	public String getHealthCheck() {

		String resultData = "";

		try {

			// // トークン験証 NzBiNGExYjEtNjcyNC00ZTNlLWIyNmUtNWRmNmUzNjI0Zjll
			// TbTerminalSessionCtrl session =
			// terminalSessionCtrlDao.findOne(token);
			//
			// // トークンチェックがNGの場合
			// if (session == null) {
			// throw new CommonException(1001, "トークンチェック失敗します");
			// }

			// 初期化
			ComThread.InitMTA(true);

			/**
			 * ProgIDからOCXコントロールを呼び出すと、コンポーネントのProgIDがレジストリのMFCActiveXControl1.
			 * ocx登録済みProgID値に対応し、 レジストリを開いてocxファイル名を検索して対応する値を見つけることができます。
			 * 
			 * HKEY_CLASSES_ROOT\Wow6432Node\CLSID\{9EDD1618-2C53-492F-B20F-
			 * AB3BF2410F08} ActiveXComponent com = new
			 * ActiveXComponent("コンポーネントのProgID") ;
			 */
			ActiveXComponent com = new ActiveXComponent("CLSID:535CE73B-482F-42B6-93AA-AD746181F1D0");

			// CLSIDでOCXコントロールを呼び出します。
			Dispatch disp = (Dispatch) com.getObject();

			/**
			 * Dispatch.call(CLSIDコールOCXコントロール、OCXコントロールのメソッド名、パラメータ【new
			 * Variant(*)】
			 */

			// Variant[] varReq = new Variant[2];
			// varReq[0] = new Variant(1, true);//mode
			// varReq[1] = new Variant(0, true);//printMode
			//
			 Variant varResponse = new Variant();
			//
			// Variant[] varRsp = new Variant[2];
			// varRsp[0] = new Variant(1, true);//mode
			// varRsp[1] = new Variant(0, true);//printMode

			// request
			Variant[] varReq = new Variant[2];
			varReq[0] = new Variant(1, true);// mode
			varReq[1] = new Variant(0, true);// printMode
			
			
			// response0
			// 制御情報
			Variant varControlInfoNormal = new Variant(35);
			//varControlInfoNormal.put("313121c.21.21.21");
//			varControlInfoNormal.putInt(1); 	// mode
//			varControlInfoNormal.putInt(1);		// service
//			varControlInfoNormal.putInt(1);		// biz
//			varControlInfoNormal.putInt(1);		// procResult
//			varControlInfoNormal.putLong(1);	// outputId
//			varControlInfoNormal.putLong(1);	// termIdentityNo
//			//varControlInfoNormal.putString("");	// dateTime
//			
//			// 業務情報
//			Variant varBizInfoStruct = new Variant();
//			varBizInfoStruct.putInt(1);    		// posWaitingStatus
//			
//			// 
//			Variant[] varHealthCheckNormalResponse = new Variant[2];
//			//varHealthCheckNormalResponse.putVariantArray(in);
//			varHealthCheckNormalResponse[0] = varControlInfoNormal;
//			varHealthCheckNormalResponse[1] = varBizInfoStruct;
			//Integer result = Dispatch.call(disp, "HealthCheck", varReq).getInt();
			

			Variant[] var = new Variant[2];
			var[0] = new Variant(2, true);
			var[1] = new Variant(5, true);
			Integer result = Dispatch.call(disp, "Multiply", var).getInt();
			
			
			// response1
//			Variant varRsp = new Variant("", true);
//			Integer result = Dispatch.call(disp, "HealthCheck", varReq, varRsp).getInt();
//			System.out.println("返回数据为：" + varRsp.toString() + "++++" + result);

			// response2
			// Dispatch deviceInfo = new Dispatch();
			// result = Dispatch.call(disp, "HealthCheck", varReq,
			// deviceInfo).getInt();
			// System.out.println("返回数据为：" + deviceInfo.toString() + "++++" +
			// result);

			// response3
//			byte[] b = new byte[2048];
//			 //其实主要目的只是为了初始化String的长度，才能接收到相应个数的字符串
//			 Variant v1 = new Variant (new String(b, "UNICODE"), true) ;
//			 Integer result = Dispatch.call(disp, "HealthCheck", varReq, v1).getInt();
//			 String ret = new String(v1.toString().getBytes("UNICODE"));
//			 System.out.println("返回数据为：" + ret.toString() + "++++" + result);
//			

			// jclass objectClass = (env)->GetObjectClass(obj);
			// //obj就是FileInfo类的实际对象
			// jfieldID fs = (env)->GetFieldID(objectClass,"filesize","I");
			// //可以获取到int型的filesize这个属性了
			// jfieldID ss = (env)->GetFieldID(objectClass,"ss","[I");
			// //这样是不是可以获取到int型的数组ss了？

			// ResponseInfo responseInfo = new ResponseInfo();
			// var[1] = new Variant(responseInfo, true);
			// Integer result = Dispatch.call(disp, "HealthCheck",
			// var).getInt();
			// Integer result = Dispatch.call(disp, "HealthCheck", varReq,
			// varRsp).getInt();

			logger.error("ocxに通って、お返事の値は： " + result);

			resultData = result.toString();
		} catch (Exception e) {
			// エラー発生
			e.printStackTrace();
		} finally {
			// リリーススレッド
			ComThread.Release();
		}

		return resultData;
	}
}
