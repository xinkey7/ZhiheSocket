package com.example.tecpie.ZhiheSocket.pojo;


/**
 * Created by xsy35 on 2017/12/13.
 */

public class CsBean {
    private String apiBaseUrl;
    private String signalrUrl;
	public String getApiBaseUrl() {
		return apiBaseUrl;
	}
	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}
	public String getSignalrUrl() {
		return signalrUrl;
	}
	public void setSignalrUrl(String signalrUrl) {
		this.signalrUrl = signalrUrl;
	}
	@Override
	public String toString() {
		return "CsBean [apiBaseUrl=" + apiBaseUrl + ", signalrUrl="
				+ signalrUrl + "]";
	}
    
 
	
    
	
    
}
