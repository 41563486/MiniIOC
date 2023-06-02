package com.minis.beans.factory.config;

//scope属性是判断该bean是什么模式（单例或者原型模式）
// lazyInit表示加载时是否需要初始化,initMethodName为初始化话方法的名称
//constructorArgumentValues构造器参数
//dependsOn表示bean与bean间的依赖关系
//beanClass  bean对应的映射类

import com.minis.beans.PropertyValues;

public class BeanDefinition {
    private String id;
    private String className;

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    private boolean lazyInit = true;
    private String[] dependsOn;
    private ConstructorArgumentValues constructorArgumentValues;

    private PropertyValues propertyValues;
    private String initMethodName;

    //volatile保证可见性，该私有变量更新时会立刻更新到内存中，禁止指令重排序（位于修饰词后面的语句）
    private volatile Object beanClass;

    private String scope=SCOPE_SINGLETON;





    public BeanDefinition() {
    }

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public ConstructorArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    public void setConstructorArgumentValues(ConstructorArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }
    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }


    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public Object getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Object beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }
}

