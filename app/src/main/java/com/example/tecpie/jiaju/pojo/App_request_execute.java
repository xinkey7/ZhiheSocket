package com.example.tecpie.jiaju.pojo;

import java.util.List;

public class App_request_execute {
	private String gateway;
	private Long time;
	private String id;
	private Integer profileid;
	private StateBean control;
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public StateBean getControl() {
		return control;
	}
	public void setControl(StateBean control) {
		this.control = control;
	}
	public Integer getProfileid() {
		return profileid;
	}
	public void setProfileid(Integer profileid) {
		this.profileid = profileid;
	}
	
	
}
