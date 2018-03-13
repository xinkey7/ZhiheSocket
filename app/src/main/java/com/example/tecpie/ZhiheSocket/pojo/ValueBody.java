package com.example.tecpie.ZhiheSocket.pojo;

import java.util.List;
//���ڷ��ͻ�ȡ��ص�ֵ�е�body
public class ValueBody {
	private List<String> names;
	private List<String> ids;
	private String boxNo;
	private Boolean userCache;
	private int timeOut;
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public String getBoxNo() {
		return boxNo;
	}
	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}
	public Boolean getUserCache() {
		return userCache;
	}
	public void setUserCache(Boolean userCache) {
		this.userCache = userCache;
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	@Override
	public String toString() {
		return "ValueBody [boxNo=" + boxNo + ", ids=" + ids + ", names="
				+ names + ", timeOut=" + timeOut + ", userCache=" + userCache
				+ "]";
	}
	

}
