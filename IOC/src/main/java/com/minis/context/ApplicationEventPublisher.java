package com.minis.context;

import com.minis.beans.ApplicationEvent;

public interface ApplicationEventPublisher {
    //事件发布
    void publishEvent(ApplicationEvent event);
    //添加应用程序监听器
    void addApplicationListener(ApplicationListener listener);

}
