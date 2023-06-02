package com.minis.beans.factory.config;

import java.util.*;

public class ConstructorArgumentValues {
    //    private  final Map<Integer,ArgumentValue> indexedArgumentValues=new HashMap<>(0);
//    private  final List<ArgumentValue> genericArgumentValues=new LinkedList<>();

    //该类不会被继承，且这个数组必须初始化
    private final List<ConstructorArgumentValue> constructorArgumentValueList = new ArrayList<>();

    //构造函数
    public ConstructorArgumentValues() {
    }

    //添加新的参数值，用arraylist内置api
    public void addArgumentValue(ConstructorArgumentValue newValue) {
        this.constructorArgumentValueList.add(newValue);

    }

    //    public boolean hasIndexedArgumentValue(int index){
//        return this.argumentValueList.containsValue(index);
//
//    }


    //通过索引值获取参数值
    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        ConstructorArgumentValue constructorArgumentValue = this.constructorArgumentValueList.get(index);
        return constructorArgumentValue;
    }

//    public void addGenericArgumentValue(Object value, String type) {
//        this.argumentValueList.add(new ArgumentValue(value, type));
//
//    }

    //    private void  addGenericArgumentValue(ArgumentValue newValue){
//        if (newValue.getName()!=null){
//            for (Iterator<ArgumentValue> it=this.argumentValueList.iterator(); it.hasNext();){
//                ArgumentValue currentValue=it.next();
//                if (newValue.getName().equals(currentValue.getName())){
//                    it.remove();
//                }
//            }
//
//        }
//        this.genericArgumentValues.add(newValue);
//    }
//    public ArgumentValue getGenericArgumentValue(String requiredName){
//        for (ArgumentValue valueHolder:this.genericArgumentValues){
//            if (valueHolder.getName() !=null &&
//                    (requiredName==null|| !valueHolder.getName().equals(requiredName))){
//                continue;
//            }
//            return valueHolder;
//        }
//        return null;
//
//    }

    //获取参数值数组的大小
    public int getArgumentCount() {
        return this.constructorArgumentValueList.size();
    }

    //判断参数值数组是否为空
    public boolean isEmpty() {
        return this.constructorArgumentValueList.isEmpty();

    }


}
