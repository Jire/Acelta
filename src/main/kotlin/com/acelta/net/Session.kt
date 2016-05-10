package com.acelta.net

import com.acelta.packet.PacketConductor
import com.acelta.packet.Packeteer
import io.netty.buffer.PooledByteBufAllocator
import io.netty.channel.Channel
import io.netty.util.AttributeKey
import java.util.concurrent.atomic.AtomicReference

val SESSION = AttributeKey.newInstance<Session>("SESSION")

class Session(val channel: Channel) : Packeteer(PooledByteBufAllocator.DEFAULT.buffer()) {

	val conductor: AtomicReference<PacketConductor> = AtomicReference(PacketConductor.Guest)

	fun flush() {
		channel.writeAndFlush(content())
		clear()
	}

	fun disconnect() {}

}