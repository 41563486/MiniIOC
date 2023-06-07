package com.minis.beans.factory;

import com.minis.beans.BeanFactory;
import com.minis.beans.BeansException;

import java.util.Map;

//获取bean集合等职能

public interface ListableBeanFactory extends BeanFactory {
    //判断包含某个bean定义
    boolean containsBeanDefinition(String beanName);

    //获取bean定义的数量
    int getBeanDefinitionCount();

    //获取bean定义的名字集合

    String[] getBeanDefinitionNames();

    //获取bean类集合获取bean别名
    String[] getBeanNamesForType(Class<?> type);

    //根据指定的类型获取所有满足条件的bean集合
    <T> Map<String, T> getBeanOfType(Class<?> type) throws BeansException;

}
