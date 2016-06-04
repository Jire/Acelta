package com.acelta.net.http

import com.acelta.channelType
import com.acelta.group
import io.netty.bootstrap.ServerBootstrap

object HTTPServer {

	const val DEFAULT_PORT = 43595

	fun bind(port: Int = DEFAULT_PORT) = ServerBootstrap().group(group)
			.channel(channelType).childHandler(HTTPInitializer).bind(port)

}