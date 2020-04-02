package jp.co.archTest.rest.ps.common;

import java.util.HashMap;

public class ResponsData {

	private boolean result = true;

	private Object success = new HashMap<String, Object>();

	private ErrorMessage failure = new ErrorMessage(0, "");

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Object getSuccess() {
		return success;
	}

	public void setSuccess(Object success) {
		this.success = success;
	}

	public ErrorMessage getFailure() {
		return failure;
	}

	public void setFailure(ErrorMessage failure) {
		this.failure = failure;
	}

}
