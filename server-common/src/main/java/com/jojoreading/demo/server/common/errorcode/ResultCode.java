package com.jojoreading.demo.server.common.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

	SYSTEM_ERROR("0000", "系统错误"),
    ;

    private String code;

    private String message;
}
