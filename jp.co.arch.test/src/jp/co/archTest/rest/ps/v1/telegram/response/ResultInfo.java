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
public class ResultInfo implements IParser {

	/**
	 * 結果<br>
	 * -1=エラー, 0=正常, 1=警告
	 */
	private Integer result;

	/**
	 * エラー情報
	 */
	private ErrorInfo errorInfo;

	public void parse(Element element) {

		Iterator<Element> iterator = element.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			String nodeName = e.getName();
			if ("result".equals(nodeName)) {
				this.result = Integer.parseInt(e.getText());
			} else if ("errorInfo".equals(nodeName)) {
				this.errorInfo = new ErrorInfo();
				this.errorInfo.parse(e);
			}
		}
	}
}
