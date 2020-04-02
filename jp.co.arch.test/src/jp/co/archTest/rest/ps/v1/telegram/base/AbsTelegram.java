package jp.co.archTest.rest.ps.v1.telegram.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Element;

import lombok.extern.java.Log;

@Log
public abstract class AbsTelegram {

	public void toXml(Element root) {
		
		Field[] fs = this.getClass().getDeclaredFields();
		for (Field f : fs) {
			String propertyName = f.getName();
			try {
				Object prop = PropertyUtils.getProperty(this, propertyName);
				if (prop == null) {
					continue;
				}
				Element ele = root.addElement(propertyName);
				if (prop instanceof AbsTelegram) {
					((AbsTelegram) prop).toXml(ele);
				} else {
					ele.setText(prop.toString());
				}
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
				log.warning("リクエストメッセージのheader生成：" + propertyName + "のデータが取得されませんでした。");
			}

		}
		// return header;
	}
	
}
