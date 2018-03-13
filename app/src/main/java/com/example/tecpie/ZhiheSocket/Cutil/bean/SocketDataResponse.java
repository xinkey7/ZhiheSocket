package com.example.tecpie.ZhiheSocket.Cutil.bean;

/**
 * Created by xsy35 on 2018/3/11.
 */

public class SocketDataResponse {
    private String code;
    private Integer online;
    private Double vol;
    private Double cur;
    private Double power;
    private Double consum;
    private Integer onoff;
    private Integer result;
    private String ieee;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Double getVol() {
        return vol;
    }

    public void setVol(Double vol) {
        this.vol = vol;
    }

    public Double getCur() {
        return cur;
    }

    public void setCur(Double cur) {
        this.cur = cur;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getConsum() {
        return consum;
    }

    public void setConsum(Double consum) {
        this.consum = consum;
    }

    public Integer getOnoff() {
        return onoff;
    }

    public void setOnoff(Integer onoff) {
        this.onoff = onoff;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getIeee() {
        return ieee;
    }

    public void setIeee(String ieee) {
        this.ieee = ieee;
    }
}
