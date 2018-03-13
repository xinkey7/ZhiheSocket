package com.example.tecpie.ZhiheSocket.pojo;

public class ValueBean {
	private String id;
	private String timestamp;
	private int dataType;
	private Object value;
	private String name;
	private long boxId;
	private String boxNo;
	private int status;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getBoxId() {
		return boxId;
	}
	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}
	public String getBoxNo() {
		return boxNo;
	}
	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "ValueBean [boxId=" + boxId + ", boxNo=" + boxNo + ", dataType="
				+ dataType + ", id=" + id + ", name=" + name + ", status="
				+ status + ", timestamp=" + timestamp + ", value=" + value
				+ "]";
	}
	
	
	

}
