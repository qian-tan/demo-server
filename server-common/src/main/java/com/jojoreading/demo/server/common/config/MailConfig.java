package com.jojoreading.demo.server.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MailConfig {

    @Value("${common.mail.from}")
	private String mailFrom;

    @Value("${common.mail.host}")
    private String mailHost;

    @Value("${common.mail.port}")
    private Integer mailPort;

    @Value("${common.mail.encoding}")
    private String mailEncoding;

    @Value("${common.mail.username}")
    private String mailUsername;

    @Value("${common.mail.password}")
    private String mailPassword;

    @Value("common.mail.smtp.starttls.enable")
    private String mailSMTPStartTLSEnable;

    @Value("common.mail.smtp.starttls.required")
    private String mailSMTPStartTLSRequired;

    @Value("${common.mail.smtp.ssl.enable}")
    private String mailSMTPSSLEnable;

}
