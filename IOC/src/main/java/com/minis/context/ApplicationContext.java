package com.minis.context;

import com.minis.beans.ApplicationEventPublisher;
import com.minis.beans.BeansException;
import com.minis.beans.factory.ListableBeanFactory;
import com.minis.beans.factory.config.BeanFactoryPostProcessor;
import com.minis.beans.factory.config.ConfigurableBeanFactory;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.core.env.Environment;
import com.minis.core.env.EnvironmentCapable;

//应用程序上下文,管理spring应用程序的配置和组件,访问资源
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {
    //获取应用程序的名称
    String getApplicationName();

    //获取开始日期
    long getStartupDate();


    //获取bean工厂对象
    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    //设置环境对象
    void setEnvironment(Environment environment);

    //获取环境对象
    Environment getEnvironment();

    //添加bean工厂扩展处理器
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    //刷新
    void refresh() throws BeansException, IllegalStateException;

    //关闭
    void close();

    //判断是否活动
    boolean isActive();
}
