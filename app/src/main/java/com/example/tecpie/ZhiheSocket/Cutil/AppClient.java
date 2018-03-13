package com.example.tecpie.ZhiheSocket.Cutil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class AppClient extends Thread {
	
	private ChannelFuture f;

	@Override
	public void run() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					//此处未设置超时
					ch.pipeline().addLast(new AppClientHandle());
				}
			});
			// Start the client.
			//此处网关的ip与端口已被设定死，因为网关开放的服务ip与端口不会改变
			f = b.connect("192.168.35.1", 9001);
			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		new AppClient().start();
	}
}

