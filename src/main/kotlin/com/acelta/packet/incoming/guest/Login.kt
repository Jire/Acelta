package com.acelta.packet.incoming.guest

import com.acelta.net.Session
import com.acelta.packet.incoming.Packet
import com.acelta.util.nums.usin

interface LoginListener {
	fun Session.on(version: Int, release: Int, highDetail: Boolean, uid: Int, username: String, password: String)
}

object Login : Packet<LoginListener>(16) {

	override fun Session.receive() {
		val version = 0xFF - byte.usin
		val release = short.usin
		val highDetail = boolean

		skip(9 /* CRCs */ + 1 /* block length */ + 1 /* block ID */ + 4 /* ISAAC keys */)

		val uid = int
		val username = string
		val password = string

		dispatch { on(version, release, highDetail, uid, username, password) }
	}

	inline operator fun invoke(crossinline on: (Session).(Int, Int, Boolean, Int, String, String) -> Unit)
			= Login.attach(object : LoginListener {
		override fun Session.on(version: Int, release: Int, highDetail: Boolean,
		                        uid: Int, username: String, password: String)
				= on(version, release, highDetail, uid, username, password)
	})

}