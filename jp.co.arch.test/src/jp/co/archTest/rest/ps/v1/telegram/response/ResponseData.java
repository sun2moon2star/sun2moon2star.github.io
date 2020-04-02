package jp.co.archTest.rest.ps.v1.telegram.response;

import java.util.Iterator;

import org.dom4j.Element;

import jp.co.archTest.rest.ps.v1.telegram.base.IParser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ヘッダ
 * 
 * @author xueyb
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseData implements IParser {

	/**
	 * 端末ID
	 */
	private ResultInfo resultInfo;

	private Object dataInfo;

	public void parse(Element node) {
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			String nodeName = e.getName();
			if ("resultInfo".equals(nodeName)) {
				this.resultInfo = new ResultInfo();
				this.resultInfo.parse(e);
			} else {
				try {
					nodeName = nodeName.substring(0, 1).toUpperCase() + nodeName.substring(1);
					Class<?> c = Class.forName("jp.co.archTest.rest.ps.v1.telegram.response." + nodeName);
					Object o = c.newInstance();
					if (o instanceof IParser) {
						((IParser) o).parse(e);
					} else {
						// TODO LOG
					}
					this.dataInfo = o;
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
