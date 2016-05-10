package com.acelta.net

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

internal class Handler : ChannelInboundHandlerAdapter() {

	private var session: Session? = null

	override fun channelRegistered(ctx: ChannelHandlerContext) {
		// TODO create a DoS prevention service
		session = Session(ctx.channel())
		ctx.attr(KEY_SESSION).set(session)
	}

	override fun channelUnregistered(ctx: ChannelHandlerContext) {
		session?.disconnect()
		ctx.attr(KEY_SESSION).remove()
		session = null
		ctx.close()
	}

}