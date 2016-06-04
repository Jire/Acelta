package com.acelta.net.game

import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

@Sharable
internal object GameChannelInitializer : ChannelInitializer<SocketChannel>() {

	override fun initChannel(ch: SocketChannel) {
		ch.pipeline().addFirst(GameDecoder())
	}

}