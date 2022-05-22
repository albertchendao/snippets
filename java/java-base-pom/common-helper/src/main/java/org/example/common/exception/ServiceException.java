package org.example.common.exception;


import org.example.common.enumeration.RespCode;

/**
 * 业务异常
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 2668428979959522600L;
    private RespCode errorCode;

    public ServiceException(RespCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ServiceException(String message, RespCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(String message, RespCode errorCode, Throwable exception) {
        super(message, exception);
        this.errorCode = errorCode;
    }

    public RespCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(RespCode errorCode) {
        this.errorCode = errorCode;
    }

    public int getHttpStatus() {
        return this.errorCode.getHttpCode();
    }

}
