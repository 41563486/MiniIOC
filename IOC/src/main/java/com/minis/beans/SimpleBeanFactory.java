package com.minis.beans;

import java.lang.reflect.InvocationTargetException;
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
        //方法兼顾判空拿取和，创建实例
        //如果还没有这个bean的实例，则获取它的定义来创建实例
        if (singletons == null) {
            //获取bean的定义
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (beanDefinition == null) {
                throw new BeanException("No Bean.");
            }
            try {
                //获取bean的定义,通过java反射加载类，后面跟的方法是如果类没有构造方法，则创建一个构造方法
                singleton = Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            //新注册这个bean实例
            this.registerSingleton(beanName, singleton);


        }
        //返回一个单例bean实例
        return singleton;
    }

    //注册bean定义
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
//        this.beanDefinitionNames.add(name);
//        if (!beanDefinition.isLazyInit()) {
//            try {
//                getBean(name);
//            } catch (BeanException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    //移除bean定义
    public void removeBeanDefinition(String name) {
        this.beanDefinitions.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }
    //获取bean定义
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitions.get(name);
    }


    //重写判断是否有单例bean实例
    @Override
    public boolean ContainsBean(String name) {
        return containsSingleton(name);
    }

    //重写注册单例bean实例
    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    //重写判断是否有单例bean实例
    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitions.containsKey(name);
    }
    //重写判断是否是设置的属性
    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitions.get(name).isPrototype();
    }

    //重写判断bean的类型
    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitions.get(name).getClass();
    }


}
