package com.jojoreading.demo.server.common.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DruidConfig {

    @Value("${common.datasource.url}")
    private String dbUrl;
    @Value("${common.datasource.username}")
    private String username;
    @Value("${common.datasource.password}")
    private String password;
    @Value("${common.datasource.driverClassName}")
    private String driverClassName;
    @Value("${common.datasource.druid.defaultReadOnly}")
    private Boolean defaultReadOnly;
    @Value("${common.datasource.druid.initialSize}")
    private Integer initialSize;
    @Value("${common.datasource.druid.maxActive}")
    private Integer maxActive;
    @Value("${common.datasource.druid.minIdle}")
    private Integer minIdle;
    @Value("${common.datasource.druid.maxWait}")
    private Integer maxWait;
    @Value("${common.datasource.druid.validationQuery}")
    private String validationQuery;
    @Value("${common.datasource.druid.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${common.datasource.druid.testOnReturn}")
    private Boolean testOnReturn;
    @Value("${common.datasource.druid.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${common.datasource.druid.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${common.datasource.druid.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${common.datasource.druid.logAbandoned}")
    private Boolean logAbandoned;
    @Value("${common.datasource.druid.removeAbandoned}")
    private Boolean removeAbandoned;
    @Value("${common.datasource.druid.removeAbandonedTimeout}")
    private Integer removeAbandonedTimeout;
    @Value("${common.datasource.druid.removeAbandonedTimeoutMillis}")
    private Integer removeAbandonedTimeoutMillis;

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setDefaultReadOnly(defaultReadOnly);
        datasource.setInitialSize(initialSize);
        datasource.setMaxActive(maxActive);
        datasource.setMinIdle(minIdle);
        datasource.setMaxWait(maxWait);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setLogAbandoned(logAbandoned);
        datasource.setRemoveAbandoned(removeAbandoned);
        datasource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        datasource.setRemoveAbandonedTimeoutMillis(removeAbandonedTimeoutMillis);

        // 添加 sql 过滤器
        List<Filter> filterList = new ArrayList<>();

        datasource.setProxyFilters(filterList);

        // 添加数据集支持
        List<String> list = new ArrayList<>();
        list.add("set names utf8mb4");

        datasource.setConnectionInitSqls(list);
        return datasource;
    }

}
