package com.jojoreading.demo.server.biz.service;

import com.jojoreading.demo.server.biz.domain.CreateUserDTO;
import com.jojoreading.demo.server.common.api.ResultDTO;

/**
 * 用户接口
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/22
 **/
public interface IUserBizService {

	/**
	 * 创建用户
	 *
	 * @param createUserDTO    用户资料
	 * @return true 表示创建成功
	 *         false 表示创建失败
	 */
	ResultDTO<Void> createUser(CreateUserDTO createUserDTO);

}
