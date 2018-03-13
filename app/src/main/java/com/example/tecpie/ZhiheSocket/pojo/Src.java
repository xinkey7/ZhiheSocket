package com.example.tecpie.ZhiheSocket.pojo;

public class Src {
	private int deadType;
	private String deadValue;
	private int deviceId;
	private Boolean ext;
	private int mainAddr;
	private int portNo;
	private int regId;
	private int regWidth;
	private int serverId;
	private int stationNo;
	private int subAddr;
	private int subIndex;
	private String uid;
	public int getDeadType() {
		return deadType;
	}
	public void setDeadType(int deadType) {
		this.deadType = deadType;
	}
	public String getDeadValue() {
		return deadValue;
	}
	public void setDeadValue(String deadValue) {
		this.deadValue = deadValue;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public Boolean getExt() {
		return ext;
	}
	public void setExt(Boolean ext) {
		this.ext = ext;
	}
	public int getMainAddr() {
		return mainAddr;
	}
	public void setMainAddr(int mainAddr) {
		this.mainAddr = mainAddr;
	}
	public int getPortNo() {
		return portNo;
	}
	public void setPortNo(int portNo) {
		this.portNo = portNo;
	}
	public int getRegId() {
		return regId;
	}
	public void setRegId(int regId) {
		this.regId = regId;
	}
	public int getRegWidth() {
		return regWidth;
	}
	public void setRegWidth(int regWidth) {
		this.regWidth = regWidth;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public int getStationNo() {
		return stationNo;
	}
	public void setStationNo(int stationNo) {
		this.stationNo = stationNo;
	}
	public int getSubAddr() {
		return subAddr;
	}
	public void setSubAddr(int subAddr) {
		this.subAddr = subAddr;
	}
	public int getSubIndex() {
		return subIndex;
	}
	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "Src [deadType=" + deadType + ", deadValue=" + deadValue
				+ ", deviceId=" + deviceId + ", ext=" + ext + ", mainAddr="
				+ mainAddr + ", portNo=" + portNo + ", regId=" + regId
				+ ", regWidth=" + regWidth + ", serverId=" + serverId
				+ ", stationNo=" + stationNo + ", subAddr=" + subAddr
				+ ", subIndex=" + subIndex + ", uid=" + uid + "]";
	}
	
	
	
}
