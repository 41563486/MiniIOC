package com.minis.test;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;


public class Test1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");
        AService aService;
        try {
            aService = (AService)ctx.getBean("aservice");
            aService.sayHello();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

}
