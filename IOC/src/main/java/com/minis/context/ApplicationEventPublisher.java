package com.minis.context;

import com.minis.beans.ApplicationEvent;

public interface ApplicationEventPublisher {
    //事件监听
    void publishEvent(ApplicationEvent event);
    void addApplicationListener(ApplicationListener listener);

}
