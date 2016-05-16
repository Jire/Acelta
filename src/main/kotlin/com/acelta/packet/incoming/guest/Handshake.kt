package com.acelta.packet.incoming.guest

import com.acelta.net.Session
import com.acelta.packet.Packeteer
import com.acelta.packet.incoming.Packet
import com.acelta.packet.usin

interface HandshakeListener { fun on(session: Session, nameHash: Int) }

object Handshake : Packet<HandshakeListener>(14) {

	override fun Packeteer.receive(session: Session) {
		val nameHash = byte.usin

		dispatch { on(session, nameHash) }
	}

	inline operator fun invoke(crossinline body: (Session).(Int) -> Any) = attach(object : HandshakeListener {
		override fun on(session: Session, nameHash: Int) {
			session.body(nameHash)
		}
	})

}