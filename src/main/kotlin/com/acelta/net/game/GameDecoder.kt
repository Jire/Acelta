package com.acelta.net.game

import com.acelta.util.nums.usin
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

internal class GameDecoder : ByteToMessageDecoder() {

	private var session: Session? = null

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any?>) = with(session!!) {
		read.buf = buf
		if (readable > 0) {
			val id = byte.usin
			conductor.receive(id, this)
		}
	}

	override fun channelActive(ctx: ChannelHandlerContext) {
		session = Session(ctx.channel())
	}

	override fun channelUnregistered(ctx: ChannelHandlerContext) {
		session?.disconnect()
		session = null

		ctx.close()
	}

	override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
	}

}