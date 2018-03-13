package com.example.tecpie.ZhiheSocket.utils;

import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import com.example.tecpie.ZhiheSocket.activity.SocketDetailActivity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

public class ModbusClientHandle extends ChannelInboundHandlerAdapter{


	/**
	 * 读数据调用
	 */
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		String cell = "";// 单元ID
		int startId = 0;// 寄存器ID
		try {
			String address = ctx.channel().remoteAddress().toString().replace("/", "");
			ByteBuf result = (ByteBuf) msg;
			byte[] resultByte = new byte[result.readableBytes()];
			Log.i("123",resultByte.toString());
			result.readBytes(resultByte);
			result.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送请求
	 */
	public void channelActive(ChannelHandlerContext ctx) {
		Log.i("123", "建立通讯");
		byte[] sendByte = new byte[]{0,1,2,3,4};
		ByteBuf encoded = Unpooled.wrappedBuffer(sendByte);
		SocketChannel sc = (SocketChannel) ctx.channel();
		if (null != sc) {
			sc.writeAndFlush(encoded);
		}
	}
	
	/**
	 * 一旦通道发生关闭重新启动
	 */
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		String address = ctx.channel().remoteAddress().toString().replace("/", "");
		String ip = address.split(":")[0];
		String port = address.split(":")[1];
		System.out.println("ip地址:" + ip + "端口:" + port + "通道发生关闭");
		try {
			new NettyClient(ip, Integer.parseInt(port), "com.example.tecpie.ZhiheSocket.utils.ModbusClientHandle", "ModbusClient")
					.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
