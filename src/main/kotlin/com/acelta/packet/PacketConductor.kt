package com.acelta.packet

import com.acelta.game.Player
import com.acelta.game.world.Position
import com.acelta.net.Session
import com.acelta.packet.incoming.Packet
import com.acelta.packet.incoming.guest.Handshake
import com.acelta.packet.incoming.guest.Login
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
		val packet = incoming[id] ?: return
		packet(session)
	}

	object Guest : PacketConductor("incoming.guest") {
		init {
			// Simplistic packet handlers for login, should be done in a plugin.

			this[18] = Login // reconnecting

			Handshake { nameHash -> send.handshakeResponse(0, 0); flush() }

			Login { ver, rel, hd, uid, user, pass ->
				val index = 1

				with(send) {
					loginResponse(2, 2, false)
					playerDetails(true, index)
				}

				player = Player(index, Position(), this)
				conductor.set(Game)

				with(player.send) {
					for (i in 0..20) setSkill(i, 1, 1)
					setInterface(0, 2423)
					setInterface(1, 3917)
					setInterface(2, 638)
					setInterface(3, 3213)
					setInterface(4, 1644)
					setInterface(5, 5608)
					setInterface(6, 1151)
					setInterface(8, 5065)
					setInterface(9, 5715)
					setInterface(10, 2449)
					setInterface(11, 4445)
					setInterface(12, 147)
					setInterface(13, 2699)

					mapRegion()

					msg("Welcome to Acelta.")
				}
				flush()

				println("LOGIN (user: $user, pass: $pass)")
			}
		}
	}

	object Game : PacketConductor("incoming.game")

}