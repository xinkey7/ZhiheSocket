package com.example.tecpie.ZhiheSocket.Cutil.bean;


import com.example.tecpie.ZhiheSocket.Cutil.util.HexTools;

public class TalkBean {
	private String sync;//起始字符
	private String length;//消息长度
	private String msgType;//消息类型
	private String total;//总帧数
	private String current;//当前帧数
	private String content;//消息体
	private String tail;//校验码
	
	
	public TalkBean(String ret) {
		this.sync=ret.substring(0,4);
		this.length=ret.substring(4, 8);
		long packageLen = HexTools.hexStr2long(this.length);
		int packLength = Integer.parseInt(packageLen + "");
		this.msgType=ret.substring(8, 12);
		this.total=ret.substring(12, 16);
		this.current=ret.substring(16, 20);
		this.content=ret.substring(20, packLength*2-32);
		this.tail=ret.substring(packLength*2-32,packLength*2);
	}
	
	public String headContent(){
		return this.sync+this.length+this.msgType+this.total+this.current+this.content;
	}
	
	public String getSync() {
		return sync;
	}
	public void setSync(String sync) {
		this.sync = sync;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTail() {
		return tail;
	}
	public void setTail(String tail) {
		this.tail = tail;
	}
	
	
}
