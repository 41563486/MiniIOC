package com.minis.context;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.minis.beans.factory.config.BeanFactoryPostProcessor;

import com.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;

import java.util.ArrayList;
import java.util.List;

public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    //    SimpleBeanFactory beanFactory;
    AutowireCapableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors =
            new ArrayList<BeanFactoryPostProcessor>();
    private String beanName;
    private Object obj;

    //构造方法
    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    //加载xml，是否需要在加载xml时一起加载bean实例
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource res = new ClassPathXmlResource(fileName);
        //调用能解释注解的工厂对象
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //加载定义
        reader.loadBeanDefinitions(res);
        this.beanFactory = beanFactory;

        //判断是否是需要刷新实例
        if (isRefresh) {
            try {
                refresh();
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //
    public void refresh() throws BeansException, IllegalStateException {
        // Register bean processors that intercept bean creation.
        //注册拦截bean的bean处理器
        registerBeanPostProcessors(this.beanFactory);

        //初始化特定上下文子类的其他特殊bean
        // Initialize other special beans in specific context subclasses.
        onRefresh();
    }

    //防止循环依赖,先创建的毛胚bean实例
    private void onRefresh() {
        this.beanFactory.refresh();
    }

    //通过bean别名获取bean实例
    @Override
    public Object getBean(String beanName) throws BeansException {
        try {
            return this.beanFactory.getBean(beanName);
        } catch (com.minis.beans.BeansException e) {
            throw new RuntimeException(e);
        }
    }

    //判断是否包含此名字的bean
    @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

 /*   @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }
*/
    private void registerBeanPostProcessors(AutowireCapableBeanFactory bf) {
        //if (supportAutowire) {
        bf.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
        //}
    }





    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }


    //注册bean
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    //发布事件
    @Override
    public void publishEvent(ApplicationEvent event) {
    }


    //是否是实例
    @Override
    public boolean isSingleton(String name) {
        // TODO Auto-generated method stub
        return false;
    }


    //判断是否是原型对象
    @Override
    public boolean isPrototype(String name) {
        //生成方法的存根
        // TODO Auto-generated method stub
        return false;
    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return beanFactoryPostProcessors;
    }

    //获取类的类型
    @Override
    public Class<?> getType(String name) {
        //生成方法的存根
        // TODO Auto-generated method stub
        return null;
    }

}
