package com.jojoreading.demo.server.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Slf4j
public abstract class UnitTestBase {

    private static final String DOCKER_PROPS_PATH = "docker.properties";
    private static final String BASE_PROPS_PATH = "apollo-config";
    private static final String[] ALLOW_PROPS_EXT = new String[]{"properties", "yml"};

    private static Properties dockerProps;

    private static Properties loadProps(File configFile) {
        Properties props = new Properties();
        try {
            InputStream inputStream = new FileInputStream(configFile);
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return props;
    }

    // 成功初始化 Apollo 配置中心
    private static boolean initApolloSuccess = false;

    // 初始化 Apollo 配置中心
    private static void initApollo(File dockerPropsFile) {
        if (initApolloSuccess) {
            log.info("Init Apollo success already");
            return;
        }

        // 读取指定目录下的配置
        File file = new File(UnitTestBase.class.getClassLoader().getResource(BASE_PROPS_PATH).getFile());
        Collection<File> fileList = FileUtils.listFiles(file, ALLOW_PROPS_EXT, true);
        for (File configFile: fileList) {
            log.info("file path:" + configFile.getAbsolutePath());
        }

        // 初始化配置中心
        List<String> namespaceList = new ArrayList<>();
        ApolloTestClient client = new ApolloTestClient(dockerPropsFile);
        for (File configFile: fileList) {
			namespaceList.add(client.createNamespaceAndInitProps(configFile));
        }

        // 配置系统属性
        String namespaces = "application," + String.join(",", namespaceList);
        System.setProperty("apollo.configService", "http://127.0.0.1:18080");
        System.setProperty("app.id", "dev-app");
        System.setProperty("apollo.bootstrap.enabled", "true");
        System.setProperty("apollo.bootstrap.namespaces", namespaces);

        initApolloSuccess = true;
        log.info("Init Apollo success");
    }

    @BeforeClass
    public static void initBeforeClass() {
        // 单是在 idea 里面的 maven 插件选择 profile 不会生效，需要启动前手动声明
        System.setProperty("spring.profiles.active", "test-suit");

        // 读取 Docker 配置信息
        File dockerPropsFile = new File(UnitTestBase.class.getClassLoader().getResource(DOCKER_PROPS_PATH).getFile());
        dockerProps = loadProps(dockerPropsFile);

        // 初始化 Docker 中的资源
        initDockerResource();

        // 初始化配置中心
        initApollo(dockerPropsFile);
    }

    // 初始化 Docker 中的资源
    private static void initDockerResource() {
        // 初始化 MySQL 资源
        // 读取文件夹内的所有文件
        File mysqlInitSqlFile = new File(UnitTestBase.class.getClassLoader().getResource("init-sql").getFile());
        Collection<File> fileList = FileUtils.listFiles(mysqlInitSqlFile, new String[]{"sql"}, true);

        // 执行初始化
        String mysqlDriver = dockerProps.getProperty("mysql_driver");
        String mysqlHost = dockerProps.getProperty("mysql_host");
        Integer mysqlPort = Integer.valueOf(dockerProps.getProperty("mysql_port"));
        String mysqlUser = dockerProps.getProperty("mysql_user");
        String mysqlPassword = dockerProps.getProperty("mysql_password");
        String jdbcUrl = "jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&"
                + "user=" + mysqlUser + "&password=" + mysqlPassword;
        for (File configFile: fileList) {
            try {
                Class.forName(mysqlDriver);
                Connection mConnection = DriverManager.getConnection(jdbcUrl);
                ScriptRunner runner = new ScriptRunner(mConnection, false, false);
                runner.runScript(new BufferedReader(new FileReader(configFile)));
            } catch (IOException | ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // 初始化 Redis 资源
        String address = "redis://" + dockerProps.getProperty("redis_host") + ":" + dockerProps.getProperty("redis_port");
        Config config = new Config();
        config.useSingleServer().setAddress(address)
                .setDatabase(0)
                .setPassword(dockerProps.getProperty("redis_password"));

        RedissonClient redissonClient = Redisson.create(config);
		redissonClient.getKeys().flushall();
    }

    @Before
    public void initBeforeMethod() {
        initDockerResource();
    }

}
