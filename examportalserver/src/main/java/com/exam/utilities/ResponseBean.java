package com.exam.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
/**
 * This class represents the structure of response to be used to be send response from all API's
 * @author Ashok Ulava
 */
@Component
public class ResponseBean {

	private HttpStatus status;
	private String message;
	private Object data;
	
	public ResponseBean() {
		
	}
	public ResponseBean(HttpStatus status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
