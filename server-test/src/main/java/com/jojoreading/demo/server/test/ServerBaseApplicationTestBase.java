package com.jojoreading.demo.server.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@Slf4j
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = ServerBaseApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ServerBaseApplicationTestBase extends UnitTestBase {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(context)
                .build();
    }

	protected  <T> void replaceBeanInRuntime(Class<T> type, T newBean) {
		log.info("Replace bean in runtime, type={}", type);
		ConfigurableApplicationContext configContext = (ConfigurableApplicationContext) context;
		SingletonBeanRegistry beanRegistry = configContext.getBeanFactory();
		DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanRegistry;

		// 读取对应的实现，目前只能处理单例模式的服务
		Map<String, T> beanMap = listableBeanFactory.getBeansOfType(type);
		Assert.assertEquals(1, beanMap.size());
		String originalBeanName = beanMap.keySet().iterator().next();

		// 从 context 中移除以前的实现
		listableBeanFactory.removeBeanDefinition(originalBeanName);
		// 在 context 中注册最新的实现
		listableBeanFactory.registerSingleton(originalBeanName, newBean);
	}

}
