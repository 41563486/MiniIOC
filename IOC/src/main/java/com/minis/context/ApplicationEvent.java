package com.minis.context;

import java.util.EventObject;

//应用程序消息
public class ApplicationEvent extends EventObject {
    //序列化版本号
    private static final long serialVersionUID = 1L;
    //消息变量
    protected String msg = null;


    //应用程序事件
    public ApplicationEvent(Object arg0) {
        super(arg0);
        this.msg = arg0.toString();
    }
}
