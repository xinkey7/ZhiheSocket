package com.example.tecpie.ZhiheSocket.pojo;

import java.util.List;


/**
 * Created by xsy35 on 2017/12/13.
 */

public class DMonEntrieBean {
    private String name;
    private String uid;
    private List<DMonEntries> dMonEntries;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<DMonEntries> getdMonEntries() {
		return dMonEntries;
	}
	public void setdMonEntries(List<DMonEntries> dMonEntries) {
		this.dMonEntries = dMonEntries;
	}
	@Override
	public String toString() {
		return "DMonEntrieBean [dMonEntries=" + dMonEntries + ", name=" + name
				+ ", uid=" + uid + "]";
	}
    
	
    
	
    
}
