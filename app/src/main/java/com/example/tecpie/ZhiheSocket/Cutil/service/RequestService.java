package com.example.tecpie.ZhiheSocket.Cutil.service;

import com.example.tecpie.ZhiheSocket.Cutil.bean.DispatchSocketRequest;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketDataRequest;
import com.example.tecpie.ZhiheSocket.Cutil.bean.WriteConfigRequest;

import java.util.List;


import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author lanqiuzf
 * app向网关发送的请求指令
 */
public interface RequestService {
	
	//app发送简单基本请求
	public void baseRequest(ChannelHandlerContext ctx, String code, String checkString);
	
	//app下发插座数据至网关
	public void dispatchSocketRequest(ChannelHandlerContext ctx, DispatchSocketRequest request, String checkString);
	
	//app写入网关配置信息
	public void writeConfigRequest(ChannelHandlerContext ctx, WriteConfigRequest request, String checkString);

	//app发送简单基本请求
	public void socketDataRequest(ChannelHandlerContext ctx, SocketDataRequest request, String checkString);
}
