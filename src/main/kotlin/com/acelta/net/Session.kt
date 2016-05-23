package com.acelta.net

import com.acelta.game.Player
import com.acelta.packet.ByteBufPacketeer
import com.acelta.packet.PacketConductor
import com.acelta.packet.SplitPacketeer
import com.acelta.packet.outgoing.SessionSend
import io.netty.channel.Channel
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater

class Session(val channel: Channel, override var read: ByteBufPacketeer = ByteBufPacketeer(),
              override var write: ByteBufPacketeer = ByteBufPacketeer(
		              channel.alloc().buffer(9))) : SplitPacketeer<ByteBufPacketeer>() {

	companion object {
		val CONDUCTOR_UPDATER = AtomicReferenceFieldUpdater
				.newUpdater<Session, PacketConductor>(Session::class.java, PacketConductor::class.java, "conductor")
	}

	val send = SessionSend(this)

	@Volatile var conductor: PacketConductor = PacketConductor.Guest
	@Volatile lateinit var player: Player

	private val flushTask = Runnable {
		with(write.data) {
			channel.writeAndFlush(retain(), channel.voidPromise())
			clear()
		}
	}

	fun flush() = channel.eventLoop().execute(flushTask)

	fun disconnect() {
		read.data.release()
		write.data.release()

		channel.close()
	}

}