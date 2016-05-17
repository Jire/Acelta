package com.acelta.net

import com.acelta.game.Player
import com.acelta.packet.ByteBufPacketeer
import com.acelta.packet.PacketConductor
import com.acelta.packet.SplitPacketeer
import com.acelta.util.log
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import java.util.concurrent.atomic.AtomicReference

class Session(val channel: Channel, override var write: ByteBufPacketeer = ByteBufPacketeer(Unpooled.buffer())) :
		SplitPacketeer<ByteBufPacketeer>() {

	val conductor: AtomicReference<PacketConductor> = AtomicReference(PacketConductor.Guest)
	lateinit var player: Player

	fun flush() {
		write.retain()
		channel.writeAndFlush(write)
		write.content().clear()
	}

	fun disconnect() {
		log("DISCONNECTED?")

		channel.close()

		write.release()
		read?.release()
		read = null
	}

}