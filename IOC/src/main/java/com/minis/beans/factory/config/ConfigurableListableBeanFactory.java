package com.minis.beans.factory.config;

import com.minis.beans.factory.ListableBeanFactory;

//支持注册扩展处理器、自定义作用域、管理别名。更加灵活管理和修改bean
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

}
