package com.jojoreading.demo.server.web.base.model.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 创建用户信息
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/22
 **/
@Data
public class CreateUserParam {
	@Length(max = 32)
	@NotBlank
	private String name;

	@Length(max = 254)
	@NotBlank
	private String email;
}
