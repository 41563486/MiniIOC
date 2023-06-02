package com.minis.beans.factory.annotation;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.minis.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            //对每一个属性进行判断，如果有带有@Autowired注解进行处理
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    //根据属性名查找同名的bean
                    String fieldName = field.getName();
                    Object autowireObj = this.getBeanFactory().getBean(fieldName);
                    //设置属性值，完成注入
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowireObj);
                        System.out.println("autowire" + fieldName + "for bean" + beanName);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }


            }
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
