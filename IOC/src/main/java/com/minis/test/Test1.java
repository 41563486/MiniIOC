package com.minis.test;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;

public class Test1 {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		AService aService;
		BaseService bService;
		//aService = (AService)ctx.getBean("aservice");
		//aService.sayHello();

		try {
			bService = (BaseService)ctx.getBean("baseservice");
		} catch (BeansException e) {
			throw new RuntimeException(e);
		}
		bService.sayHello();
	}

}
