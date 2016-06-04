package com.acelta.packet.incoming.session

import com.acelta.net.game.Session
import com.acelta.packet.incoming.ListenableIncomingPacket
import com.acelta.util.nums.usin

interface LoginListener {
	fun Session.on(uid: Int, username: String, password: String)
}

object Login : ListenableIncomingPacket<LoginListener>(16, 18 /* reconnect */) {

	override fun Session.receive(id: Int) {
		val fullLength = byte.usin
		if (readable < fullLength) return

		skip(8 * Integer.BYTES) // CRC-32

		val rsaLength = byte.usin
		if (readable < rsaLength) return

		byte.usin // ISAAC session block ID
		skip(4 * Integer.BYTES) // ISAAC session keys

		val uid = int
		val username = string
		val password = string

		dispatch { on(uid, username, password) }
	}

	inline operator fun invoke(crossinline on: (Session).(uid: Int, username: String, password: String) -> Unit)
			= Login.attach(object : LoginListener {
		override fun Session.on(uid: Int, username: String, password: String) = on(uid, username, password)
	})

}