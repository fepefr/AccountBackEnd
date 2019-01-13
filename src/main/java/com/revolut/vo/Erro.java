package com.revolut.vo;

import java.util.ArrayList;
import java.util.List;
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Erro {
	  private Integer httpStatus;
      private String message;
      private List<FieldError> fieldErrors;
      
      
	public Erro(Integer status, String message) {
	    this.setHttpStatus(status);
	    this.message = message;
	}
      
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}
	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

    public void addFieldError(String path, String message) {
    	if(fieldErrors == null) fieldErrors = new ArrayList<FieldError>();
        FieldError error = new FieldError(path, message);
        fieldErrors.add(error);
    }

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}
}