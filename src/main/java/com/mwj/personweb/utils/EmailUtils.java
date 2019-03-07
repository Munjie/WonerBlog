package com.mwj.personweb.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @Author: 母哥 @Date: 2019-03-07 11:53 @Version 1.0
 */

// 邮件发送工具
@Component
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender; // 注入 javaMailSender

    @Value("${spring.mail.username}")
    private String username; // 发送邮件者 邮箱 【谁发的】

    /**
     * 发送邮件
     *
     * @param title           标题
     * @param titleWithName   是否在标题后添加 名称
     * @param content         内容
     * @param contentWithName 是否在内容后添加 名称
     * @param email           接收者的邮箱【注册人】
     */
    public void sendNormalEmail(
            String title, boolean titleWithName, String content, boolean contentWithName, String email) {
        String dName = "[个人博客]";
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = javaMailSender.createMimeMessage(); // 创建要发送的信息
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(new InternetAddress(username, dName, "UTF-8")); // 设置 谁发送的
            helper.setTo(email); // 发给谁 【接收者的邮箱】
            title = titleWithName ? title + "-" + dName : title; // 标题内容
            helper.setSubject(title); // 发送邮件的辩题
            if (contentWithName) {
                content += "<p style='text-align:right'>" + dName + "</p>";
                content +=
                        "<p style='text-align:right'>" + SendUtil.curDate("yyyy-MM-dd HH:mm:ss") + "</p>";
            }
            helper.setText(content, true); // 发送的内容 是否为html
        } catch (Exception e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送重置密码的验证码
     *
     * @param email 接收者的邮箱【注册人】
     * @param code  验证码
     */
    public void sendRestPwdCode(final String email, String code) {
        final StringBuffer sb = new StringBuffer(); // 实例化一个StringBuffer
        sb.append("<h2>" + email + ",您好！<h2>").append("<p>此次重置密码的验证码是：" + code + "</p>");
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        sendNormalEmail("验证码", true, sb.toString(), true, email);
                    }
                })
                .start();
    }

    /**
     * 注册成功时的提示邮件
     *
     * @param email 接收的邮箱地址 【注册人】
     * @param pwd   初始密码
     * @param url   登陆地址
     */
    public void registerSucSender(final String email, String name, String pwd, String url) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<h3>恭喜您，注册成功！</h3>")
                .append("<h2>用户名是：<b style=''>")
                .append(name)
                .append("<h2>你设置的初始密码是：<b style=''>")
                .append(pwd)
                .append(",<a href='")
                .append(url)
                .append("'>登陆网站</a>");
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        sendNormalEmail("注册成功", true, sb.toString(), true, email);
                    }
                })
                .start();
    }

    /**
     * 重置密码成功
     *
     * @param email 接收的邮箱地址 【注册人】
     * @param pwd   初始密码
     * @param url   登陆地址
     */
    public void pwdResetSucSender(final String email, String pwd, String url) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<h3>恭喜您，重置密码成功！</h3>")
                .append("<h2>您此次重置的密码是：<b>")
                .append(pwd)
                .append("<a href='")
                .append(url)
                .append("'>登陆网站</a>");

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        sendNormalEmail("重置密码成功", true, sb.toString(), true, email);
                    }
                })
                .start();
    }

    /**
     * 新用户注册通知管理员
     *
     * @param email    接收邮箱地址（管理员的）
     * @param nickname 注册人姓名
     * @param regEmail 注册人邮箱
     * @param url      地址
     */
    public void newUserSender(final String email, String nickname, String regEmail, String url) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<a href='").append(url).append("'><h1>姓名：").append(nickname).append("</h1></a>");
        sb.append("<h3>注册邮箱：").append(regEmail).append("</h3>");
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        sendNormalEmail("新用户注册通知", true, sb.toString(), true, email);
                    }
                })
                .start();
    }

    // 非空判断
    public static boolean isNotEmpty(String str) {
        if (str != null && str.trim().length() != 0) {
            return true;
        }
        return false;
  }
}
