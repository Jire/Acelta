package com.acelta.packet

import com.acelta.net.Session
import com.acelta.packet.incoming.Packet
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

	object Guest : PacketConductor("incoming.rs317.guest")

	object Game : PacketConductor("incoming.rs317.game")

}