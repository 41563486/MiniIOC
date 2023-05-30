package com.minis.context;

import com.minis.beans.*;

import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;

public class ClassPathXmlApplicationContext implements BeanFactory,ApplicationEventPublisher{

    SimpleBeanFactory beanFactory;

    //构造方法
    public ClassPathXmlApplicationContext(String fileName){
        this(fileName, true);
    }

    //加载xml，是否需要在加载xml时一起加载bean实例
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
        Resource res = new ClassPathXmlResource(fileName);
        SimpleBeanFactory bf = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions(res);
        this.beanFactory = bf;

        if (isRefresh) {
            this.beanFactory.refresh();
        }
    }

    //通过bean别名获取bean实例
    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    //是否包含此bean
    @Override
    public boolean ContainsBean(String name) {
        return this.beanFactory.ContainsBean(name);
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


    //判断当前对象是另一个对象的原型
    @Override
    public boolean isPrototype(String name) {
        //生成方法的存根
        // TODO Auto-generated method stub
        return false;
    }


    //获取类的类型
    @Override
    public Class<?> getType(String name) {
        //生成方法的存根
        // TODO Auto-generated method stub
        return null;
    }

}
