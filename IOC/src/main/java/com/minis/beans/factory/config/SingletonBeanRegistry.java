package com.minis.beans.factory.config;

public interface SingletonBeanRegistry {
    //1、注册单例bean
    void registerSingleton(String beanName,Object singletonObject);
    //2、获取单例bean
    Object getSingleton(String beanName);
    //3、判断是否包含单例
    boolean containsSingleton(String beanName);
    //4、单例bean名称的集合
    String [] getSingletonNames();
}
