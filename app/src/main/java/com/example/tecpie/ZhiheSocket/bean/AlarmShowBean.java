package com.example.tecpie.ZhiheSocket.bean;

/**
 * Created by xsy35 on 2017/12/20.
 */

public class AlarmShowBean {
    private String time;
    private String name;
    private String groupName;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "AlarmShowBean{" +
                "time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
