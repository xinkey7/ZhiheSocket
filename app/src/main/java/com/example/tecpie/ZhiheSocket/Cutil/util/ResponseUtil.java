package com.example.tecpie.ZhiheSocket.Cutil.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.tecpie.ZhiheSocket.Cutil.bean.ChannelBean;
import com.example.tecpie.ZhiheSocket.Cutil.bean.TalkBeanRequest;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class ResponseUtil {

	public static void sendMsg(String msg, ChannelHandlerContext ctx) {
		Log.i("debugs","向通道下发报文"+msg);
		byte[] msg_byte = HexTools.HexStringtoBytes(msg);
		ByteBuf encoded = Unpooled.wrappedBuffer(msg_byte);
		ctx.write(encoded);
		ctx.flush();
	}
	
	public static List<TalkBeanRequest> createMsg(String code, String content, String checkCode) {
		List<TalkBeanRequest> list = new ArrayList<TalkBeanRequest>();
		try {
			int sum = content.length() / 2 / 1024;
			int left = content.length() / 2 % 1024;
			int total_int = 0;
			if (left == 0) {
				total_int = sum;
			} else {
				total_int = sum + 1;
			}
			for (int i = 1; i <= total_int; i++) {
				TalkBeanRequest response = new TalkBeanRequest();
				response.setSync("5a5a");
				response.setMsgType(code);
				String current = HexTools.long2hexStr(i);
				response.setCurrent(HexTools.obox(current, 4));
				String total = HexTools.long2hexStr(total_int);
				response.setTotal(HexTools.obox(total, 4));
				int start = 0;
				int end = 0;

				if (i == total_int) {
					start = 1024 * (i - 1) * 2;
					end = content.length();
				} else {
					start = 1024 * (i - 1) * 2;
					end = 1024 * i * 2;
				}
				response.setContent(HexTools.Str2hexStr(content.substring(start,
						end)));
				int length_int = HexTools.Str2hexStr(content.substring(start, end))
						.length() / 2 + 10 + 16;
				String length_str = HexTools.long2hexStr(length_int);
				response.setLength(HexTools.obox(length_str, 4));
				String head_content = response.headContent();
				String md5 = Md5Util.HexStrMD5(head_content + HexTools.Str2hexStr(checkCode));
				response.setTail(md5);
				list.add(response);
			}
			return list;
		}catch (Exception e){
			e.printStackTrace();

		}

		return list;
	}

	//关闭该链路
	public static void channelClose(ChannelHandlerContext ctx) {
		ctx.close();
		ChannelBean.removeChannel("channel");
	}
}
