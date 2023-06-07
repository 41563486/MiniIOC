package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.BeanPostProcessor;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


//ListableBeanFactory的默认实现
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {

    //获取bean定义的数量
    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    //获取bean定义别名数组
    @Override
    public String[] getBeanDefinitionNames() {
        return (String[]) this.beanDefinitionNames.toArray();
    }

    //通过类型获取bean别名数组
    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        //遍历bean定义别名,定义一个状态值接收是否有匹配条件
        for (String beanName : this.beanDefinitionNames) {
            boolean matchFound = false;
            BeanDefinition mbd = this.getBeanDefinition(beanName);
            //匹配bean的Class的类型
            Class<?> classToMatch = mbd.getClass();
            //isAssignableFrom判断一个类是否可以赋值给另外一个类，检查类之间的继承关系和接口之间的实现关系,如果是则返回真
            if (type.isAssignableFrom(classToMatch)) {
                matchFound = true;

            } else {
                matchFound = false;

            }
            if (matchFound) {
                result.add(beanName);

            }
        }
        return (String[]) result.toArray();
    }


    //抑制编译器报警,处理泛型类
    @SuppressWarnings("unchecked")
    //通过类型获取此类型bean的集合
    @Override
    public <T> Map<String, T> getBeanOfType(Class<?> type) throws BeansException {
        //通过此类型虎丘bean别名
        String[] beanNames = getBeanNamesForType(type);
        //用一个和bean别名数组长度相等的数组接收，用来存放bean别名和bean实例
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        //遍历bean别名数组,并创建bean实例
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);

        }
        return result;
    }

    //添加扩展处理器
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {

    }

    //注册bean依赖关系
    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {

    }


    @Override
    public String[] getDependentBeans(String beanName) {
        return new String[0];
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return new String[0];
    }
}
