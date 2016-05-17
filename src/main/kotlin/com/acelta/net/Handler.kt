package com.acelta.net

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

internal class Handler : ChannelInboundHandlerAdapter() {

	private var session: Session? = null

	override fun channelRegistered(ctx: ChannelHandlerContext) {
		session = Session(ctx.channel())
		ctx.attr(Session.KEY).set(session)
	}

	override fun channelUnregistered(ctx: ChannelHandlerContext) {
		session?.disconnect()
		ctx.attr(Session.KEY).remove()
		session = null
		ctx.close()
	}

}