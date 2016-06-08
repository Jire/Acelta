package com.acelta.packet.incoming

import com.acelta.net.game.Session
import org.reflections.Reflections

abstract class PacketConductor(`package`: String, packetCapacity: Int = 256) {

	private val incoming = arrayOfNulls<IncomingPacket>(packetCapacity)

	init {
		Reflections("${javaClass.`package`.name}.$`package`")
				.getSubTypesOf(IncomingPacket::class.java).forEach {
			val obj = it.kotlin.objectInstance!!
			for (id in obj.ids) this[id] = obj
		}
	}

	operator fun set(id: Int, packet: IncomingPacket) {
		incoming[id] = packet
	}

	fun receive(id: Int, session: Session) {
		val packet = incoming[id] ?: return
		packet.sessionReceive(session, id)
	}

}