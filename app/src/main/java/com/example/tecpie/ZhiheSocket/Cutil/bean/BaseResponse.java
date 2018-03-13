package com.example.tecpie.ZhiheSocket.Cutil.bean;

public class BaseResponse {

	private String code;
	private Integer result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "BaseResponse{" +
				"code='" + code + '\'' +
				", result=" + result +
				'}';
	}
}
