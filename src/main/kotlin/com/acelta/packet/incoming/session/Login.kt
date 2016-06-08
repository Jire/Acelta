package com.acelta.packet.incoming.session

import com.acelta.net.game.Session
import com.acelta.packet.incoming.IncomingPacket
import com.acelta.util.nums.usin
import com.acelta.world.World

object Login : IncomingPacket(16, 18 /* reconnect */) {

	override fun Session.receive(id: Int) {
		val fullLength = byte.usin
		if (readable < fullLength) return

		skip(8 * Integer.BYTES) // CRC-32

		byte.usin // ISAAC session block ID
		skip(4 * Integer.BYTES) // ISAAC session keys

		int // UID
		val username = string
		val password = string

		World.register(this, id == 18, username, password)
	}

}