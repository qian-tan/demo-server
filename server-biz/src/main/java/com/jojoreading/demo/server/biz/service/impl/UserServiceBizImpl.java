package com.jojoreading.demo.server.biz.service.impl;

import com.jojoreading.demo.server.biz.common.CommonConstants;
import com.jojoreading.demo.server.biz.domain.CreateUserDTO;
import com.jojoreading.demo.server.biz.service.IUserBizService;
import com.jojoreading.demo.server.common.api.ResultDTO;
import com.jojoreading.demo.server.common.email.EmailSenderService;
import com.jojoreading.demo.server.core.domain.UserDO;
import com.jojoreading.demo.server.core.service.IUserCoreService;
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
public class UserServiceBizImpl implements IUserBizService {

	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private IUserCoreService userCoreService;

	@Override
	public ResultDTO<Void> createUser(CreateUserDTO createUserDTO) {
		// 存储用户信息
		UserDO userDO = new UserDO();
		userDO.setUid(System.currentTimeMillis());
		userDO.setName(createUserDTO.getName());
		userDO.setEmail(createUserDTO.getEmail());
		userCoreService.save(userDO);

		// 发送邮件信息
		emailSenderService.sendMail(createUserDTO.getEmail(), CommonConstants.CREATE_USER_SUCCESS_EMAIL_TITLE,
			CommonConstants.CREATE_USER_SUCCESS_EMAIL_CONTENT);

		return ResultDTO.<Void>builder().success(true).build();
	}

}
