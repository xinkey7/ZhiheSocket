package com.example.tecpie.ZhiheSocket.Cutil.serviceImpl;


import android.util.Log;

import com.example.tecpie.ZhiheSocket.Cutil.bean.BaseResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.LoginResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ReadSocketResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketDataResponse;
import com.example.tecpie.ZhiheSocket.Cutil.service.ResponseService;
import com.example.tecpie.ZhiheSocket.Cutil.util.JsonUtil;

public class ResponseServiceImpl implements ResponseService {

	@Override
	public BaseResponse baseResponse(String content) {
		BaseResponse response = JsonUtil.fromJson(content, BaseResponse.class);
		return response;
	}

	@Override
	public LoginResponse loginResponse(String content) {
		LoginResponse response = JsonUtil.fromJson(content, LoginResponse.class);
		return response;
	}

	@Override
	public ReadSocketResponse readSocketResponse(String content) {
		ReadSocketResponse response = JsonUtil.fromJson(content, ReadSocketResponse.class);
		return response;
	}

	@Override
	public SocketDataResponse socketDataResponse(String content) {
		SocketDataResponse response = JsonUtil.fromJson(content, SocketDataResponse.class);
		return response;
	}

}
