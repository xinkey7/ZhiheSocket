package com.example.tecpie.ZhiheSocket.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NettyClient extends Thread {
	private String host;
	private int port;
	private String classname;
	private String protocolName;
	private ChannelFuture f;

	public NettyClient(String host, int port, String classname,
			String protocolName) {
		super();
		this.host = host;
		this.port = port;
		this.classname = classname;
		this.protocolName = protocolName;
	}

	@Override
	public void run() {

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			final Class clz = Class.forName(classname);
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					//超时handler（当服务器端与客户端在指定时间以上没有任何进行通信，则会关闭响应的通道，主要为减小服务端资源占用）
					//此处设置为100s,即100s内双方无数据交互则关闭通道
					ch.pipeline().addLast(new ReadTimeoutHandler(10)).addLast(
							(ChannelHandler) clz.newInstance());
				}
			});

			// Start the client.
//			f = b.connect(host, port).addListener(
//					new ConnectionListener(this, host, port, classname,
//							protocolName)).sync();
			f = b.connect(host, port);
			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}

	}

}

/*class ConnectionListener implements ChannelFutureListener {

	private NettyClient client;
	private String host;
	private int port;
	private String classname;
	private String protocolName;

	public ConnectionListener(NettyClient client, String host, int port,
			String classname, String protocolName) {

		this.client = client;
		this.host = host;
		this.port = port;
		this.classname = classname;
		this.protocolName = protocolName;

	}

	@Override
	public void operationComplete(ChannelFuture channelFuture) throws Exception {

		if (!channelFuture.isSuccess()) {
			Thread.sleep(1000 * 300);
			System.out.println("Reconnect");
			new NettyClient(host, port, classname, protocolName).start();

		}

	}

}*/