package com.acelta.net

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import java.net.InetSocketAddress

object Server {

	const val DEFAULT_PORT = 43594

	private val bootstrap = ServerBootstrap()
	private val group = NioEventLoopGroup()
	private val channel = NioServerSocketChannel::class.java

	fun bind(port: Int = DEFAULT_PORT) = bind(InetSocketAddress(port))

	fun bind(address: InetSocketAddress) = with(bootstrap) {
		group(group)
		channel(channel)
		childHandler(Initializer)
		bind(address)
	}

}