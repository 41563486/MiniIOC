package com.minis.beans;

public interface BeanFactory {


    //1、获取bean特性
    Object getBean(String beanName) throws BeansException;
    //3、判断是否含bean实例
    boolean ContainsBean(String name);
    //2、注册bean
    void registerBean(String beanName,Object obj);

    //4、判断是否是新增的单例bean
    boolean isSingleton(String name);
    //5、判断是否是新增的属性值
    boolean isPrototype(String name);
    //6、获取bean的类型
    Class<?> getType(String name);



}
