package com.acelta.net

import com.acelta.packet.ByteBufPacketeer
import com.acelta.util.nums.usin
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import java.util.concurrent.atomic.AtomicReference

internal class Handler : ByteToMessageDecoder() {

	private val session = AtomicReference<Session>()

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) = with(session.get()!!) {
		read = ByteBufPacketeer(buf)
		val id = byte.usin
		conductor.get().receive(id, this)
	}

	override fun channelRegistered(ctx: ChannelHandlerContext) = with(ctx.channel()) {
		session.set(Session(this))
		//println("Registered session from ${remoteAddress()}")
	}

	override fun channelUnregistered(ctx: ChannelHandlerContext) {
		println("Unregistered session from ${ctx.channel().remoteAddress()}")
		session.get().disconnect()
		session.set(null)
		ctx.close()
	}

}