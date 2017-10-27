package com.example.tecpie.jiaju.pojo;

/**
 * Created by xsy35 on 2017/8/23.
 */

import com.example.tecpie.jiaju.bean.Bean_device;

/**
 * 发送设备查询请求
 */

public class PostQueryDeviceBean {
    private Bean_device[] devices;

    public Bean_device[] getDevices() {
        return devices;
    }

    public void setDevices(Bean_device[] devices) {
        this.devices = devices;
    }
}

