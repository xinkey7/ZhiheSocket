package com.example.tecpie.ZhiheSocket.pojo;

public class DMonEntries {
	private String boxUid;
	private String dMonGroupUid;
	private int dataType;
	private Boolean deviceChanged;
	private String desc;
	private int fracDigits;
	private int intDigits;
	private String name;
	private int privilege;
	private String uid;
	private Src src;
	public String getBoxUid() {
		return boxUid;
	}
	public void setBoxUid(String boxUid) {
		this.boxUid = boxUid;
	}
	public String getdMonGroupUid() {
		return dMonGroupUid;
	}
	public void setdMonGroupUid(String dMonGroupUid) {
		this.dMonGroupUid = dMonGroupUid;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public Boolean getDeviceChanged() {
		return deviceChanged;
	}
	public void setDeviceChanged(Boolean deviceChanged) {
		this.deviceChanged = deviceChanged;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getFracDigits() {
		return fracDigits;
	}
	public void setFracDigits(int fracDigits) {
		this.fracDigits = fracDigits;
	}
	public int getIntDigits() {
		return intDigits;
	}
	public void setIntDigits(int intDigits) {
		this.intDigits = intDigits;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Src getSrc() {
		return src;
	}
	public void setSrc(Src src) {
		this.src = src;
	}
	@Override
	public String toString() {
		return "dMonEntries [boxUid=" + boxUid + ", dMonGroupUid="
				+ dMonGroupUid + ", dataType=" + dataType + ", desc=" + desc
				+ ", deviceChanged=" + deviceChanged + ", fracDigits="
				+ fracDigits + ", intDigits=" + intDigits + ", name=" + name
				+ ", privilege=" + privilege + ", src=" + src + ", uid=" + uid
				+ "]";
	}
	

}
