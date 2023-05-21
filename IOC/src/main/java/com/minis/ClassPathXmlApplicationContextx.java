package com.minis;

import com.minis.beans.*;
import com.minis.context.ApplicationEvent;
import com.minis.context.ApplicationEventPublisher;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;

public class ClassPathXmlApplicationContextx implements BeanFactory, ApplicationEventPublisher {
    BeanFactory beanFactory;

    //context负责整合容器的启动过程，读外部配置，解析bean定义，创建BeanFactory


    public ClassPathXmlApplicationContextx(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public boolean ContainsBean(String name) {
        return this.beanFactory.ContainsBean(name);
    }


    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName,obj);

    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }


    //context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法


/*    @Override
    public Object getBean(String beanName) throws BeanException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public boolean ContainsBean(String name) {
        return false;
    }*/

//    @Override
//    public boolean ContainsBean(String name) {
//        return false;
//    }
//
//    @Override
//    public void registerBeanDefinition(BeanDefinition beanDefinition) {
//        this.beanFactory.registerBeanDefinition(beanDefinition);
//    }
//    @Override
//    public Boolean containsBean(String name){
//        return  this.beanFactory.containsBean(name);
//
//}
/*
    @Override
    public void registerBean(String beanName, Object obj) {
        return;
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }*/
}
