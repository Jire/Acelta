package com.acelta.net

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

@ChannelHandler.Sharable
internal object Initializer : ChannelInitializer<SocketChannel>() {

	override fun initChannel(ch: SocketChannel) {
		ch.pipeline().addFirst(Handler())
	}

}