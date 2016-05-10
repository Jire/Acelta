package com.acelta.net

import com.acelta.packet.InitialPacketConductor
import com.acelta.packet.PacketConductor
import com.acelta.packet.Packeteer
import io.netty.buffer.PooledByteBufAllocator
import io.netty.channel.socket.SocketChannel

class Session(val channel: SocketChannel, var conductor: PacketConductor = InitialPacketConductor) :
		Packeteer(PooledByteBufAllocator.DEFAULT.buffer()) {

	fun flush() {
		channel.writeAndFlush(content())
		clear()
	}

}