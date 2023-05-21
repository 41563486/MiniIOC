package com.minis.beans;


import com.minis.core.Resource;
import org.dom4j.Element;

public class XmlBeanDefinitionReader {
    BeanFactory beanFactory;
    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void LoadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
//            this.simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
