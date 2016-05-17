package com.acelta.net

import com.acelta.game.Player
import com.acelta.packet.ByteBufPacketeer
import com.acelta.packet.PacketConductor
import com.acelta.packet.SplitPacketeer
import io.netty.channel.Channel
import java.util.concurrent.atomic.AtomicReference

class Session(val channel: Channel, override var write: ByteBufPacketeer = ByteBufPacketeer(channel.alloc().buffer(9))) :
		SplitPacketeer<ByteBufPacketeer>() {

	val conductor: AtomicReference<PacketConductor> = AtomicReference(PacketConductor.Guest)
	@Volatile lateinit var player: Player

	fun flush() = with(write.content()) {
		retain()
		channel.writeAndFlush(this, channel.voidPromise())
		clear()
	}

	fun disconnect() {
		channel.close()

		write.release()
		read = null
	}

}