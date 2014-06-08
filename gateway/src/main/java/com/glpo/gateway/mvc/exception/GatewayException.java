package com.glpo.gateway.mvc.exception;

public class GatewayException extends RuntimeException{
	 
	private String exceptionMsg;
	private Throwable e;

	public GatewayException(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	public GatewayException(String exceptionMsg, Throwable e) {
		this.exceptionMsg = exceptionMsg;
		this.e = e;
	}
	
	public String getExceptionMsg(){
		return this.exceptionMsg;
	}
	
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
	public Throwable getExeption() {
	    return this.e;
	}
	
	public void setException(Throwable e) {
	    this.e = e;
	}
}