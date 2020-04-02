package jp.co.archTest.rest.ps.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

	private long errorCode;
	private String message;

	//JSON文字列に変換して出力
	public String toJsonString() {
		try {
			return new ObjectMapper().writeValueAsString(new ErrorMessage(this.errorCode,this.message));
		} catch (JsonProcessingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
}
