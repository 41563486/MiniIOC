package com.minis.context;

import com.minis.beans.ApplicationEvent;

//应用程序的刷新事件
public class ContextRefreshEvent extends ApplicationEvent {
    //序列化版本号
    private static final long serialVersionUID = 1L;

    public ContextRefreshEvent(Object arg0) {
        super(arg0);
    }
    //转为字符串
    public String toString() {
        return this.msg;
    }
}
