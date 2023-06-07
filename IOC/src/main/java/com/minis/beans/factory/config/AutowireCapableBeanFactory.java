package com.minis.beans.factory.config;

import com.minis.beans.BeanFactory;
import com.minis.beans.BeansException;

public interface AutowireCapableBeanFactory extends BeanFactory {
    //定义自动装配的状态,自动装配是通过名字或者类型
    int AUTOWIRE_NO=0;
    int AUTOWIRE_BY_NAME=1;
    int AUTOWIRE_BY_TYPE=2;
    //添加初始化前的扩展处理器
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean,String beanName)throws BeansException;
    //添加初始化后的扩展处理器
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean,String beanName)throws BeansException;

}
