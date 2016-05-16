package com.acelta.packet.outgoing

import com.acelta.net.Session
import com.acelta.util.nums.a
import com.acelta.util.nums.byte
import com.acelta.util.nums.leA
import com.acelta.util.nums.short

fun Session.handshakeResponse(id: Int, sessionKey: Long) = this + 0L + id.byte + sessionKey

fun Session.loginResponse(id: Int, crown: Int, flagged: Boolean) = this + id.byte + crown.byte + flagged

fun Session.playerDetails(member: Boolean, index: Int) = this + 249.byte + member.byte.a + index.short.leA