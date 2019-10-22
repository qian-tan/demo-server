package com.jojoreading.demo.server.biz.service.impl;

import com.jojoreading.demo.server.biz.common.CommonConstants;
import com.jojoreading.demo.server.biz.domain.CreateUserDTO;
import com.jojoreading.demo.server.biz.service.IUserService;
import com.jojoreading.demo.server.common.api.ResultDTO;
import com.jojoreading.demo.server.common.email.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户接口实现
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/22
 **/
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public ResultDTO<Void> createUser(CreateUserDTO createUserDTO) {
		// TODO: 2019/10/22 存储到数据库中
		log.info("Insert record success, CreateUserDTO={}", createUserDTO);

		// 发送邮件信息
		emailSenderService.sendMail(createUserDTO.getEmail(), CommonConstants.CREATE_USER_SUCCESS_EMAIL_TITLE,
			CommonConstants.CREATE_USER_SUCCESS_EMAIL_CONTENT);

		return ResultDTO.<Void>builder().success(true).build();
	}

}
