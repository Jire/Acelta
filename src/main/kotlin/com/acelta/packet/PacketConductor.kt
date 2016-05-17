package com.acelta.packet

import com.acelta.game.Player
import com.acelta.game.world.Position
import com.acelta.net.Session
import com.acelta.packet.incoming.Packet
import com.acelta.packet.incoming.guest.Handshake
import com.acelta.packet.incoming.guest.Login
import com.acelta.packet.incoming.guest.LoginListener
import com.acelta.packet.outgoing.handshakeResponse
import com.acelta.packet.outgoing.loginResponse
import com.acelta.packet.outgoing.mapRegion
import com.acelta.packet.outgoing.playerDetails
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
		if (packet == null) println("Unhandled packet: $id")
		else {
			packet(session)
			println("Handled packet: $id")
		}
	}

	object Guest : PacketConductor("incoming.guest") {
		init {
			// Simplistic packet handlers for login, should be done in a plugin.
			Handshake { nameHash -> handshakeResponse(0, 0); flush() }
			Login.attach(object : LoginListener {
				override fun Session.on(version: Int, release: Int, highDetail: Boolean,
				                        uid: Int, username: String, password: String) {

				}
			})
			Login { ver, rel, hd, uid, user, pass ->
				val index = 1

				loginResponse(2, 2, false)
				playerDetails(true, index)

				player = Player(index, Position(), this)
				conductor.set(Game)

				player.mapRegion()

				flush()

				println("Login from $user, $pass")
			}
		}
	}

	object Game : PacketConductor("incoming.game")

}