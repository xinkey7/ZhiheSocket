package com.example.tecpie.ZhiheSocket.bean;

/**
 * Created by HuaJian on 2017/6/28.
 */
public class Bean_device {
    private String mac;
    private String id;
    private Integer profileid;
    private Integer endpointid;
    private Integer deviceid;
    private String name;
    private String equipment_brand;
    private String equipment_model;
    private Integer equipment_type;
    private Bean_state state;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getProfileid() {
        return profileid;
    }

    public void setProfileid(Integer profileid) {
        this.profileid = profileid;
    }

    public Integer getEndpointid() {
        return endpointid;
    }

    public void setEndpointid(Integer endpointid) {
        this.endpointid = endpointid;
    }

    public Integer getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Integer deviceid) {
        this.deviceid = deviceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquipment_brand() {
        return equipment_brand;
    }

    public void setEquipment_brand(String equipment_brand) {
        this.equipment_brand = equipment_brand;
    }

    public String getEquipment_model() {
        return equipment_model;
    }

    public void setEquipment_model(String equipment_model) {
        this.equipment_model = equipment_model;
    }

    public Integer getEquipment_type() {
        return equipment_type;
    }

    public void setEquipment_type(Integer equipment_type) {
        this.equipment_type = equipment_type;
    }

    public Bean_state getState() {
        return state;
    }

    public void setState(Bean_state state) {
        this.state = state;
    }
}
