package com.acelta.net

import com.acelta.net.Session.Companion.CONDUCTOR_UPDATER
import com.acelta.util.nums.usin
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.util.internal.PlatformDependent.newAtomicReferenceFieldUpdater

internal class Handler : ByteToMessageDecoder() {

	companion object {
		private val SESSION_UPDATER = newAtomicReferenceFieldUpdater<Handler, Session>(Handler::class.java, "session")
	}

	@Volatile private lateinit var session: Session

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>)
			= with(SESSION_UPDATER.get(this)!!) {

		read.data = buf
		val id = byte.usin
		CONDUCTOR_UPDATER.get(this).receive(id, this)
	}

	override fun channelActive(ctx: ChannelHandlerContext) {
		SESSION_UPDATER.set(this, Session(ctx.channel()))
	}

	override fun channelUnregistered(ctx: ChannelHandlerContext) {
		SESSION_UPDATER.get(this)?.disconnect()
		SESSION_UPDATER.lazySet(this, null)
		ctx.close()
	}

	override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
	}

}