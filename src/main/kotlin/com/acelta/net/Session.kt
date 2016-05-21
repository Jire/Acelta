package com.acelta.net

import com.acelta.game.Player
import com.acelta.packet.ByteBufPacketeer
import com.acelta.packet.PacketConductor
import com.acelta.packet.SplitPacketeer
import com.acelta.packet.outgoing.SessionSend
import io.netty.channel.Channel
import java.util.concurrent.atomic.AtomicReference

class Session(val channel: Channel, override var write: ByteBufPacketeer = ByteBufPacketeer(
		channel.alloc().buffer(9))) : SplitPacketeer<ByteBufPacketeer>() {

	val send = SessionSend(this)

	val conductor: AtomicReference<PacketConductor> = AtomicReference(PacketConductor.Guest)
	@Volatile lateinit var player: Player

	fun flush() = with(write.content()) {
		channel.writeAndFlush(retain(), channel.voidPromise())
		clear()
	}

	fun disconnect() {
		write.release()
		read = null

		channel.close()
	}

}