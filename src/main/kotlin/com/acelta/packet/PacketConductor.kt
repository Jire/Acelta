package com.acelta.packet

import com.acelta.packet.incoming.Packet

object PacketConductor {

	private val incoming = arrayOfNulls<Packet<*>>(256)

	fun incoming(id: Int, packeteer: Packeteer) {
		val packet = incoming[id] ?: return
		packet(packeteer)
	}

}