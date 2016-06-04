package com.acelta.packet.incoming

import com.acelta.net.game.Session
import org.reflections.Reflections

abstract class PacketConductor(`package`: String, packetCapacity: Int = 256) {

	private val incoming = arrayOfNulls<ListenableIncomingPacket<*>>(packetCapacity)

	init {
		val ref = Reflections("${javaClass.`package`.name}.$`package`")
		for (packet in ref.getSubTypesOf(ListenableIncomingPacket::class.java)) {
			val obj = packet.kotlin.objectInstance!!
			for (id in obj.ids) this[id] = obj
		}
	}

	operator fun set(id: Int, packet: ListenableIncomingPacket<*>) {
		incoming[id] = packet
	}

	fun receive(id: Int, session: Session) {
		val packet = incoming[id] ?: return
		packet.sessionReceive(session, id)
	}

}