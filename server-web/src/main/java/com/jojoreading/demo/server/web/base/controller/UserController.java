package com.jojoreading.demo.server.web.base.controller;

import com.jojoreading.demo.server.biz.domain.CreateUserDTO;
import com.jojoreading.demo.server.biz.service.IUserService;
import com.jojoreading.demo.server.common.api.ResultDTO;
import com.jojoreading.demo.server.web.base.model.req.CreateUserParam;
import com.jojoreading.demo.server.web.common.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/user")
@RestController
public class UserController extends AbstractController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ApiResult createUser(@Validated CreateUserParam createUserParam) {
		// 创建用户
		ResultDTO<Void> resultDTO = userService.createUser(CreateUserDTO.builder().name(createUserParam.getName())
			.email(createUserParam.getEmail()).build());

		if (resultDTO.isSuccess()) {
			return ApiResult.success();
		} else {
			return convertFailResultDTO2Result(resultDTO);
		}
	}

}
