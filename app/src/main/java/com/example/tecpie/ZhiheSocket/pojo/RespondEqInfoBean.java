package com.example.tecpie.ZhiheSocket.pojo;

/**
 * 接收电量数据请求结果
 */
public class RespondEqInfoBean {
    private boolean result;

    private RespondEqInfoValuesBean values ;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public RespondEqInfoValuesBean getValues() {
        return values;
    }

    public void setValues(RespondEqInfoValuesBean values) {
        this.values = values;
    }
}