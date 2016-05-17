package com.acelta.packet

import com.acelta.game.Player
import com.acelta.game.world.Position
import com.acelta.net.Session
import com.acelta.packet.incoming.Packet
import com.acelta.packet.incoming.guest.Handshake
import com.acelta.packet.incoming.guest.Login
import com.acelta.packet.outgoing.handshakeResponse
import com.acelta.packet.outgoing.loginResponse
import com.acelta.packet.outgoing.mapRegion
import com.acelta.packet.outgoing.playerDetails
import com.acelta.util.log
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

	fun receive(id: Int, session: Session) {
		val packet = incoming[id]
		if (packet == null) log("UNHANDLED PACKET (id: $id)", 2)
		else {
			packet(session)
			log("HANDLED PACKET (id: $id)", 2)
		}
	}

	object Guest : PacketConductor("incoming.guest") {
		init {
			// Simplistic packet handlers for login, should be done in a plugin.
			Handshake { nameHash -> handshakeResponse(0, 0); log("HANDSHAKE (nameHash: $nameHash)", 2); flush() }
			Login { ver, rel, hd, uid, user, pass ->
				val index = 1

				loginResponse(2, 2, false)
				playerDetails(true, index)

				player = Player(index, Position(), this)
				conductor.set(Game)

				player.mapRegion()

				flush()

				log("LOGIN (user: $user, pass: $pass)", 2)
			}
		}
	}

	object Game : PacketConductor("incoming.game")

}