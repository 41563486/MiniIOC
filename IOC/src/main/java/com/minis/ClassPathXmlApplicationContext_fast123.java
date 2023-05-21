package com.minis;

import com.minis.beans.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClassPathXmlApplicationContext_fast123 {
    private List<BeanDefinition>  beanDefinitions=new ArrayList<>();
    private Map<String, Object> singletons=new HashMap<>();
    //构造器获取外部配置，解析bean的定义，形成内存映像


    public ClassPathXmlApplicationContext_fast123(String fileName) {
            this.readXml(fileName);
            this.instanceBeans();
    }

    private void readXml(String fileName) {
        SAXReader saxReader=new SAXReader();

        try {
            URL xmlPath=this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(fileName);
            Element rootElement = document.getRootElement();
            //对配置文件的每一个bean进行处理
            for (Element element:(List<Element>) rootElement.elements() ){
                //获取bean的基本信息
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanID,beanClassName);
                //将bean的定义存放到beanDefinitions中
                beanDefinitions.add(beanDefinition);
            }


        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

    }
        //利用反射原理创建bean实例，并储存到singletons中
    private void instanceBeans() {

        for (BeanDefinition beanDefinition:beanDefinitions){
            try {
                singletons.put(beanDefinition.getId(),Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (InstantiationException e) {


            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
    //设置一个对外的方法，让外部获取bean实例，逐渐演变为核心方法
    public Object getBean(String beanName){
        return  singletons.get(beanName);
    }

}
