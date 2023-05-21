package com.minis.context;

public interface ApplicationEventPublisher {
    //事件监听
    void publishEvent(ApplicationEvent event);
}
