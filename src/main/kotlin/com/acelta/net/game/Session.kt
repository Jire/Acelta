package com.acelta.net.game

import com.acelta.world.mob.player.Player
import com.acelta.packet.ByteBufPacketeer
import com.acelta.packet.incoming.PacketConductor
import com.acelta.packet.SplitPacketeer
import com.acelta.packet.incoming.SessionPacketConductor
import com.acelta.packet.outgoing.session.SessionSend
import io.netty.channel.Channel

class Session(val channel: Channel,
              override var read: ByteBufPacketeer = ByteBufPacketeer(),
              override var write: ByteBufPacketeer = ByteBufPacketeer(channel.alloc().directBuffer(9)))
: SplitPacketeer<ByteBufPacketeer>() {

	val send = SessionSend(this)

	var conductor: PacketConductor = SessionPacketConductor
	lateinit var player: Player

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