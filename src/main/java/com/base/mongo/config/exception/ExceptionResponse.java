package com.base.mongo.config.exception;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;

@Scope("prototype")
public class ExceptionResponse {

	private Date timestamp;
	private HttpStatus error;
	private String message;
	private Object obj;
	
    public ExceptionResponse(){    
        this.timestamp = new Date();
    }
    
    public ExceptionResponse(String message){    
        timestamp = new Date();
        this.message = message;
    }
    
    public ExceptionResponse(String message, Object obj){    
        timestamp = new Date();
        this.message = message;
        this.obj = obj;
    }
    
    public ExceptionResponse(String message, Object obj, HttpStatus error){    
        timestamp = new Date();
        this.message = message;
        this.obj = obj;
        this.error = error;
    }
    
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public HttpStatus getError() {
		return error;
	}
	public void setError(HttpStatus error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}

}
