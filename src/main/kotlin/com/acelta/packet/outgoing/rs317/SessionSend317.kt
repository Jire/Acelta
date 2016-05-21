package com.acelta.packet.outgoing.rs317

import com.acelta.packet.outgoing.SessionSend
import com.acelta.util.nums.a
import com.acelta.util.nums.byte
import com.acelta.util.nums.leA
import com.acelta.util.nums.short

fun SessionSend.handshakeResponse(sessionKey: Long, id: Int) = it + 0L + id.byte + sessionKey

fun SessionSend.loginResponse(id: Int, crown: Int, flagged: Boolean) = it + id.byte + crown.byte + flagged

fun SessionSend.playerDetails(member: Boolean, index: Int) = it + 249.byte + member.byte.a + index.short.leA