package com.example.tecpie.ZhiheSocket.bean;

import java.util.List;

/**
 * Created by HuaJian on 2017/6/28.
 */
public class Response_QueryDevice {
    private boolean result;
    private List<Bean_device> devices;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<Bean_device> getDevices() {
        return devices;
    }

    public void setDevices(List<Bean_device> devices) {
        this.devices = devices;
    }
}
