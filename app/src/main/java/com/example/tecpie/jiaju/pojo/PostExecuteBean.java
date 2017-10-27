package com.example.tecpie.jiaju.pojo;

import java.util.List;

/**
 * 发送空调控制请求
 */
public class PostExecuteBean {
    private long time;

    private List<PostExcuteDevicesBean> devices ;

    public void setTime(long time){
        this.time = time;
    }
    public long getTime(){
        return this.time;
    }
    public void setDevices(List<PostExcuteDevicesBean> devices){
        this.devices = devices;
    }
    public List<PostExcuteDevicesBean> getDevices(){
        return this.devices;
    }

}