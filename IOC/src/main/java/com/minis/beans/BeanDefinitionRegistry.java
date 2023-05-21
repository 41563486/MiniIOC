package com.minis.beans;

public interface BeanDefinitionRegistry {
    //1、注册bean定义
    void registerBeanDefinition(String name,BeanDefinition bd);
    //2、移除bean定义
    void  removeBeanDefinition(String name);
    //3、获取bean定义
    BeanDefinition getBeanDefinition(String  name);
    //4、判断bean定义
    boolean containsBeanDefinition(String name);



}
