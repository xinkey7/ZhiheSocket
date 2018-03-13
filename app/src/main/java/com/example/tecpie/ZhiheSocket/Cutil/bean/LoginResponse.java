package com.example.tecpie.ZhiheSocket.Cutil.bean;

public class LoginResponse {

	private String code;
	private String mac;
	private String gateway;
	private String data;
	private String panid;
	private String profile;
	private String channel;
	private Integer result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getPanid() {
		return panid;
	}

	public void setPanid(String panid) {
		this.panid = panid;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "LoginResponse{" +
				"code='" + code + '\'' +
				", mac='" + mac + '\'' +
				", gateway='" + gateway + '\'' +
				", data='" + data + '\'' +
				", panid='" + panid + '\'' +
				", profile='" + profile + '\'' +
				", channel='" + channel + '\'' +
				", result=" + result +
				'}';
	}
}
