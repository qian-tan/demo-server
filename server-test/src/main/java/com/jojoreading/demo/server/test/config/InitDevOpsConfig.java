package com.jojoreading.demo.server.test.config;

import com.jojoreading.demo.server.test.UnitTestBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 初始化 devops 资源
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/25
 **/
@Slf4j
@Configuration
public class InitDevOpsConfig {

	@PostConstruct
	public void logResource() {
		log.info("Prepare to load DevOps resource");
		UnitTestBase.initBeforeClass();
	}

}
