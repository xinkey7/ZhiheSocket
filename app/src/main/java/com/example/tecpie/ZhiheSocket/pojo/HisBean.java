package com.example.tecpie.ZhiheSocket.pojo;


/**
 * Created by xsy35 on 2017/12/13.
 */

public class HisBean {
    private String uid;
    private String boxUid;
    private String name;
    private Boolean isDeviceChanged;
    private HisSrc src;
    private String desc;
    private int dataType;
    private Boolean hasCtrl;
    private HisCtrl ctrl;
    private int period;
    private int intDigits;
    private int fracDigits;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getBoxUid() {
		return boxUid;
	}
	public void setBoxUid(String boxUid) {
		this.boxUid = boxUid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsDeviceChanged() {
		return isDeviceChanged;
	}
	public void setIsDeviceChanged(Boolean isDeviceChanged) {
		this.isDeviceChanged = isDeviceChanged;
	}
	public HisSrc getSrc() {
		return src;
	}
	public void setSrc(HisSrc src) {
		this.src = src;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public Boolean getHasCtrl() {
		return hasCtrl;
	}
	public void setHasCtrl(Boolean hasCtrl) {
		this.hasCtrl = hasCtrl;
	}
	public HisCtrl getCtrl() {
		return ctrl;
	}
	public void setCtrl(HisCtrl ctrl) {
		this.ctrl = ctrl;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getIntDigits() {
		return intDigits;
	}
	public void setIntDigits(int intDigits) {
		this.intDigits = intDigits;
	}
	public int getFracDigits() {
		return fracDigits;
	}
	public void setFracDigits(int fracDigits) {
		this.fracDigits = fracDigits;
	}
	@Override
	public String toString() {
		return "HisBean [boxUid=" + boxUid + ", dataType=" + dataType
				+ ", desc=" + desc + ", fracDigits=" + fracDigits
				+ ", hasCtrl=" + hasCtrl + ", intDigits=" + intDigits
				+ ", isDeviceChanged=" + isDeviceChanged + ", name=" + name
				+ ", period=" + period + ", uid=" + uid + "]";
	}
    
    
    
    
 
	
    
	
    
}
