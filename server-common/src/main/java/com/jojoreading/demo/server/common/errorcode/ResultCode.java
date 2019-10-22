package com.jojoreading.demo.server.common.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

	SUCCESS(0, "成功"),
	SYSTEM_ERROR(1, "系统错误"),
	INVALID_PARAMS(2, "参数错误"),
    ;

	private Integer code;

    private String message;
}
