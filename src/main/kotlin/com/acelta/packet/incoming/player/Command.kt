package com.acelta.packet.incoming.player

import com.acelta.net.game.Session
import com.acelta.packet.incoming.IncomingPacket
import com.acelta.util.nums.usin

object Command : IncomingPacket(37) {

	override fun Session.receive(id: Int) {
		val size = byte.usin
		if (readable < size) return

		val line = string
	}

}