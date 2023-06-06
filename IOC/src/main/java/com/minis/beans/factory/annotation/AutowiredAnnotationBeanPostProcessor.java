package com.minis.beans.factory.annotation;

import com.minis.beans.BeansException;
import com.minis.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.minis.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
//实现初始化接口
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
	//存放AutowireCapableBeanFactory的实例化对象
	private AbstractAutowireCapableBeanFactory beanFactory;

	//在初始化之前的方法
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Object result = bean;
		//获取bean的类对象
		Class<?> clazz = result.getClass();
		//获取类的声明的所有字段
		Field[] fields = clazz.getDeclaredFields();
		if(fields!=null){
			//对每一个属性进行判断，如果带有@Autowired注解则进行处理
			for(Field field : fields){
				//检查是否有Autowired注解
				boolean isAutowired = field.isAnnotationPresent(Autowired.class);
				if(isAutowired){
					//根据属性名查找相同名的bean
					String fieldName = field.getName();
					//获取有字此段名的bean
					Object autowiredObj = this.getBeanFactory().getBean(fieldName);
					try {
						//修改成员变量（字段）的可访问性，设置true可用来访问private。
						field.setAccessible(true);
						//为字段设置bean类和拥有改字段类的名称
						field.set(bean, autowiredObj);
						System.out.println("autowire " + fieldName + " for bean " + beanName);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

				}
			}
		}
		//返回的bean类
		return result;
	}

	//初始化之后的方法
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	//bean工厂对象的getter,获取bean工厂对象
	public AbstractAutowireCapableBeanFactory getBeanFactory() {
		return beanFactory;
	}

	//bean工厂对象setter方法
	public void setBeanFactory(AbstractAutowireCapableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

}
