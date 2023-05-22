package com.minis.beans;

import java.util.EventObject;

//提供观察者模式的入口，事件监听
public class ApplicationEvent extends EventObject {
    //序列化标识符
    private static final long serialVersionUID=1L;


    public ApplicationEvent(Object arg0) {
        super(arg0);
    }
}
