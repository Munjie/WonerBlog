package com.mwj.personweb.service;

/**
 * @Author: 母哥 @Date: 2019-02-28 13:08 @Version 1.0
 */
public interface IMailService {

    /**
     * 发送简单邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    void sendSimpleEmail(String to, String subject, String content);

    /**
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filepath
     */
    void sendFileMail(String to, String subject, String content, String filepath);
}
