package com.minis.beans.factory.config;

import com.minis.beans.BeansException;

import com.minis.beans.factory.BeanFactory;

//bean工厂请求拦截器
public interface BeanFactoryPostProcessor {
	void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
