package com.example.tecpie.ZhiheSocket.pojo;

public class HisCtrl {
	private int deviceId;
	private int serverId;
	private int protNo;
	private int stationNo;
	private int regId;
	private int regWidth;
	private int mainAddr;
	private int subIndex;
	private int dataType;
	private Boolean ctrlType;
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public int getProtNo() {
		return protNo;
	}
	public void setProtNo(int protNo) {
		this.protNo = protNo;
	}
	public int getStationNo() {
		return stationNo;
	}
	public void setStationNo(int stationNo) {
		this.stationNo = stationNo;
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
	public int getMainAddr() {
		return mainAddr;
	}
	public void setMainAddr(int mainAddr) {
		this.mainAddr = mainAddr;
	}
	public int getSubIndex() {
		return subIndex;
	}
	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public Boolean getCtrlType() {
		return ctrlType;
	}
	public void setCtrlType(Boolean ctrlType) {
		this.ctrlType = ctrlType;
	}
	@Override
	public String toString() {
		return "HisCtrl [ctrlType=" + ctrlType + ", dataType=" + dataType
				+ ", deviceId=" + deviceId + ", mainAddr=" + mainAddr
				+ ", protNo=" + protNo + ", regId=" + regId + ", regWidth="
				+ regWidth + ", serverId=" + serverId + ", stationNo="
				+ stationNo + ", subIndex=" + subIndex + "]";
	}
	

}
