package com.minis.beans;

//事件发布类。如果需要发布事件需要实现这个类
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
