package com.example.tecpie.ZhiheSocket.Cutil.service;


import com.example.tecpie.ZhiheSocket.Cutil.bean.BaseResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.LoginResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ReadSocketResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketDataResponse;

public interface ResponseService {

	//网关基本响应
	public BaseResponse baseResponse(String content);
	
	//网关登录响应
	public LoginResponse loginResponse(String content);
	
	//读取插座信息响应
	public ReadSocketResponse readSocketResponse(String content);

	//读取插座信息响应
	public SocketDataResponse socketDataResponse(String content);
}
