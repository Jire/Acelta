package com.acelta.packet

import com.acelta.net.Session
import com.acelta.packet.incoming.Packet
import com.acelta.packet.incoming.guest.Handshake
import com.acelta.packet.incoming.guest.Login
import com.acelta.packet.outgoing.handshakeResponse
import com.acelta.packet.outgoing.loginResponse
import org.reflections.Reflections

abstract class PacketConductor(packageExtension: String, packetCapacity: Int = 256) {

	private val incoming = arrayOfNulls<Packet<*>>(packetCapacity)

	init {
		val ref = Reflections("${javaClass.`package`.name}.$packageExtension")
		for (packet in ref.getSubTypesOf(Packet::class.java)) {
			val obj = packet.kotlin.objectInstance!!
			this[obj.id] = obj
		}
	}

	operator fun set(id: Int, packet: Packet<*>) {
		incoming[id] = packet
	}

	fun receive(id: Int, session: Session, input: Packeteer) {
		val packet = incoming[id] ?: return
		packet(input, session)
	}

	object Guest : PacketConductor("incoming.guest") {
		init {
			// Simplistic packet handlers for login, should be done in a plugin.
			Handshake { nameHash -> handshakeResponse(2, 0).flush() }
			Login { ver, rel, hd, uid, user, pass -> loginResponse(2, 2, false).flush() }
		}
	}

	object Game : PacketConductor("incoming.game")

}