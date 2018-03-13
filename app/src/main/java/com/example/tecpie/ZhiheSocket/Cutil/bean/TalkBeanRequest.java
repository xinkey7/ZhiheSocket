package com.example.tecpie.ZhiheSocket.Cutil.bean;

public class TalkBeanRequest {
	private String sync;
	private String length;
	private String msgType;
	private String total;
	private String current;
	private String content;
	private String tail;

	public String headContent() {
		return this.sync + this.length + this.msgType + this.total + this.current + this.content;
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

	public String hex() {
		return this.sync + this.length + this.msgType + this.total + this.current + this.content + this.tail;
	}
}
