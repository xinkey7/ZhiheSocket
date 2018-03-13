package com.example.tecpie.ZhiheSocket.Cutil.bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;


public class ChannelBean {
	private static LoginResponse loginResponse;
	private static BaseResponse baseResponse;
	private static ReadSocketResponse readSocketResponse;
	private static SocketDataResponse socketDataResponse;

	private static Map<String, ChannelHandlerContext> channelMap = new ConcurrentHashMap<String, ChannelHandlerContext>();
	
	public static void addChannel(String id, ChannelHandlerContext channel) {
		channelMap.put(id, channel);
	}

	public static Map<String, ChannelHandlerContext> getChannels() {
		return channelMap;
	}

	public static ChannelHandlerContext getChannel(String id) {
		return channelMap.get(id);
	}

	public static LoginResponse getLoginResponse() {
		return loginResponse;
	}

	public static BaseResponse getBaseResponse() {
		return baseResponse;
	}

	public static ReadSocketResponse getReadSocketResponse() {
		return readSocketResponse;
	}

	public static void setLoginResponse(LoginResponse loginResponse) {
		ChannelBean.loginResponse = loginResponse;
	}

	public static void setBaseResponse(BaseResponse baseResponse) {
		ChannelBean.baseResponse = baseResponse;
	}

	public static void setReadSocketResponse(ReadSocketResponse readSocketResponse) {
		ChannelBean.readSocketResponse = readSocketResponse;
	}

	public static SocketDataResponse getSocketDataResponse() {
		return socketDataResponse;
	}

	public static void setSocketDataResponse(SocketDataResponse socketDataResponse) {
		ChannelBean.socketDataResponse = socketDataResponse;
	}

	public static void removeChannel(String id) {
		channelMap.remove(id);
	}
}
