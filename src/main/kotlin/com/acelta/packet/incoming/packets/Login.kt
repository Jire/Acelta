package com.acelta.packet.incoming.packets

import com.acelta.packet.incoming.Packet
import com.acelta.packet.incoming.listeners.LoginListener
import com.acelta.packet.usin

object Login : Packet<LoginListener>(16, {
	val version = 0xFF - byte.usin
	val release = short.usin
	val highDetail = boolean

	for (i in 0..9) int // CRCs

	byte // block length
	byte // block ID

	for (i in 0..4) int // ISAAC keys

	val uid = int
	val username = string
	val password = string

	it.dispatch { on(version, release, highDetail, uid, username, password) }
})