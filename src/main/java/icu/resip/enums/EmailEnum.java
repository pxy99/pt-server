package icu.resip.enums;

import lombok.Getter;

/**
 * @Author Peng
 * @Date 2021/11/19 10:54
 * @Version 1.0
 */
@Getter
public enum EmailEnum {

    /**
     * 邮箱绑定成功邮件
     */
    BIND_EMAIL_SUCCESS("一页图区 邮箱绑定通知", "bind-email.ftl"),

    /**
     * 邮箱验证邮件
     */
    VERIFY_EMAIL("一页图区 邮箱验证", "verify-email.ftl");

    /**
     * 邮件主题
     */
    private final String subject;
    /**
     * 邮件内容/模板名称
     */
    private final String text;

    EmailEnum(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

}
