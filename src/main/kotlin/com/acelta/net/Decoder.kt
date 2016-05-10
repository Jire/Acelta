package com.acelta.net

import com.acelta.packet.PacketConductor
import com.acelta.packet.Packeteer
import com.acelta.packet.usin
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

internal class Decoder : ByteToMessageDecoder() {

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
		with(Packeteer(buf)) {
			val id = byte.usin
			PacketConductor.incoming(id, this)
		}
	}

}