package com.minis;

public interface BeanFactory {
    Object getBean(String beanName) throws BeanException;
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
