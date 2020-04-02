package jp.co.archTest.rest.ps.v1.telegram.response;

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
public class ErrorInfo implements IParser {

	public void parse(Element element) {
		
	}
}
