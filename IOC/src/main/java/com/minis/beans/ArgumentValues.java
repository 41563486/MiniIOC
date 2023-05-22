package com.minis.beans;

import java.util.*;

//参数值内不止一个，所以需要集合类
public class ArgumentValues {
    //定义hashmap装序号和参数值使用final修饰不可修改
    private final Map<Integer, ArgumentValue> indexedArgumentValues = new HashMap<>();
    //
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();

    //private final List<ArgumentValue> argumentValueList=  new ArrayList<ArgumentValue>();

    public ArgumentValues() {
    }

    //添加新参数值并附加key值
    private void addArgumentValue(Integer key, ArgumentValue newValue) {
        this.indexedArgumentValues.put(key, newValue);

    }

    //查询是否有对应序号的参数值
    public boolean hasIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.containsValue(index);

    }
    //获取指定序号的参数值
    public ArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    //添加通用参数值和类型
    public void addGenericArgumentValue(Object value, String type) {
        this.genericArgumentValues.add(new ArgumentValue(value, type));

    }

    //添加新参数值判空（使用迭代器）并覆盖
    private void addGenericArgumentValue(ArgumentValue newValue) {
        if (newValue.getName() != null) {
            for (Iterator<ArgumentValue> it = this.genericArgumentValues.iterator(); it.hasNext(); ) {
                ArgumentValue currentValue = it.next();
                if (newValue.getName().equals(currentValue.getName())) {
                    it.remove();
                }
            }

        }
        this.genericArgumentValues.add(newValue);
    }

    //获取通用参数值的必需名称,使用增强for。判断名称是否统一如果是直接返回name
    public ArgumentValue getGenericArgumentValue(String requiredName) {
        for (ArgumentValue valueHolder : this.genericArgumentValues) {
            if (valueHolder.getName() != null &&
                    (requiredName == null || !valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;

    }

    //获取参数值容器内的长度
    public int getArgumentCount() {
        return this.genericArgumentValues.size();
    }

    //判空
    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();

    }


}
