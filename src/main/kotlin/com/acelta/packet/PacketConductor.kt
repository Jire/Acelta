package com.acelta.packet

import com.acelta.net.Session
import com.acelta.packet.incoming.Packet
import com.acelta.packet.incoming.guest.Handshake
import com.acelta.packet.incoming.guest.Login
import com.acelta.packet.outgoing.handshakeResponse
import com.acelta.packet.outgoing.loginResponse

abstract class PacketConductor(packetCapacity: Int = 256) {

	private val incoming = arrayOfNulls<Packet<*>>(packetCapacity)

	operator fun set(id: Int, packet: Packet<*>) {
		incoming[id] = packet
	}

	fun receive(id: Int, session: Session, input: Packeteer) {
		val packet = incoming[id] ?: return
		packet(input, session)
	}

	object Guest : PacketConductor() {
		init {
			// Manually register packets
			this[Handshake.id] = Handshake
			this[Login.id] = Login

			// Simplistic packet handlers for login
			Handshake { nameHash -> handshakeResponse(2, 0).flush() }
			Login { ver, rel, hd, uid, user, pass -> loginResponse(2, 2, false).flush() }
		}
	}

	object Game : PacketConductor()

}