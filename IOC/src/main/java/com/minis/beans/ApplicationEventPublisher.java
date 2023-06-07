package com.minis.beans;

public interface ApplicationEventPublisher {
    //事件监听
    void publishEvent(ApplicationEvent event);
}
