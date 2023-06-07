package com.minis.core.env;

//解析属性
public interface PropertyResolver {
    //判断是否包含属性
    boolean containsProperty(String key);
    //获取属性
    String getProperty(String key);
    //获取属性值
    String getProperty(String key,String defaultValue);
    //将属性转换为指定类型
    <T> T getProperty(String key,Class<T> targetType);
    //将属性转换为指定类型并，如果找不到则返回指定的默认值
    <T> T getProperty(String key,Class<T> targetType,T defaultValue);
    //根据属性键获取属性值，并将其转换为目标类型
    <T> Class<T> getPropertyAsClass(String key,Class<T> targetType)throws IllegalStateException;
    //解析属性占位值并替换为属性值
    String resolvePlaceholders(String text);
    //解析包含属性占位符的字符串，并将其替换为实际的属性值,常用与文件加载和属性解析中，如果找不到占位符所对应的属性值，则会抛出异常
    String resolveRequiredPlaceholders(String text) throws IllegalArgumentException;
    

}
