package com.example.springbootrestserver.exception;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {
	
    private Date timeStamp ;
    private HttpStatus status;
    private String message;
    private List<String> errors;
    private String path;

    public ApiError( Date timeStamp ,HttpStatus status, String message, List<String> errors,String path) {
        super();
        this.timeStamp=timeStamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.path=path;
        
    }

    public ApiError( Date timeStamp, HttpStatus status, String message, String error,String path) {
        super();
        this.timeStamp=timeStamp;
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
        this.path=path;
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

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
    
	
}
