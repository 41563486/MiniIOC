package com.minis.context;

import com.minis.beans.*;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;

public class ClassPathXmlApplicationContext implements BeanFactory,ApplicationEventPublisher {
    BeanFactory beanFactory;

    //context负责整合容器的启动过程，读外部配置，解析bean定义，创建BeanFactory


    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.LoadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }
    //context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法

    @Override
    public void publishEvent(ApplicationEvent event) {
    }

    @Override
    public Object getBean(String beanName) throws BeanException {
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


}
