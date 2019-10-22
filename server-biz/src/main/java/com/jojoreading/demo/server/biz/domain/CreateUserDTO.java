package com.jojoreading.demo.server.biz.domain;

import lombok.Builder;
import lombok.Data;

/**
 * 创建用户
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/22
 **/
@Data
@Builder
public class CreateUserDTO {
	private String name;
	private String email;
}
