package com.minis;

public interface BeanFactory {
    Object getBean(String beanName) throws BeanException;
    Boolean containsBean(String name);
    void registerBean(String beanName, Object obj);

    void registerBeanDefinition(BeanDefinition beanDefinition);
}
