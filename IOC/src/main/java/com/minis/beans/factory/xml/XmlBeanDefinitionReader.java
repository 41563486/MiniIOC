package com.minis.beans.factory.xml;


import com.minis.beans.*;
import com.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.support.SimpleBeanFactory;
import com.minis.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;


public class XmlBeanDefinitionReader {
//    SimpleBeanFactory bf;
    AutowireCapableBeanFactory bf;
//    public XmlBeanDefinitionReader(SimpleBeanFactory bf) {
//        this.bf = bf;
//    }

    public XmlBeanDefinitionReader(AutowireCapableBeanFactory beanFactory) {
    }

    //加载bean定义
    public void loadBeanDefinitions(Resource res) {
        //Resource类继承了迭代器，通过while循环迭代器的序号。遍历资源包里面的每一个元素节点，并且获取属性值
        while (res.hasNext()) {
            Element element = (Element)res.next();
            String beanID=element.attributeValue("id");
            String beanClassName=element.attributeValue("class");

            //将获取到的id名和class类值赋值到bean定义里面
            BeanDefinition beanDefinition=new BeanDefinition(beanID,beanClassName);

            //get constructor
            //处理构造器参数
            //使用dom4j内置api获取元素节点
            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues AVS = new ConstructorArgumentValues();
            //通过增强for循环遍历需要的属性值
            for (Element e : constructorElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                AVS.addArgumentValue(new ConstructorArgumentValue(pType,pName,pValue));
            }
            //setter方法
            beanDefinition.setConstructorArgumentValues(AVS);
            //end of handle constructor

            //handle properties
            //处理属性
            //增强for遍历属性并增加一个ref属性，并创建一个数组容器存放
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                //判断pValue属性值是否非空，为真将pValue值赋给pV空字符串，判断ref是否为空，状态值转换为真，判断是用ref属性还是value属性，并将ref对象属性值添加进数组容器中
                if (pValue != null && !pValue.equals("")) {
                    isRef = false;
                    pV = pValue;
                } else if (pRef != null && !pRef.equals("")) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                //属性数组，创建一个属性对象
                PVS.addPropertyValue(new PropertyValue(pType, pName, pV, isRef));
            }
            //在bean定义里面将该属性数组更新
            beanDefinition.setPropertyValues(PVS);
            //创建一个长度为0的string数组，并将其赋值给一个String数组
            String[] refArray = refs.toArray(new String[0]);
            //将改bean实例添加依赖
            beanDefinition.setDependsOn(refArray);
            //end of handle properties
            //注册该bean的id和定义
            this.bf.registerBeanDefinition(beanID,beanDefinition);
        }
    }



}
