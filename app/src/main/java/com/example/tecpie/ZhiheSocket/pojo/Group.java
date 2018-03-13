package com.example.tecpie.ZhiheSocket.pojo;

public class Group {
	private String boxUid;
	private String memo;
	private String name;
	private String uid;
	public String getBoxUid() {
		return boxUid;
	}
	public void setBoxUid(String boxUid) {
		this.boxUid = boxUid;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "Group [boxUid=" + boxUid + ", memo=" + memo + ", name=" + name
				+ ", uid=" + uid + "]";
	}
	

}
