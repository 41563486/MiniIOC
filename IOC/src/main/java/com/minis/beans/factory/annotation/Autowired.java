package com.minis.beans.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//限制注解的应用范围，只能是字段属性（成员属性）
@Target(ElementType.FIELD)
//在运行是保留，保留进class编译文件，可通过反射机制获取和处理注解
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {

}
