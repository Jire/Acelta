package com.acelta.packet.outgoing

import com.acelta.net.Session
import com.acelta.packet.a
import com.acelta.packet.le
import com.acelta.packet.toByte

fun Session.handshakeResponse(id: Int, sessionKey: Long) = apply { this + 0L + id.toByte() + sessionKey }

fun Session.loginResponse(id: Int, crown: Int, flagged: Boolean) = apply {
	this + id.toByte() + crown.toByte() + flagged
}

fun Session.playerDetails(member: Boolean, index: Int) = apply {
	this + 249.toByte() + member.toByte().a() + index.toShort().le().a()
}