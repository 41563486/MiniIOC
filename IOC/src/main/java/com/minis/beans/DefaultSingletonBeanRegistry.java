package com.minis.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    //容器中存放所有bean的名称的列表
    protected List<String> beanNames = new ArrayList<>();
    //实例中存放所有bean实例的map
    //ConcurrentHashMap允许多个线程同时读写，锁的粒度较小。不会锁住整个线程，使用了锁分段技术。内部的值是设定表的大小而不会动态调整
    protected Map<String, Object> singletons = new ConcurrentHashMap<>(256);

    //synchronized保护线程安全，阻塞其他访问的线程
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons) {
            this.singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);

        }

    }

    //获取bean单例方法
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    //判断是否有bean单例
    public boolean containsSingleton(String beanName) {

        return this.singletons.containsKey(beanName);
    }

    //获取bean集合内部bean
    public String[] getSingletonNames() {
        //将ArrayList转换成数组再强制转换成String数组
        return (String[]) this.beanNames.toArray();
    }
    //删除bean单例，使用synchronize保证线程的安全性
    public void removeSingleton(String beanName) {
        synchronized (this.singletons) {
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);

        }
    }

}
