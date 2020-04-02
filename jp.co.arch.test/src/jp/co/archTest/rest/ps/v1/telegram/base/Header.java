package jp.co.archTest.rest.ps.v1.telegram.base;

import java.util.Iterator;

import org.dom4j.Element;

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
public class Header extends AbsTelegram implements IParser {

	/**
	 * 端末ID
	 */
	private String terminalID;
	/**
	 * 店番号
	 */
	private Integer storeNo;
	/**
	 * レジ番号
	 */
	private Integer regNo;

	/**
	 * 送信日時
	 */
	private String sendDateTime;

	/**
	 * 精算回数
	 */
	private Integer calculationCount;

	/**
	 * 取引番号
	 */
	private Integer transactionNo;

	/**
	 * リクエストID
	 */
	private Integer requestID;

	/**
	 * コマンドNo.
	 */
	private Integer commandNo;

	public void parse(Element element) {

		Iterator<Element> iterator = element.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			String nodeName = e.getName();
			if ("terminalID".equals(nodeName)) {
				this.terminalID = e.getText();
			} else if ("storeNo".equals(nodeName)) {
				this.storeNo = Integer.parseInt(e.getText());
			} else if ("regNo".equals(nodeName)) {
				this.regNo = Integer.parseInt(e.getText());
			} else if ("sendDateTime".equals(nodeName)) {
				this.sendDateTime = e.getText();
			} else if ("calculationCount".equals(nodeName)) {
				this.calculationCount = Integer.parseInt(e.getText());
			} else if ("transactionNo".equals(nodeName)) {
				this.transactionNo = Integer.parseInt(e.getText());
			} else if ("requestID".equals(nodeName)) {
				this.requestID = Integer.parseInt(e.getText());
			} else if ("commandNo".equals(nodeName)) {
				this.commandNo = Integer.parseInt(e.getText());
			}
		}
	}
}
