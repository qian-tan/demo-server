package com.jojoreading.demo.server.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;
import java.util.Properties;

@Slf4j
@Configuration
public class SpringCommonConfig {

    @Autowired
    private MailConfig mailConfig;

    @Bean(name = "commonPropertyConfig")
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("/config/application-com.yml"));
        configurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

    @Bean(name = "javaMailSender")
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailConfig.getMailHost());
        javaMailSender.setUsername(mailConfig.getMailUsername());
        javaMailSender.setPassword(mailConfig.getMailPassword());
        javaMailSender.setPort(mailConfig.getMailPort());
        javaMailSender.setDefaultEncoding(mailConfig.getMailEncoding());
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.starttls.enable", mailConfig.getMailSMTPStartTLSEnable());
        properties.setProperty("mail.smtp.starttls.required", mailConfig.getMailSMTPStartTLSRequired());
        properties.setProperty("mail.smtp.ssl.enable", mailConfig.getMailSMTPSSLEnable());
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
