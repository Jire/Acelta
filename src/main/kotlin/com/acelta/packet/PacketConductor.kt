package com.acelta.packet

import com.acelta.net.Session
import com.acelta.packet.incoming.Packet
import com.acelta.packet.incoming.guest.Handshake
import com.acelta.packet.incoming.guest.Login

abstract class PacketConductor(packetCapacity: Int = 256) {

	private val incoming = arrayOfNulls<Packet<*>>(packetCapacity)

	init {
		incoming[Handshake.id] = Handshake
		incoming[Login.id] = Login
	}

	fun incoming(id: Int, session: Session, input: Packeteer) {
		val packet = incoming[id] ?: return
		packet(input, session)
	}

	object Guest : PacketConductor()
	object Game : PacketConductor()

}