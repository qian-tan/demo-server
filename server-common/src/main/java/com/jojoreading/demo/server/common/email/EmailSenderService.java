package com.jojoreading.demo.server.common.email;

public interface EmailSenderService {

    /**
     * 发送邮件
     *
     * @param to        收件人
     * @param subject   主题
     * @param content   内容
     * @return true 表示发送成功
     *         false 表示发送失败
     */
    boolean sendMail(String to, String subject, String content);

}
