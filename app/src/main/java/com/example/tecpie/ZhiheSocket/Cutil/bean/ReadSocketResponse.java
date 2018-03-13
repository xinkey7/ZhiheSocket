package com.example.tecpie.ZhiheSocket.Cutil.bean;

import java.util.List;

public class ReadSocketResponse {

	private String code;
	private String gateway;
	private String mac;
	private String name;
	private List<SocketBean> data;
	private Integer result;

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

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

}
