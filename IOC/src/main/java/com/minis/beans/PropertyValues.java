package com.minis.beans;

import java.util.ArrayList;
import java.util.List;

//xml中property标签的属性集合类
public class PropertyValues {
    //使用final修饰的List容器
    private final List<PropertyValue> propertyValueList;

    //用ArrayList再装list
    public PropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    //获取list
    public List<PropertyValue> getPropertyValueList() {
        return this.propertyValueList;

    }

    //判断list的长度
    public int size() {
        return this.propertyValueList.size();

    }

    //添加属性值
    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    //添加属性值
    public void addPropertyValue(String propertyName, Object propertyValue) {
        addPropertyValue(new PropertyValue(propertyName, propertyValue));
    }



    //删除属性值
    public void removePropertyValue(String propertyName) {
        this.propertyValueList.remove(getPropertyValue(propertyName));
    }
    //获取多个属性值返回一个数组
    public PropertyValue[] getPropertyValues(String propertyName){
        return this.propertyValueList.toArray(new PropertyValue[this.propertyValueList.size()]);
    }

    //获取属性值列表内的属性
    public   PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv:this.propertyValueList){
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
    return null;
    }
    //获取属性值value 并判空
    public  Object get(String propertyName){
        PropertyValue pv=getPropertyValue(propertyName);
        return pv!=null?pv.getValue():null;

    }
    //判断是否含有属性名
    public boolean contains(String propertyName){
        return getPropertyValue(propertyName)!=null;
    }
    //判断集合是否为空
    public boolean isEmpty(){
        return this.propertyValueList.isEmpty();
    }


}
