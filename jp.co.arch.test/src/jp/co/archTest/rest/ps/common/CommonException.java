package jp.co.archTest.rest.ps.common;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

//WEBAPIのエラーレスポンスを返すためのカスタム例外
//参考:http://edgegram.hatenablog.jp/entry/2015/12/16/141347
public class CommonException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	private ErrorMessage errorMessage;
	private int statusCode = 200;

	public CommonException(Status status, String message) {
		this(status.getStatusCode(), message);
		this.statusCode = status.getStatusCode();
	}

	public CommonException(int errorCode, String message) {
		super(Response.status(200).entity(new ErrorMessage(errorCode, message)).build());
		this.statusCode = 200;
		this.errorMessage = new ErrorMessage(errorCode, message);
	}

	public CommonException(int statusCode, ErrorMessage errorMessage) {
		super(Response.status(statusCode).entity(errorMessage.toJsonString()).type(MediaType.APPLICATION_JSON).build());
		this.errorMessage = errorMessage;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
