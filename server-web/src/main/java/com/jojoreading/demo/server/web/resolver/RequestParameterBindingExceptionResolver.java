package com.jojoreading.demo.server.web.resolver;

import com.jojoreading.demo.server.common.errorcode.ResultCode;
import com.jojoreading.demo.server.web.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Slf4j
@Component("requestParameterBindingExceptionResolver")
public class RequestParameterBindingExceptionResolver extends AbstractHandlerExceptionResolver {

    private final static int order = 0;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof MissingServletRequestParameterException) {
                return handleMissingServletRequestParameter((MissingServletRequestParameterException) ex, response);
            } else if (ex instanceof MethodArgumentTypeMismatchException) {
				return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, response);
            } else if (ex instanceof ConstraintViolationException) {
				return handleConstraintViolationException((ConstraintViolationException) ex, response);
            } else if (ex instanceof BindException) {
				return handleBindException((BindException) ex, response);
            }
        } catch (Exception e) {
            logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", ex);
        }
        return null;
    }

	private ModelAndView handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletResponse response) {
		return buildExceptionModelView(ApiResult.error(ResultCode.INVALID_PARAMS,
			String.format("参数缺失,参数名 = %s,类型 = %s", ex.getParameterName(), ex.getParameterType())));
    }

	private ModelAndView handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletResponse response) {
		return buildExceptionModelView(ApiResult.error(ResultCode.INVALID_PARAMS,
			String.format("参数类型错误,参数名 = %s,类型= %s,值 = %s", ex.getName(), ex.getRequiredType().getSimpleName(), ex.getValue())));
    }

	private ModelAndView handleConstraintViolationException(ConstraintViolationException ex, HttpServletResponse response) {
		return buildExceptionModelView(ApiResult.error(ResultCode.INVALID_PARAMS,
			ex.getConstraintViolations().stream().findFirst().map(ConstraintViolation::getMessage).orElse(ex.getMessage())));
    }

	private ModelAndView handleBindException(BindException ex, HttpServletResponse response) {
		return buildExceptionModelView(ApiResult.error(ResultCode.INVALID_PARAMS,
			ex.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @Override
    public int getOrder() {
        return order;
    }

	private ModelAndView buildExceptionModelView(ApiResult result) {
		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		mav.addObject("result", result);
		return mav;
    }

}
