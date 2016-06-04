package com.acelta.packet.incoming

import com.acelta.net.game.Session

abstract class IncomingPacket(vararg val ids: Int) {

	abstract fun Session.receive(id: Int)

	// workaround to allow external `Session.receive(id)` access
	fun sessionReceive(session: Session, id: Int) = session.receive(id)

}