package com.minis.beans;

public interface BeanFactory {


    //1、获取bean特性
    Object getBean(String beanName) throws BeanException;
    boolean ContainsBean(String name);
    //2、注册bean
    void registerBean(String beanName,Object obj);

    boolean isSingleton(String name);
    boolean isPrototype(String name);
    Class<?> getType(String name);




}
