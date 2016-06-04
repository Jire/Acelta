package com.acelta.net.http

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.*
import io.netty.handler.codec.http.HttpResponseStatus.OK
import io.netty.handler.codec.http.HttpVersion.HTTP_1_1
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import java.nio.file.Files
import java.nio.file.Paths

internal class HTTPHandler : SimpleChannelInboundHandler<HttpRequest>() {

	companion object {
		private val responseCache = Object2ObjectArrayMap<String, FullHttpResponse>(8)
	}

	override fun channelRead0(ctx: ChannelHandlerContext, msg: HttpRequest) {
		val uri = msg.uri()
		var response = responseCache[uri]
		if (response == null) {
			val bytes = Files.readAllBytes(Paths.get("data/cache/", uri))
			val buffer = ctx.alloc().directBuffer(bytes.size)
			buffer.writeBytes(bytes)
			response = DefaultFullHttpResponse(HTTP_1_1, OK, buffer, false)
			responseCache[uri] = response
		}
		ctx.writeAndFlush(response.retain(), ctx.voidPromise())
	}

}