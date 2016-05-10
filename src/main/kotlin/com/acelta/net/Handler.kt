package com.acelta.net

import io.netty.channel.ChannelHandlerAdapter
import io.netty.channel.ChannelHandlerContext

internal class Handler : ChannelHandlerAdapter() {

	private var session: Session? = null

	override fun channelRegistered(ctx: ChannelHandlerContext) {
		// TODO create a DoS prevention service
		session = Session(ctx.channel())
		ctx.attr(SESSION).set(session)
	}

	override fun channelUnregistered(ctx: ChannelHandlerContext) {
		session?.disconnect()
		ctx.attr(SESSION).remove()
		session = null
	}

}