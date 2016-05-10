package com.acelta.net

import com.acelta.packet.GuestPacketConductor
import com.acelta.packet.PacketConductor
import com.acelta.packet.Packeteer
import io.netty.buffer.PooledByteBufAllocator
import io.netty.channel.socket.SocketChannel
import java.util.concurrent.atomic.AtomicReference

class Session(val channel: SocketChannel) : Packeteer(PooledByteBufAllocator.DEFAULT.buffer()) {

	val conductor: AtomicReference<PacketConductor> = AtomicReference(GuestPacketConductor)

	fun flush() {
		channel.writeAndFlush(content())
		clear()
	}

}