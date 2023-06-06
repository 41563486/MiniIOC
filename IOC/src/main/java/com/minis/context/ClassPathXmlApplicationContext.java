package com.minis.context;

import com.minis.beans.ApplicationEvent;
import com.minis.beans.BeansException;
import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.minis.beans.factory.config.BeanFactoryPostProcessor;

import com.minis.beans.factory.config.BeanPostProcessor;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.beans.factory.support.DefaultListableBeanFactory;

import com.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;
import com.minis.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    DefaultListableBeanFactory beanFactory;

    //    SimpleBeanFactory beanFactory;
//    AbstractAutowireCapableBeanFactory beanFactory;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

//    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors =
//            new ArrayList<BeanFactoryPostProcessor>();
//    private String beanName;
//    private Object obj;

    //构造方法
    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    //加载xml，是否需要在加载xml时一起加载bean实例
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource res = new ClassPathXmlResource(fileName);
        //调用能解释注解的工厂对象

        DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //加载定义
        reader.loadBeanDefinitions(res);
        this.beanFactory = bf;

        //判断是否是需要刷新实例
        if (isRefresh) {
            try {
                refresh();
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

/*
    //
    public void refresh() throws BeansException, IllegalStateException {
        // Register bean processors that intercept bean creation.
        //注册拦截bean的bean处理器
        registerBeanPostProcessors(this.beanFactory);

        //初始化特定上下文子类的其他特殊bean
        // Initialize other special beans in specific context subclasses.
        onRefresh();
    }
*/

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void setEnvironment(Environment environment) {

    }

    @Override
    public Environment getEnvironment() {
        return null;
    }

    //防止循环依赖,先创建的毛胚bean实例
    public void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    protected void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);

    }

    @Override
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());

    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

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


    private void registerBeanPostProcessors(AbstractAutowireCapableBeanFactory bf) {
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

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    //发布事件
    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
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

    @Override
    protected void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed...."));

    }


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {

    }

    @Override
    public int getBeanPostProcessorCount() {
        return 0;
    }

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


    @Override
    public int getBeanDefinitionCount() {
        return 0;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return new String[0];
    }

    @Override
    public <T> Map<String, T> getBeanOfType(Class<?> type) throws BeansException {
        return null;
    }
}
