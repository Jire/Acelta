package com.acelta.packet.incoming.packets

import com.acelta.packet.Packeteer
import com.acelta.packet.incoming.Packet
import com.acelta.packet.usin

interface HandshakeListener { fun on(nameHash: Int) }

object Handshake : Packet<HandshakeListener>(14) {

	override fun Packeteer.receive() {
		val nameHash = byte.usin

		dispatch { on(nameHash) }
	}

}