package com.example.tecpie.ZhiheSocket.pojo;

/**
 * 发送电量数据请求
 */
public class ControlBean {
    private String id;
   private Control control;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }
}

