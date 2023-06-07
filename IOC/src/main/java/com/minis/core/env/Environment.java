package com.minis.core.env;

//获取属性
public interface Environment  extends PropertyResolver {
    //获取活动profiles配置文件
    String[] getActiveProfiles();
    //获取默认profiles配置文件
    String[] getDefaultProfiles();
    //判断profiles文件是否在活动状态
    boolean acceptsProfiles(String...profiles);

}
