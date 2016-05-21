package com.acelta.packet.incoming.rs317.guest

import com.acelta.net.Session
import com.acelta.packet.incoming.Packet
import com.acelta.util.nums.usin

interface LoginListener {
	fun Session.on(version: Int, release: Int, highDetail: Boolean, uid: Int, username: String, password: String)
}

object Login : Packet<LoginListener>(16) {

	override fun Session.receive() {
		val fullLength = byte.usin
		if (readable < fullLength) return

		val version = 255 - byte.usin
		val release = short.usin
		val highDetail = boolean

		skip(9 * Integer.BYTES) // CRC-8 checksums

		val sessionBlockLength = byte.usin
		if (readable < sessionBlockLength) return
		byte.usin // session block ID
		skip(4 * Integer.BYTES) // session block keys

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