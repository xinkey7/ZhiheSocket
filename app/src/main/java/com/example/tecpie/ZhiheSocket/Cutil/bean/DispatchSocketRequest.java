package com.example.tecpie.ZhiheSocket.Cutil.bean;

import java.util.List;

public class DispatchSocketRequest {

	private String code;
	private String gateway;
	private String mac;
	private String name;
	private List<SocketBean> data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SocketBean> getData() {
		return data;
	}

	public void setData(List<SocketBean> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DispatchSocketRequest{" +
				"code='" + code + '\'' +
				", gateway='" + gateway + '\'' +
				", mac='" + mac + '\'' +
				", name='" + name + '\'' +
				", data=" + data +
				'}';
	}
}
