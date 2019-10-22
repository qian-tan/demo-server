package com.jojoreading.demo.server.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("tab_user")
public class UserDO extends BaseDO {

	@TableId("`uid`")
	private Long uid;

	@TableField("`name`")
	private String name;

	@TableField("`email`")
	private String email;

}

