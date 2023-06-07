package com.minis.context;

import com.minis.beans.ApplicationEvent;

import java.util.EventListener;

public class ApplicationListener implements EventListener {
    //处于监听状态
    void onApplicationEvent(ApplicationEvent event){
        System.out.println(event.toString());
    }
}
