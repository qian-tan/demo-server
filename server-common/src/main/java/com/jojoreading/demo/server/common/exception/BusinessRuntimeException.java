package com.jojoreading.demo.server.common.exception;

import com.jojoreading.demo.server.common.errorcode.ResultCode;

public class BusinessRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -5317007026578376164L;

    /**
     * 错误码
     */
	private Integer errorCode;
    /**
     * 错误描述
     */
    private String errorMsg;

	public BusinessRuntimeException(Integer errorCode, String errorMsg) {
        super(String.format("BusinessException{errorCode:%s, errorMsg:%s}", errorCode, errorMsg));
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

	public BusinessRuntimeException(Integer errorCode, String errorMsg, Throwable cause) {
        super(String.format("BusinessException{errorCode:%s, errorMsg:%s}", errorCode, errorMsg), cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessRuntimeException(ResultCode resultCode) {
        super(String.format("BusinessException{errorCode:%s, errorMsg:%s}", resultCode.getCode(), resultCode.getMessage()));
    }

    public BusinessRuntimeException(ResultCode resultCode, Throwable cause) {
        super(String.format("BusinessException{errorCode:%s, errorMsg:%s}", resultCode.getCode(), resultCode.getMessage()), cause);
    }

	public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
