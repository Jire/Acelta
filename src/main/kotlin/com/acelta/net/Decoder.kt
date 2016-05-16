package com.acelta.net

import com.acelta.packet.Packeteer
import com.acelta.packet.usin
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

internal class Decoder : ByteToMessageDecoder() {

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
		val session = ctx.attr(Session.KEY).get() ?: return
		with(Packeteer(buf)) {
			val id = byte.usin
			session.conductor.get().receive(id, session, this)
		}
	}

}