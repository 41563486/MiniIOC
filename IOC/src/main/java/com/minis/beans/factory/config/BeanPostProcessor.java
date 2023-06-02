package com.minis.beans.factory.config;

import com.minis.ClassPathXmlApplicationContext_fast123;
import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;

public interface BeanPostProcessor {
    //初始化之前
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    //初始化之后
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;


}
