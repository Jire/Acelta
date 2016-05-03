package com.acelta.net

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

internal class Decoder : ByteToMessageDecoder() {

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
	}

}