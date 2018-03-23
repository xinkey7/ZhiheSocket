package com.example.tecpie.ZhiheSocket.Cutil.serviceImpl;

import android.util.Log;

import com.example.tecpie.ZhiheSocket.Cutil.bean.BaseRequest;
import com.example.tecpie.ZhiheSocket.Cutil.bean.DispatchSocketRequest;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketDataRequest;
import com.example.tecpie.ZhiheSocket.Cutil.bean.TalkBeanRequest;
import com.example.tecpie.ZhiheSocket.Cutil.bean.WriteConfigRequest;
import com.example.tecpie.ZhiheSocket.Cutil.service.RequestService;
import com.example.tecpie.ZhiheSocket.Cutil.util.JsonUtil;
import com.example.tecpie.ZhiheSocket.Cutil.util.ResponseUtil;

import java.util.List;



import io.netty.channel.ChannelHandlerContext;

public class RequestServiceImpl implements RequestService {

	@Override
	public void baseRequest(ChannelHandlerContext ctx, String code, String checkCode) {
		BaseRequest request = new BaseRequest();
		request.setCode(code);
		String content = JsonUtil.toJson(request);
		List<TalkBeanRequest> list = ResponseUtil.createMsg(code, content, checkCode);
		if (list != null) {
			for (TalkBeanRequest res : list) {
				ResponseUtil.sendMsg(res.hex(), ctx);
			}
		}
	}

	@Override
	public void dispatchSocketRequest(ChannelHandlerContext ctx, DispatchSocketRequest request, String checkCode) {
		String content = JsonUtil.toJson(request);
		Log.i("dispatchSocketRequest",""+content);
		String code = request.getCode();
		Log.i("dispatchSocketRequest",""+code);
		List<TalkBeanRequest> list = ResponseUtil.createMsg(code, content, checkCode);
		Log.i("dispatchSocketRequest","list"+list.size());
		if (list != null) {
			for (TalkBeanRequest res : list) {
				ResponseUtil.sendMsg(res.hex(), ctx);
			}
		}
	}

	@Override
	public void writeConfigRequest(ChannelHandlerContext ctx, WriteConfigRequest request, String checkCode) {
		String content = JsonUtil.toJson(request);
		String code = request.getCode();
		List<TalkBeanRequest> list = ResponseUtil.createMsg(code, content, checkCode);
		if (list != null) {
			for (TalkBeanRequest res : list) {
				ResponseUtil.sendMsg(res.hex(), ctx);
			}
		}
	}

	@Override
	public void socketDataRequest(ChannelHandlerContext ctx, SocketDataRequest request, String checkString) {
		String content = JsonUtil.toJson(request);
		String code = request.getCode();
		List<TalkBeanRequest> list = ResponseUtil.createMsg(code, content, checkString);
		if (list != null) {
			for (TalkBeanRequest res : list) {
				ResponseUtil.sendMsg(res.hex(), ctx);
			}
		}
	}

}
