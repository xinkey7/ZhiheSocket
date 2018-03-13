package com.example.tecpie.ZhiheSocket.bean;

public class Detail {
	private String name;
	private int stationNo;
	private int dataType;
	private  int mainAddr;
	private int subAddr;
	private int privilege;
	private String desc;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public int getMainAddr() {
		return mainAddr;
	}
	public void setMainAddr(int mainAddr) {
		this.mainAddr = mainAddr;
	}
	public int getSubAddr() {
		return subAddr;
	}
	public void setSubAddr(int subAddr) {
		this.subAddr = subAddr;
	}
	public int getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getStationNo() {
		return stationNo;
	}
	public void setStationNo(int stationNo) {
		this.stationNo = stationNo;
	}
	@Override
	public String toString() {
		return "Detail [dataType=" + dataType + ", desc=" + desc
				+ ", mainAddr=" + mainAddr + ", name=" + name + ", privilege="
				+ privilege + ", stationNo=" + stationNo + ", subAddr="
				+ subAddr + "]";
	}
	
	

}
