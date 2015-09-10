package com.suay.king.exception.http;

/**
 * 
 * @author csuay
 *
 */
public class HttpException extends Exception {

    /**
     * Serial for this class version
     */
    private static final long serialVersionUID = -2408869074768230346L;

    private int httpCode;
    private String httpMessage;

    public HttpException(int code, String httpMessage) {
	super();
	this.httpCode = code;
	this.httpMessage = httpMessage;
    }

    /**
     * @return the httpCode
     */
    public int getHttpCode() {
	return httpCode;
    }

    /**
     * @return the httpMessage
     */
    public String getHttpMessage() {
	return httpMessage;
    }

}
