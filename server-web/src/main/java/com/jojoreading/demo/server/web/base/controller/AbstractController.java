package com.jojoreading.demo.server.web.base.controller;

import com.jojoreading.demo.server.common.api.ResultDTO;
import com.jojoreading.demo.server.common.errorcode.ResultCode;
import com.jojoreading.demo.server.web.common.ApiResult;

/**
 * 基础方法
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/22
 **/
public class AbstractController {

	/**
	 * 转换失败的调用结果
	 * resultCode 优先顺序 resultDTO.resultCode.code, ResultCode.SYSTEM_ERROR.code
	 * errorMsg 优先顺序 resultDTO.message, resultDTO.resultCode.message, ResultCode.SYSTEM_ERROR.message
	 *
	 * @param resultDTO    失败的调用结果
	 * @return 接口返回对象
	 */
	protected ApiResult convertFailResultDTO2Result(ResultDTO resultDTO) {
		ApiResult result = ApiResult.builder().build();
		result.setResultCode(ResultCode.SYSTEM_ERROR.getCode());
		result.setErrorMsg(ResultCode.SYSTEM_ERROR.getMessage());

		if (resultDTO.getResultCode() != null) {
			result.setResultCode(resultDTO.getResultCode().getCode());
			result.setErrorMsg(resultDTO.getResultCode().getMessage());
		}

		if (resultDTO.getMessage() != null) {
			result.setErrorMsg(resultDTO.getMessage());
		}

		return result;
	}

}
