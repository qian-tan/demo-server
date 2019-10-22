package com.jojoreading.demo.server.web.common;

import com.jojoreading.demo.server.common.errorcode.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 344400440420827850L;

	private Integer resultCode;

	private T data;

	private String errorMsg;

    public static <T> ApiResult<T> success() {
		return ApiResult.<T>builder().resultCode(ResultCode.SUCCESS.getCode()).build();
    }

    public static <T> ApiResult<T> success(T data) {
		return ApiResult.<T>builder().resultCode(ResultCode.SUCCESS.getCode()).data(data).build();
    }

	public static <T> ApiResult<T> error(ResultCode resultCode) {
		return ApiResult.<T>builder().resultCode(resultCode.getCode()).errorMsg(resultCode.getMessage()).build();
    }

    public static <T> ApiResult<T> error(ResultCode resultCode, String customErrorMsg) {
		return ApiResult.<T>builder().resultCode(resultCode.getCode()).errorMsg(customErrorMsg).build();
	}

	public static <T> ApiResult<T> error(Integer code, String customErrorMsg) {
		return ApiResult.<T>builder().resultCode(code).errorMsg(customErrorMsg).build();
	}

    @Override
    public ApiResult<T> clone() {
		return ApiResult.<T>builder().resultCode(resultCode).data(data).errorMsg(errorMsg).build();
    }
}
