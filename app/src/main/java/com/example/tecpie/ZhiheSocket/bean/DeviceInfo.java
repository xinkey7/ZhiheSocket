package com.example.tecpie.ZhiheSocket.bean;

public class DeviceInfo {
	private int boxType; //0 :��׼����      1:mini����
	private String memo; //��ע
	private int connectionState; //����״̬��0��δ֪��1���Ѿ����ӣ�2����ʱ��3���ѶϿ�
	private int refreshTime;//��ص�ˢ��ʱ�䣨��λ���룩
	private int enabledDMonPush;
	private int enabledAlarmPush;
	private int enabledHDataPush;
	private String ip;
	private int ipSource;
	private String subnetWork;
	private String gateWay;
	private String primaryDns;
	private String secondaryDns;
	private String subIp;
	private int netWorkType;//��ǰ�������� ��1����̫����2��2G,3��3G,4��WIFI,5��4G
	public int getBoxType() {
		return boxType;
	}
	public void setBoxType(int boxType) {
		this.boxType = boxType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getConnectionState() {
		return connectionState;
	}
	public void setConnectionState(int connectionState) {
		this.connectionState = connectionState;
	}
	public int getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}
	public int getEnabledDMonPush() {
		return enabledDMonPush;
	}
	public void setEnabledDMonPush(int enabledDMonPush) {
		this.enabledDMonPush = enabledDMonPush;
	}
	public int getEnabledAlarmPush() {
		return enabledAlarmPush;
	}
	public void setEnabledAlarmPush(int enabledAlarmPush) {
		this.enabledAlarmPush = enabledAlarmPush;
	}
	public int getEnabledHDataPush() {
		return enabledHDataPush;
	}
	public void setEnabledHDataPush(int enabledHDataPush) {
		this.enabledHDataPush = enabledHDataPush;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getIpSource() {
		return ipSource;
	}
	public void setIpSource(int ipSource) {
		this.ipSource = ipSource;
	}
	public String getSubnetWork() {
		return subnetWork;
	}
	public void setSubnetWork(String subnetWork) {
		this.subnetWork = subnetWork;
	}
	public String getGateWay() {
		return gateWay;
	}
	public void setGateWay(String gateWay) {
		this.gateWay = gateWay;
	}
	public String getPrimaryDns() {
		return primaryDns;
	}
	public void setPrimaryDns(String primaryDns) {
		this.primaryDns = primaryDns;
	}
	public String getSecondaryDns() {
		return secondaryDns;
	}
	public void setSecondaryDns(String secondaryDns) {
		this.secondaryDns = secondaryDns;
	}
	public String getSubIp() {
		return subIp;
	}
	public void setSubIp(String subIp) {
		this.subIp = subIp;
	}
	public int getNetWorkType() {
		return netWorkType;
	}
	public void setNetWorkType(int netWorkType) {
		this.netWorkType = netWorkType;
	}
	@Override
	public String toString() {
		return "DeviceInfo [boxType=" + boxType + ", connectionState="
				+ connectionState + ", enabledAlarmPush=" + enabledAlarmPush
				+ ", enabledDMonPush=" + enabledDMonPush
				+ ", enabledHDataPush=" + enabledHDataPush + ", gateWay="
				+ gateWay + ", ip=" + ip + ", ipSource=" + ipSource + ", memo="
				+ memo + ", netWorkType=" + netWorkType + ", primaryDns="
				+ primaryDns + ", refreshTime=" + refreshTime
				+ ", secondaryDns=" + secondaryDns + ", subIp=" + subIp
				+ ", subnetWork=" + subnetWork + "]";
	}
	
	
	

}
