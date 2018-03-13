package com.example.tecpie.ZhiheSocket.Cutil;

import android.util.Log;

import com.example.tecpie.ZhiheSocket.Cutil.bean.BaseResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ChannelBean;
import com.example.tecpie.ZhiheSocket.Cutil.bean.LoginResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ReadSocketResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketDataResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.TalkBean;
import com.example.tecpie.ZhiheSocket.Cutil.service.ResponseService;

import com.example.tecpie.ZhiheSocket.Cutil.serviceImpl.ResponseServiceImpl;
import com.example.tecpie.ZhiheSocket.Cutil.util.HexTools;
import com.example.tecpie.ZhiheSocket.Cutil.util.Md5Util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

public class AppClientHandle extends ChannelInboundHandlerAdapter{

	private ResponseService service = new ResponseServiceImpl();
	
	private String index_str = "";
	private String ret = "";
	private boolean isEnd = false;
	private int packLength = 0;
	private String data_code = "gateway";
	private Map<Integer, String> map = new HashMap<Integer, String>();//用来存储多帧报文


	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf result_buf = (ByteBuf) msg;
		byte[] result = new byte[result_buf.readableBytes()];
		result_buf.readBytes(result);
		result_buf.release();
		String ret_index = HexTools.bytesToHexString(result);
		index_str += ret_index;
		while(isEnd()) {
			isEnd = false;
			Log.i("debugs","收到的报文：" + ret);
			TalkBean bean = new TalkBean(ret);
			if (checkMD5(bean)) {// MD5校验
				String order = bean.getMsgType();
				String total_str = bean.getTotal();
				String current_str = bean.getCurrent();
				int total = Integer.parseInt(HexTools.hexStr2long(total_str) + "");
				int current = Integer.parseInt(HexTools.hexStr2long(current_str) + "");
				map.put(current, bean.getContent());
				if (map.size() == total) {// 消息接收完毕，开始处理程序
					// 假设total>1，先把所有消息包的内容拼起来，组成一个完成的数据
					String real_content = "";
					for (int i = 1; i <= total; i++) {
						real_content += map.get(i);
						Log.i("debugs","Json:" + HexTools.hexToString(real_content));

					}
					real_content = HexTools.hexToString(real_content);
					if (order.equals("8001")) {//登录回复
						LoginResponse response = service.loginResponse(real_content);
						ChannelBean.setLoginResponse(response);
					} else if (order.equals("8002")){//配网-开网应答
						BaseResponse response = service.baseResponse(real_content);
						ChannelBean.setBaseResponse(response);
					} else if (order.equals("8003")){//配网-关网应答
						BaseResponse response = service.baseResponse(real_content);
						ChannelBean.setBaseResponse(response);
					} else if (order.equals("8004")){//下发网关应答
						BaseResponse response = service.baseResponse(real_content);
						ChannelBean.setBaseResponse(response);
					} else if (order.equals("8005")){//读取网关插座信息应答
						ReadSocketResponse response = service.readSocketResponse(real_content);
						ChannelBean.setReadSocketResponse(response);
					} else if (order.equals("8006")){//写入网关配置应答
						BaseResponse response = service.baseResponse(real_content);
						ChannelBean.setBaseResponse(response);
					}  else if (order.equals("8007")){//获取插座信息应答

						SocketDataResponse response = service.socketDataResponse(real_content);
						Log.i("debugs","ieee"+response.getIeee());
						ChannelBean.setSocketDataResponse(response);
					}
				}
			} else {
				Log.i("debugs","MD5校验失败");

			}
		}
	}
	
	public void channelActive(ChannelHandlerContext ctx) {
		ChannelBean.addChannel("channel", ctx);
		Log.i("debugs","与网关开始建立通讯");

	}
	
	 
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Log.i("debugs","通信通道发生关闭");

		/*try {
			new AppClient().start();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * 以下为报文处理相关判断或处理
	 */
	private boolean isEnd() {
		if (index_str.startsWith("5a5a") && index_str.length() >= 8) {
			String lengthStr = index_str.substring(4, 8);
			long packageLen = HexTools.hexStr2long(lengthStr);
			packLength = Integer.parseInt(packageLen + "");
		}
		if (index_str.length() >= packLength * 2) {
			isEnd = true;
			ret = index_str.substring(0, packLength * 2);
			index_str = index_str.substring(packLength * 2, index_str.length());
		}
		return isEnd;
	}
	
	private boolean checkMD5(TalkBean bean) {
		String order = bean.getMsgType();
		String head_content = bean.headContent();
		String tail = bean.getTail();
		if ("8001".equals(order)) {// 网关登录回复
			String md5 = Md5Util.HexStrMD5(head_content + HexTools.Str2hexStr(data_code));
			if (md5.equals(tail)) {
				return true;
			}
		} else {
			if (data_code != null) {
				String md5 = Md5Util.HexStrMD5(head_content + HexTools.Str2hexStr(data_code));
				if (md5.equals(tail)) {
					return true;
				}
			}
		}
		return false;
	}


}
