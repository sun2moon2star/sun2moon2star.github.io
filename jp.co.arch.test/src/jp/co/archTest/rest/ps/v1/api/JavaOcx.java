package jp.co.archTest.rest.ps.v1.api;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import com4j.Com4jObject;
import com4j.EventCookie;
import com4j.Holder;
import saturn1000e.saturn1000eCtrl.ClassFactory;

public class JavaOcx {

	public static void main(String[] args) throws InterruptedException {
		
		try{
			ComThread.InitMTA(true);
			
			/**
			 * 通过ProgID调用ＯＣＸ控件，组件的ProgID对应注册表中MFCActiveXControl1.ocx注册后的ProgID值，可以打开注册表查找ocx文件名找到对应的值
			 * HKEY_CLASSES_ROOT\Wow6432Node\CLSID\{9EDD1618-2C53-492F-B20F-AB3BF2410F08}
			 * ActiveXComponent com = new ActiveXComponent("组件的ProgID") ;
			 */
			//ActiveXComponent com = new ActiveXComponent("MFCACTIVEXCONTRO.MFCActiveXControCtrl.1");
			ActiveXComponent com = new ActiveXComponent("CLSID:535CE73B-482F-42B6-93AA-AD746181F1D0");
			
			//通过CLSID调用OCX控件
			Dispatch disp = (Dispatch) com.getObject();
			
			/**
			 * Dispatch.call(CLSID调用OCX控件， OCX控件的方法名， 參数【new Variant(*)】)
			 * Dispatch.call(CLSID调用OCX控件， OCX控件的方法順序号， 參数【new Variant(*)】)
			 * 既可以用接口名称"Init"调用，也可以用id(1)来调用
			 */
			//int result = Dispatch.call(disp, "Init", new Variant(accountno), new Variant(extno), new Variant(password)).getInt();			
			int result = Dispatch.call(disp, "Multiply", new Variant(5),new Variant(8)).getInt();
			System.out.println("初始化结果为"+result);
			
			
			
			
			com4j.Com4jObject messageInput = new Com4jObject() {
				
				@Override
				public void setName(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public <T extends Com4jObject> T queryInterface(Class<T> arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public <T extends Com4jObject> boolean is(Class<T> arg0) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public int getPtr() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public long getPointer() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public long getIUnknownPointer() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public com4j.ComThread getComThread() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void dispose() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public <T> EventCookie advise(Class<T> arg0, T arg1) {
					// TODO Auto-generated method stub
					return null;
				}
			};
		    Holder<com4j.Com4jObject> messageOutput = new Holder<Com4jObject>(messageInput);
		    
		    
		     //ClassFactory.createSaturn1000E().creditSales(messageInput, messageOutput);
		     ClassFactory.createSaturn1000E().getArchIPAddress();
		     //this.cookie = mainForm.advise(__CTISrv.class, myIMainFormEvents);
		     
		     
		     
		}catch(Exception e){
			//
			e.printStackTrace();
		}finally{
			//释放线程
		    ComThread.Release(); 
		}
	}

}
