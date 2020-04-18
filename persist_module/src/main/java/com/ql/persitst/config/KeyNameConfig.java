package com.ql.persitst.config;

/**
 * 原则上讲，所有使用到的Key都需要在这里进行维护
 * 允许出现 KeyNameConfig.XXX+"区分ID"的组合字段
 * 不同文件下的Key允许重名
 * 防止出现的Key到处乱飞，随便起名字的问题
 * 如果Key不在配置中则使用无效
 */
public class KeyNameConfig {
    public static final String DEMO_NAME="ZTY";
}
