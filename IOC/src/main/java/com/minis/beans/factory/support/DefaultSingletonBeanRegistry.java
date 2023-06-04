package com.minis.beans.factory.support;

import com.minis.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//默认单例bean实例注册实现单例bean实例注册接口
public class DefaultSingletonBeanRegistry  implements SingletonBeanRegistry {
	//bean别名池
    protected List<String> beanNames=new ArrayList<>();
	//bean实例对象池
    protected Map<String, Object> singletonObjects =new ConcurrentHashMap<>(256);
	//bean的被依赖关系池
    protected Map<String,Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);
   //bean的依赖关系池
    protected Map<String,Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

	//注册bean实例
	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		synchronized(this.singletonObjects) {
			Object oldObject = this.singletonObjects.get(beanName);
			if (oldObject != null) {
				throw new IllegalStateException("Could not register object [" + singletonObject +
						"] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
			}
			
	    	this.singletonObjects.put(beanName, singletonObject);
	    	this.beanNames.add(beanName);
	    	System.out.println(" bean registerded............. " + beanName);
		}
	}

	//获取bean实例
	@Override
	public Object getSingleton(String beanName) {
		return this.singletonObjects.get(beanName);
	}

	//包含此bean别名的实例
	@Override
	public boolean containsSingleton(String beanName) {
		return this.singletonObjects.containsKey(beanName);
	}

	//获取bean实例的别名
	@Override
	public String[] getSingletonNames() {
		return (String[]) this.beanNames.toArray();
	}

	//移除bean实例
	protected void removeSingleton(String beanName) {
	    synchronized (this.singletonObjects) {
		    this.singletonObjects.remove(beanName);
		    this.beanNames.remove(beanName);
	    }
	}

	//注册依赖bean
	protected void registerDependentBean(String beanName, String dependentBeanName) {
		//将bean别名放进bean依赖池
		Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
		//判空
		if (dependentBeans != null && dependentBeans.contains(dependentBeanName)) {
			return;
		}

		//同步添加进依赖池,保持依赖与被依赖的bean数据的一致性
		// No entry yet -> fully synchronized manipulation of the dependentBeans Set
		synchronized (this.dependentBeanMap) {

			dependentBeans = this.dependentBeanMap.get(beanName);
			//判空，如果为真则创建一个linked集合容器盛放
			if (dependentBeans == null) {
				dependentBeans = new LinkedHashSet<String>(8);
				this.dependentBeanMap.put(beanName, dependentBeans);
			}
			dependentBeans.add(dependentBeanName);
		}
		//同步插入依赖bean,与被依赖的bean创建一致,保证数据一致和线程同步
		synchronized (this.dependenciesForBeanMap) {
			Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(dependentBeanName);
			if (dependenciesForBean == null) {
				dependenciesForBean = new LinkedHashSet<String>(8);
				this.dependenciesForBeanMap.put(dependentBeanName, dependenciesForBean);
			}
			dependenciesForBean.add(beanName);
		}

	}

	//判断bean的依赖关系
	protected boolean hasDependentBean(String beanName) {
		return this.dependentBeanMap.containsKey(beanName);
	}


	//通过bean别名获取被依赖bean
	protected String[] getDependentBeans(String beanName) {
		Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
		if (dependentBeans == null) {
			//返回空数组避免空指针异常,可以安全的添加删除依赖关系
			return new String[0];
		}
		return (String[]) dependentBeans.toArray();
	}
	//通过bean别名获取依赖bean
	protected String[] getDependenciesForBean(String beanName) {
		Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(beanName);
		if (dependenciesForBean == null) {
			//返回空数组避免空指针异常,可以安全的添加删除依赖关系
			return new String[0];
		}
		return (String[]) dependenciesForBean.toArray();

	}
}
