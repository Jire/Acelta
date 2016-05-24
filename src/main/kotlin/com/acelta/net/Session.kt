package com.acelta.net

import com.acelta.game.Player
import com.acelta.packet.ByteBufPacketeer
import com.acelta.packet.PacketConductor
import com.acelta.packet.SplitPacketeer
import com.acelta.packet.outgoing.SessionSend
import io.netty.channel.Channel
import io.netty.util.internal.PlatformDependent.newAtomicReferenceFieldUpdater

class Session(val channel: Channel, override var read: ByteBufPacketeer = ByteBufPacketeer(),
              override var write: ByteBufPacketeer = ByteBufPacketeer(
		              channel.alloc().directBuffer(9))) : SplitPacketeer<ByteBufPacketeer>() {

	companion object {
		val CONDUCTOR_UPDATER
				= newAtomicReferenceFieldUpdater<Session, PacketConductor>(Session::class.java, "conductor")
	}

	val send = SessionSend(this)

	@Volatile var conductor: PacketConductor = PacketConductor.Guest
	@Volatile lateinit var player: Player

	private val flushTask = Runnable {
		with(write.buf) {
			channel.writeAndFlush(retain(), channel.voidPromise())
			clear()
		}
	}

	fun flush() = channel.eventLoop().execute(flushTask)

	fun disconnect() {
		channel.close()
	}

}