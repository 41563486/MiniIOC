package com.minis.beans;

import java.util.*;

public class ArgumentValues {
    //    private  final Map<Integer,ArgumentValue> indexedArgumentValues=new HashMap<>(0);
//    private  final List<ArgumentValue> genericArgumentValues=new LinkedList<>();
    private final List<ArgumentValue> argumentValueList = new ArrayList<ArgumentValue>();

    public ArgumentValues() {
    }

    public void addArgumentValue( ArgumentValue newValue) {
        this.argumentValueList.add( newValue);

    }

    //    public boolean hasIndexedArgumentValue(int index){
//        return this.argumentValueList.containsValue(index);
//
//    }
    public ArgumentValue getIndexedArgumentValue(int index) {
        ArgumentValue argumentValue = this.argumentValueList.get(index);
        return argumentValue;
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
    public int getArgumentCount() {
        return this.argumentValueList.size();
    }

    public boolean isEmpty() {
        return this.argumentValueList.isEmpty();

    }


}
