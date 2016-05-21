package com.acelta.packet.outgoing

import com.acelta.net.Session
import com.acelta.util.Offsetter
import com.acelta.util.nums.a
import com.acelta.util.nums.byte
import com.acelta.util.nums.leA
import com.acelta.util.nums.short

class SessionSend(override val it: Session) : Offsetter<Session> {

	fun handshakeResponse(sessionKey: Long, id: Int) = it + 0L + id.byte + sessionKey

	fun loginResponse(id: Int, crown: Int, flagged: Boolean) = it + id.byte + crown.byte + flagged

	fun playerDetails(member: Boolean, index: Int) = it + 249.byte + member.byte.a + index.short.leA

}