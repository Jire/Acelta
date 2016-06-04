package com.acelta.net.game

import com.acelta.channelType
import com.acelta.group
import io.netty.bootstrap.ServerBootstrap
import java.net.InetSocketAddress

object GameServer {

	const val DEFAULT_PORT = 43594

	fun bind(port: Int = DEFAULT_PORT) = bind(InetSocketAddress(port))

	fun bind(address: InetSocketAddress) = ServerBootstrap().group(group)
			.channel(channelType).childHandler(GameChannelInitializer).bind(address)

}