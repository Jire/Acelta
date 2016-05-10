package com.acelta.packet

import com.acelta.packet.incoming.Packet

abstract class PacketConductor(packetCapacity: Int = 256) {

	private val incoming = arrayOfNulls<Packet<*>>(packetCapacity)

	fun incoming(id: Int, packeteer: Packeteer) {
		val packet = incoming[id] ?: return
		packet(packeteer)
	}

	object Guest : PacketConductor()
	object Game : PacketConductor()

}