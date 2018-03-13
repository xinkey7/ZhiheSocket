package com.example.tecpie.ZhiheSocket.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xsy35 on 2018/3/6.
 */

public class NetEntity {
    private String wifi;
    private String name;
    private String mac;
    private String panid;
    private String gateway;
    private String profile;
    private String channel;
    private Integer isOk;
    private List<SocketEntity> sockets;

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public List<SocketEntity> getSockets() {
        return sockets;
    }

    public void setSockets(List<SocketEntity> sockets) {
        this.sockets = sockets;
    }

    public Integer getIsOk() {
        return isOk;
    }

    public void setIsOk(Integer isOk) {
        this.isOk = isOk;
    }

    public String getPanid() {
        return panid;
    }

    public void setPanid(String panid) {
        this.panid = panid;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "NetEntity{" +
                "wifi='" + wifi + '\'' +
                ", name='" + name + '\'' +
                ", mac='" + mac + '\'' +
                ", panid='" + panid + '\'' +
                ", gateway='" + gateway + '\'' +
                ", profile='" + profile + '\'' +
                ", channel='" + channel + '\'' +
                ", isOk=" + isOk +
                ", sockets=" + sockets +
                '}';
    }
}
