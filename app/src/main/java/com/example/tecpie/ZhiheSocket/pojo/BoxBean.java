package com.example.tecpie.ZhiheSocket.pojo;


/**
 * Created by xsy35 on 2017/12/13.
 */

public class BoxBean {
    private String id;
    private String boxNo;
    private String boxType;
    private CsBean cs;
    private int devicePrimarySource;
    private String memo;
    private int net;
    private int mode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoxNo() {
		return boxNo;
	}
	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}
	public String getBoxType() {
		return boxType;
	}
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	public CsBean getCs() {
		return cs;
	}
	public void setCs(CsBean cs) {
		this.cs = cs;
	}
	public int getDevicePrimarySource() {
		return devicePrimarySource;
	}
	public void setDevicePrimarySource(int devicePrimarySource) {
		this.devicePrimarySource = devicePrimarySource;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getNet() {
		return net;
	}
	public void setNet(int net) {
		this.net = net;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	@Override
	public String toString() {
		return "BoxBean [boxNo=" + boxNo + ", boxType=" + boxType + ", cs="
				+ cs + ", devicePrimarySource=" + devicePrimarySource + ", id="
				+ id + ", memo=" + memo + ", mode=" + mode + ", net=" + net
				+ "]";
	}
    
    
 
	
    
	
    
}
