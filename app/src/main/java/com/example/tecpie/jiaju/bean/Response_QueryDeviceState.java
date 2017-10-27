package com.example.tecpie.jiaju.bean;

import java.util.List;

/**
 * Created by HuaJian on 2017/6/28.
 */
public class Response_QueryDeviceState {
    private boolean result;
    private Bean_state state;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Bean_state getState() {
        return state;
    }

    public void setState(Bean_state state) {
        this.state = state;
    }
}
