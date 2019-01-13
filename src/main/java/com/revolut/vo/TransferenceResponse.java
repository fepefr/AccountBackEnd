package com.revolut.vo;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferenceResponse {
	private Integer httpStatus;
	private String message;
	private Erro error;

	public Erro getError() {
		return error;
	}

	public void setError(Erro error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	
}