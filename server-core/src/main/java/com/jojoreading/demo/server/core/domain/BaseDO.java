package com.jojoreading.demo.server.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * 基础类型
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/22
 **/
public class BaseDO {

	@TableField("update_time")
	private Date updateTime;

	@TableField("create_time")
	private Date createTime;

}
