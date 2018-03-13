package com.example.tecpie.ZhiheSocket.bean;

/**
 * Created by xsy35 on 2017/12/20.
 */

public  class MessageShowBean {
    private String name;
    private String period;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "MessageShowBean{" +
                "name='" + name + '\'' +
                ", period='" + period + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
