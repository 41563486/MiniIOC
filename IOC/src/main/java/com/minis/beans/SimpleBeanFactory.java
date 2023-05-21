package com.minis.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    //实例中存放所有bean定义的实例的map
    //ConcurrentHashMap允许多个线程同时读写，锁的粒度较小。不会锁住整个线程，使用了锁分段技术。内部的值是设定表的大小而不会动态调整
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);

    //定义bean定义实例的集合
    private List<String> beanDefinitionNames = new ArrayList<>();

    //无参构造器
    public SimpleBeanFactory() {
    }

    //getBean,容器的核心方法，从单例bean集合内取出单例bean
    public Object getBean(String beanName) throws BeanException {
        //先尝试拿出bean实例
        Object singleton = this.singletons.get(beanName);
        //如果还没有这个bean的实例，则获取它的定义来创建实例
        if (singletons == null) {
            //获取bean的定义
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (beanDefinition == null) {
                throw new BeanException("No Bean.");
            }
            try {
                //获取bean的定义
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            this.registerSingleton(beanName, singleton);


        }

        return singleton;
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeanException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeBeanDefinition(String name) {
        this.beanDefinitions.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitions.get(name);
    }

    @Override
    public boolean ContainsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitions.containsKey(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitions.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitions.get(name).getClass();
    }


}
