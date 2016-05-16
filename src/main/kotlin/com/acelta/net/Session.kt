package com.acelta.net

import com.acelta.game.Player
import com.acelta.packet.PacketConductor
import com.acelta.packet.Packeteer
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.util.AttributeKey
import java.util.concurrent.atomic.AtomicReference

class Session(val channel: Channel) : Packeteer(Unpooled.buffer()) {

	companion object {
		val KEY = AttributeKey.newInstance<Session>("SESSION")
	}

	val conductor: AtomicReference<PacketConductor> = AtomicReference(PacketConductor.Guest)
	lateinit var player: Player

	fun flush() {
		retain()
		channel.writeAndFlush(content())
		clear()
	}

	fun disconnect() {
		channel.close()
		release()
	}

}