package com.example.tecpie.jiaju.pojo;


public class PostExecuteControlBean {
    private Boolean on; //开关状态
    private Integer bri;    //亮度状态
    private Integer hue;    //颜色状态
    private Integer sat;    //饱和度状态
    private Integer colortemp;  //色温状态
    private String infraredcode;    //红外转发的字符串
    private String infraredlearn;   //红外学习命令号
    private Integer infraredcontrol;    //红外控制命令号
    private String rawdata;
    private Integer status; //烟感状态
    private Integer zoneid; //烟感区域
    private String now_lux; //光照强度
    private Integer level_status;   //光照度等级状态
    private String target_lux;  //目标光照度

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Integer getBri() {
        return bri;
    }

    public void setBri(Integer bri) {
        this.bri = bri;
    }

    public Integer getHue() {
        return hue;
    }

    public void setHue(Integer hue) {
        this.hue = hue;
    }

    public Integer getSat() {
        return sat;
    }

    public void setSat(Integer sat) {
        this.sat = sat;
    }

    public Integer getColortemp() {
        return colortemp;
    }

    public void setColortemp(Integer colortemp) {
        this.colortemp = colortemp;
    }

    public String getInfraredcode() {
        return infraredcode;
    }

    public void setInfraredcode(String infraredcode) {
        this.infraredcode = infraredcode;
    }

    public String getInfraredlearn() {
        return infraredlearn;
    }

    public void setInfraredlearn(String infraredlearn) {
        this.infraredlearn = infraredlearn;
    }

    public Integer getInfraredcontrol() {
        return infraredcontrol;
    }

    public void setInfraredcontrol(Integer infraredcontrol) {
        this.infraredcontrol = infraredcontrol;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getZoneid() {
        return zoneid;
    }

    public void setZoneid(Integer zoneid) {
        this.zoneid = zoneid;
    }

    public String getNow_lux() {
        return now_lux;
    }

    public void setNow_lux(String now_lux) {
        this.now_lux = now_lux;
    }

    public Integer getLevel_status() {
        return level_status;
    }

    public void setLevel_status(Integer level_status) {
        this.level_status = level_status;
    }

    public String getTarget_lux() {
        return target_lux;
    }

    public void setTarget_lux(String target_lux) {
        this.target_lux = target_lux;
    }

    public String getRawdata() {
        return rawdata;
    }

    public void setRawdata(String rawdata) {
        this.rawdata = rawdata;
    }
}