package com.acelta.packet.incoming.packets

import com.acelta.packet.incoming.Packet
import com.acelta.packet.usin

interface HandshakeListener { fun on(nameHash: Int) }

object Handshake : Packet<HandshakeListener>(14, {
	val nameHash = byte.usin

	it.dispatch { on(nameHash) }
})