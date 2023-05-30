package com.minis.beans;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
//默认注册单例bean
public class DefaultSingletonBeanRegistry  implements SingletonBeanRegistry {
    //存放bean别名集合
    protected List<String> beanNames=new ArrayList<>();
    //单例池
    protected Map<String, Object> singletonObjects =new ConcurrentHashMap<>(256);
    //存放被依赖bean
    protected Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);
    //存放依赖bean
    protected Map<String,Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

    //注册bean实例
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized(this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            //判断实例是否已存在
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }

            this.singletonObjects.put(beanName, singletonObject);
            this.beanNames.add(beanName);
            System.out.println(" bean registerded............. " + beanName);
        }
    }

    //通过bean别名获取bean类
    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    //通过别名判断bean的实例是否存在
    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }

    //获取bean实例别名的数组
    @Override
    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }
    //注册bean与bean之间依赖关系
    protected void registerDependentBean(String beanName, String dependentBeanName) {
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        //判断该依赖是否已经存在
        if (dependentBeans != null && dependentBeans.contains(dependentBeanName)) {
            return;
        }

        // No entry yet -> fully synchronized manipulation of the dependentBeans Set
        synchronized (this.dependentBeanMap) {
            dependentBeans = this.dependentBeanMap.get(beanName);
            if (dependentBeans == null) {
                dependentBeans = new LinkedHashSet<String>(8);
                this.dependentBeanMap.put(beanName, dependentBeans);
            }
            dependentBeans.add(dependentBeanName);
        }
        //讲依赖bean存入dependenciesForBeanMap池内
        synchronized (this.dependenciesForBeanMap) {
            Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(dependentBeanName);
            if (dependenciesForBean == null) {
                dependenciesForBean = new LinkedHashSet<String>(8);
                this.dependenciesForBeanMap.put(dependentBeanName, dependenciesForBean);
            }
            dependenciesForBean.add(beanName);
        }

    }

    //判断是否有被依赖bean
    protected boolean hasDependentBean(String beanName) {
        return this.dependentBeanMap.containsKey(beanName);
    }
    //获取所有被依赖bean
    protected String[] getDependentBeans(String beanName) {
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        if (dependentBeans == null) {
            //返回一个新的空字符串数组表示没有此bean
            return new String[0];
        }
        //返回一个字符串数组包含dependentBeans中的元素
        return (String[]) dependentBeans.toArray();
    }
    //获取所有依赖bean
    protected String[] getDependenciesForBean(String beanName) {
        Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(beanName);
        if (dependenciesForBean == null) {
            //返回一个新的空字符串数组表示没有此bean
            return new String[0];
        }
        //返回一个字符串数组包含dependenciesForBean中的元素
        return (String[]) dependenciesForBean.toArray();

    }
}