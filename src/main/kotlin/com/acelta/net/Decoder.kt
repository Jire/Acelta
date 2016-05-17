package com.acelta.net

import com.acelta.packet.ByteBufPacketeer
import com.acelta.util.nums.usin
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

internal class Decoder : ByteToMessageDecoder() {

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
		with (ctx.attr(Session.KEY).get() ?: return) {
			read = ByteBufPacketeer(buf)
			val id = byte.usin
			conductor.get().receive(id, this)
		}
	}

}