package com.acelta.packet.incoming.player

import com.acelta.net.game.Session
import com.acelta.packet.incoming.IncomingPacket

object Ping : IncomingPacket(223) {

	override fun Session.receive(id: Int) {
	}
	
}