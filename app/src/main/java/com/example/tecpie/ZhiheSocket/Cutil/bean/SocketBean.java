package com.example.tecpie.ZhiheSocket.Cutil.bean;

public class SocketBean {

	private String ieee;
	private String name;
	private Integer deviceid;
	private Integer display;
	private Integer online;


	public String getIeee() {
		return ieee;
	}

	public void setIeee(String ieee) {
		this.ieee = ieee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(Integer deviceid) {
		this.deviceid = deviceid;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "SocketBean{" +
				"ieee='" + ieee + '\'' +
				", name='" + name + '\'' +
				", deviceid=" + deviceid +
				", display=" + display +
				", online=" + online +
				'}';
	}
}
