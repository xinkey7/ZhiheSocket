package com.example.tecpie.jiaju.pojo;

/**
 * 发送电量数据请求
 */
public class PostEqInfoBean {
    private String id;
    private int endpointid;
    private String date;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }


    public int getEndpointid() {
        return endpointid;
    }

    public void setEndpointid(int endpointid) {
        this.endpointid = endpointid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

