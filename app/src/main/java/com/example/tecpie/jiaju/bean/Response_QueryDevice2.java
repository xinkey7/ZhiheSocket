package com.example.tecpie.jiaju.bean;

import java.util.List;

/**
 * Created by HuaJian on 2017/6/28.
 */
public class Response_QueryDevice2 {
    private boolean result;
    private List<Bean_device2> devices;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<Bean_device2> getDevices() {
        return devices;
    }

    public void setDevices(List<Bean_device2> devices) {
        this.devices = devices;
    }
}
