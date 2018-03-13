package com.example.tecpie.ZhiheSocket.bean;

public class ValueSetBody {
	
	private String name;
	private int type;
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "ValueSetBody [name=" + name + ", type=" + type + ", value="
				+ value + "]";
	}
	
	

}
