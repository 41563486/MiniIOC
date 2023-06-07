package com.minis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleBeanFactory implements BeanFactory {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    public SimpleBeanFactory() {
    }

    //getBean,容器的核心方法
    public Object getBean(String beanName) throws BeanException {
        //先尝试拿出bean实例
        Object singleton = this.singletons.get(beanName);
        //如果还没有这个bean的实例，则获取它的定义来创建实例
        if (singletons == null) {
            int i = beanNames.indexOf(beanName);
            if (i == -1) {
                throw new BeanException();
            } else {

                try {
                    //获取bean的定义
                    BeanDefinition beanDefinition = beanDefinitions.get(i);
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                    singletons.put(beanDefinition.getId(), singleton);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }


            }
        }
        return singleton;
    }

    @Override
    public Boolean containsBean(String name) {
        return null;
    }

    @Override
    public void registerBean(String beanName, Object obj) {

    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
             this.beanDefinitions.add(beanDefinition);
             this.beanNames.add(beanDefinition.getId());
    }
}
