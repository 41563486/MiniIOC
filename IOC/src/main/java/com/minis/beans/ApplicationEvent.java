package com.minis.beans;

import java.util.EventObject;

public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    protected String msg = null;


    public ApplicationEvent(Object arg0) {
        super(arg0);
        this.msg=arg0.toString();
    }
}
