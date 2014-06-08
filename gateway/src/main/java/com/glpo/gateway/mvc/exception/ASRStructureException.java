package com.glpo.gateway.mvc.exception;

public class ASRStructureException extends RuntimeException{
	 
	private String exceptionMsg;
	private Throwable e;

	public ASRStructureException(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	public ASRStructureException(String exceptionMsg, Throwable e) {
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
