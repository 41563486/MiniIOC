package com.minis.beans.factory.config;

import com.minis.beans.BeansException;

//初始化bean拦截器
public interface BeanPostProcessor {
    //初始化之前
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    //初始化之后
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;


}
