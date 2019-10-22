package com.jojoreading.demo.server.common.api;

import com.jojoreading.demo.server.common.errorcode.ResultCode;
import lombok.Builder;
import lombok.Data;

/**
 * 公共返回对象
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/22
 **/
@Data
@Builder
public class ResultDTO<T> {
	private boolean success;
	private ResultCode resultCode;
	private String message;
	private T data;
}
