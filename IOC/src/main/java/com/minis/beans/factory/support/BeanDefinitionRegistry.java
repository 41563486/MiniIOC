package com.minis.beans.factory.support;

import com.minis.beans.factory.config.BeanDefinition;

//bean定义的注册
public interface BeanDefinitionRegistry {
	//注册bean定义
	void registerBeanDefinition(String name, BeanDefinition bd);
	//移除bean定义
	void removeBeanDefinition(String name);
	//获取bea定义
	BeanDefinition getBeanDefinition(String name);
	//判断是否包含该名字bean定义
	boolean containsBeanDefinition(String name);
}
