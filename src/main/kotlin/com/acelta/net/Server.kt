package com.acelta.net

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ServerChannel
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import java.net.InetSocketAddress

object Server {

	const val DEFAULT_PORT = 43594

	val epoll = Epoll.isAvailable()

	private val bootstrap = ServerBootstrap()
	private val group = if (epoll) EpollEventLoopGroup() else NioEventLoopGroup()
	private val channel = (if (epoll) EpollServerSocketChannel::class.java
	else NioServerSocketChannel::class.java) as Class<ServerChannel>

	fun bind(port: Int = DEFAULT_PORT) = bind(InetSocketAddress(port))

	fun bind(address: InetSocketAddress) = with(bootstrap) {
		group(group)
		channel(channel)
		childHandler(Initializer)
		bind(address)
	}

}