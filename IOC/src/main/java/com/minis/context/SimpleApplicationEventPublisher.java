package com.minis.context;

import com.minis.beans.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;
//简单的事件发布者
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {
    //监听者
    List<ApplicationListener> listeners=new ArrayList<>();

    //事件发布
    @Override
    public void publishEvent(ApplicationEvent event) {
        //遍历监听者,给监听者添加正在工作的事件
        for (ApplicationListener listener:listeners){
            listener.onApplicationEvent(event);
        }

    }

    //添加应用程序监听者
    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }
}
