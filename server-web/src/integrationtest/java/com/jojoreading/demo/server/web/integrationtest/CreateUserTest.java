package com.jojoreading.demo.server.web.integrationtest;

import com.jojoreading.demo.server.biz.common.CommonConstants;
import com.jojoreading.demo.server.core.domain.UserDO;
import com.jojoreading.demo.server.core.service.IUserCoreService;
import com.jojoreading.demo.server.test.ServerBaseApplicationTestBase;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试创建用户
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/22
 **/
public class CreateUserTest extends ServerBaseApplicationTestBase {

	@Autowired
	private IUserCoreService userCoreService;

	@Test
	public void createUser() throws Exception {
		final String userName = "t";
		final String userEmail = "t@t.t";

		// 替换实现
		JavaMailSender spyJavaMailSender = spy(context.getBean(JavaMailSender.class));
		replaceBeanInRuntime(JavaMailSender.class, spyJavaMailSender);

		// 捕获参数
		ArgumentCaptor<SimpleMailMessage> argument = ArgumentCaptor.forClass(SimpleMailMessage.class);

		// 执行操作请求
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/create");
		request.param("name", userName);
		request.param("email", userEmail);
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk()).andReturn();

		// 对业务内的数据进行一些断言
		// 断言有正确发送邮件信息
		verify(spyJavaMailSender).send(argument.capture());
		Assert.assertEquals(userEmail, Objects.requireNonNull(argument.getValue().getTo())[0]);
		Assert.assertEquals(CommonConstants.CREATE_USER_SUCCESS_EMAIL_TITLE, argument.getValue().getSubject());
		Assert.assertEquals(CommonConstants.CREATE_USER_SUCCESS_EMAIL_CONTENT, argument.getValue().getText());
		// 断言在数据库中存在对应的记录

		Collection<UserDO> userDOCollection = userCoreService.listByMap(Collections.singletonMap("email", userEmail));
		Assert.assertEquals(1, userDOCollection.size());
		UserDO userDO = userDOCollection.iterator().next();
		Assert.assertEquals(userName, userDO.getName());
		Assert.assertEquals(userEmail, userDO.getEmail());
	}

}
