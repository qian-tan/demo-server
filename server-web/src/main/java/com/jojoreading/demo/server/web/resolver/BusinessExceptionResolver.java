package com.jojoreading.demo.server.web.resolver;

import com.alibaba.fastjson.JSONObject;
import com.jojoreading.demo.server.common.errorcode.ResultCode;
import com.jojoreading.demo.server.common.exception.BusinessRuntimeException;
import com.jojoreading.demo.server.web.common.ApiResult;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component("exceptionResolver")
public class BusinessExceptionResolver extends AbstractHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
											  Exception ex) {
        ApiResult result;
		if (ex instanceof BusinessRuntimeException) {
            BusinessRuntimeException e = (BusinessRuntimeException) ex;
			result = ApiResult.error(e.getErrorCode(), e.getErrorMsg());
        } else {
            String requestInfo = dumpRequestInfo(request);
			log.error("Catch exception, request = {}", requestInfo, ex);
			result = ApiResult.error(ResultCode.SYSTEM_ERROR);
		}

		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		mav.addObject("result", result);
		return mav;
    }

    private String dumpRequestInfo(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		Map parameterMap = Maps.newHashMap(request.getParameterMap());
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("path", requestURI);
		jsonObject.put("parameters", parameterMap);
		return jsonObject.toJSONString();
    }
}
