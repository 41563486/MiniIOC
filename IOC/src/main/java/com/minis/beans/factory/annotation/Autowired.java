package com.minis.beans.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//声明一个类的字段
@Target(ElementType.FIELD)
//注解不止保存在class文件在，在jvm运行class之后依然存在
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {

}
