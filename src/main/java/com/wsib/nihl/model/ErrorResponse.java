package com.wsib.nihl.model;

public class ErrorResponse {

	private String status;
	private String errorCd;
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCd() {
		return errorCd;
	}

	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}

}
