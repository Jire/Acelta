package com.acelta.packet.outgoing

import com.acelta.net.Session
import com.acelta.packet.a
import com.acelta.packet.byte
import com.acelta.packet.leA
import com.acelta.packet.short

fun Session.handshakeResponse(id: Int, sessionKey: Long) = this + 0L + id.byte + sessionKey

fun Session.loginResponse(id: Int, crown: Int, flagged: Boolean) = this + id.byte + crown.byte + flagged

fun Session.playerDetails(member: Boolean, index: Int) = this + 249.byte + member.byte.a + index.short.leA