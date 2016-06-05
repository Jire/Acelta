package com.acelta.net.http

import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpServerCodec

@Sharable
internal object HTTPInitializer : ChannelInitializer<SocketChannel>() {

	override fun initChannel(ch: SocketChannel) {
		ch.pipeline()
				.addLast(HttpServerCodec(256, 512, 512, false, 64))
				.addLast(HTTPHandler)
	}

}