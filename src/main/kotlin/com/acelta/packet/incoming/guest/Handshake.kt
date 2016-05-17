package com.acelta.packet.incoming.guest

import com.acelta.net.Session
import com.acelta.packet.incoming.Packet
import com.acelta.util.nums.usin

interface HandshakeListener { fun Session.on(nameHash: Int) }

object Handshake : Packet<HandshakeListener>(14) {

	override fun Session.receive() {
		val nameHash = byte.usin

		dispatch { on(nameHash) }
	}

	inline operator fun invoke(crossinline on: (Session).(Int) -> Unit) = attach(object : HandshakeListener {
		override fun Session.on(nameHash: Int) = on(nameHash)
	})

}