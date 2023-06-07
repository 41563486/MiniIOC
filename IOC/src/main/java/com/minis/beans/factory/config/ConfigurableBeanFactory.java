package com.minis.beans.factory.config;

import com.minis.beans.BeanFactory;
import com.minis.beans.SingletonBeanRegistry;
import com.minis.beans.factory.config.BeanPostProcessor;

public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {
    //单例(Singleton)：在整个应用程序中只创建一个Bean实例，所有请求该Bean的对象都共享同一个实例。
    //原型(Prototype)：每次请求该Bean时都会创建一个新的实例，每个请求都会获得一个独立的Bean实例。
    //作用域单例
    String SCOPE_SINGLETON="singleton";
    //作用域原型
    String SCOPE_PROTOTYPE="prototype";
    //添加初始化拦截器
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    //获取bean初始化拦截器的数量
    int getBeanPostProcessorCount();
    //注册依赖关系
    void registerDependentBean(String beanName,String dependentBeanName);
    //获取被依赖bean的集合
    String[] getDependentBeans(String beanName);
    //获取依赖bean的集合
    String[] getDependenciesForBean(String beanName);

}
