package com.example.tecpie.ZhiheSocket.pojo;

import java.util.List;


/**
 * Created by xsy35 on 2017/12/13.
 */

public class FBoxBean {
    private String id;
    private String name;
    private List<BoxRegsBean> boxRegs;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<BoxRegsBean> getBoxRegs() {
		return boxRegs;
	}
	public void setBoxRegs(List<BoxRegsBean> boxRegs) {
		this.boxRegs = boxRegs;
	}
	@Override
	public String toString() {
		return "FBoxBean [boxRegs=" + boxRegs + ", id=" + id + ", name=" + name
				+ "]";
	}
    
	
    
	
    
}
