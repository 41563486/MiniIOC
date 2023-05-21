package com.minis.test;

import com.minis.ClassPathXmlApplicationContext_fast;
import com.minis.beans.BeanException;
import com.minis.context.ClassPathXmlApplicationContext;

public class Test1 {

    public static void main(String[] args) throws BeanException {
        ClassPathXmlApplicationContext_fast ctx
                = new ClassPathXmlApplicationContext_fast("bean.xml");

        AService service = (AService) ctx.getBean("aService");
//        service.SayHello();

    }

}