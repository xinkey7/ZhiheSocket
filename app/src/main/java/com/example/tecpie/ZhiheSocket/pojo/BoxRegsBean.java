package com.example.tecpie.ZhiheSocket.pojo;


/**
 * Created by xsy35 on 2017/12/13.
 */

public class BoxRegsBean {
    private String id;
    private String boxUid;
    private String alias;
    private String regData;
    private Boolean owned;
    private Boolean shared;
    private BoxBean box;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoxUid() {
		return boxUid;
	}
	public void setBoxUid(String boxUid) {
		this.boxUid = boxUid;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getRegData() {
		return regData;
	}
	public void setRegData(String regData) {
		this.regData = regData;
	}
	public Boolean getOwned() {
		return owned;
	}
	public void setOwned(Boolean owned) {
		this.owned = owned;
	}
	public Boolean getShared() {
		return shared;
	}
	public void setShared(Boolean shared) {
		this.shared = shared;
	}
	public BoxBean getBox() {
		return box;
	}
	public void setBox(BoxBean box) {
		this.box = box;
	}
	@Override
	public String toString() {
		return "BoxRegsBean [alias=" + alias + ", box=" + box + ", boxUid="
				+ boxUid + ", id=" + id + ", owned=" + owned + ", regData="
				+ regData + ", shared=" + shared + "]";
	}
	
    
    
    
 
	
    
	
    
}
