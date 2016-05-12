package com.acelta.packet.incoming.guest

import com.acelta.net.Session
import com.acelta.packet.Packeteer
import com.acelta.packet.incoming.Packet
import com.acelta.packet.usin

interface LoginListener {
	fun on(session: Session, version: Int, release: Int, highDetail: Boolean,
	       uid: Int, username: String, password: String)
}

object Login : Packet<LoginListener>(16) {

	override fun Packeteer.receive(session: Session) {
		val version = 0xFF - byte.usin
		val release = short.usin
		val highDetail = boolean

		skip(9 /* CRCs */ + 1 /* block length */ + 1 /* block ID */ + 4 /* ISAAC keys */)

		val uid = int
		val username = string
		val password = string

		dispatch { on(session, version, release, highDetail, uid, username, password) }
	}

	inline operator fun invoke(crossinline body: (Session).(Int, Int, Boolean, Int, String, String) -> Any)
			= Login.attach(object : LoginListener {
		override fun on(session: Session, version: Int, release: Int, highDetail: Boolean,
		                uid: Int, username: String, password: String) {
			session.body(version, release, highDetail, uid, username, password)
		}
	})

}