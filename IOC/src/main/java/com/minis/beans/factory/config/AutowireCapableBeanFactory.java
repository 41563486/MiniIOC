package com.minis.beans.factory.config;

import com.minis.beans.BeansException;
import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

//继承抽象bean工厂,可以替代simple工厂来处理bean的实例，@Autowired 注解的解释实现。
public class AutowireCapableBeanFactory extends AbstractBeanFactory {
    //注解处理器对象池
    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<AutowiredAnnotationBeanPostProcessor>();

    //添加处理器对象
    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    //获取处理器对象数
    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    //获取处理器对象集合
    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    //应用初始化前方法
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException {

        //已存在的bean对象
        Object result = existingBean;
        //遍历处理器对象
        for (AutowiredAnnotationBeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            //将处理器对象添加为AutowiredAnnotationBeanPostProcessor的BeanFactory对象
            beanProcessor.setBeanFactory(this);
            //将最终bean对象，和bean别名传入注解处理类，处理@Autowired注解
            result = beanProcessor.postProcessBeforeInitialization(result, beanName);
            if (result == null) {
                return null;
            }
        }
        //返回结果
        return result;
    }

    //应用初始化后方法
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException {

        Object result = existingBean;
        //遍历处理器
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            //传入bean和bean别名给初始化后方法，处理@Autowired注解
            result = beanProcessor.postProcessAfterInitialization(result, beanName);
            if (result == null) {
                return null;
            }
        }
        return result;
    }


}
