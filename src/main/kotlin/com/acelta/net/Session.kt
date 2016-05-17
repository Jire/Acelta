package com.acelta.net

import com.acelta.game.Player
import com.acelta.packet.ByteBufPacketeer
import com.acelta.packet.PacketConductor
import com.acelta.packet.SplitPacketeer
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.util.AttributeKey
import java.util.concurrent.atomic.AtomicReference

class Session(val channel: Channel, write: ByteBufPacketeer = ByteBufPacketeer(Unpooled.buffer())) :
		SplitPacketeer<ByteBufPacketeer>(write = write) {

	companion object {
		val KEY = AttributeKey.newInstance<Session>("SESSION")
	}

	val conductor: AtomicReference<PacketConductor> = AtomicReference(PacketConductor.Guest)
	lateinit var player: Player

	fun flush() = with (write) {
		retain()
		channel.writeAndFlush(this)
		content().clear()
	}

	fun disconnect() {
		channel.close()

		write.release()
		read?.release()
		read = null
	}

}