package com.example.tecpie.ZhiheSocket.entity;

/**
 * Created by xsy35 on 2018/3/6.
 */

public class SocketEntity {
    private int id;
    private String name;
    private String serialNumber;
    private String type;
    private String mac;
    private int state;
    private String power;
    private String eq;
    private String voltage;
    private String current;
    private String gateway;
    private String data;
    private String panid;
    private String profile;
    private String channel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getEq() {
        return eq;
    }

    public void setEq(String eq) {
        this.eq = eq;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPanid() {
        return panid;
    }

    public void setPanid(String panid) {
        this.panid = panid;
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
        return "SocketEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", type='" + type + '\'' +
                ", mac='" + mac + '\'' +
                ", state=" + state +
                ", power='" + power + '\'' +
                ", eq='" + eq + '\'' +
                ", voltage='" + voltage + '\'' +
                ", current='" + current + '\'' +
                ", gateway='" + gateway + '\'' +
                ", data='" + data + '\'' +
                ", panid='" + panid + '\'' +
                ", profile='" + profile + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
