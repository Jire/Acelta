package com.acelta.packet.outgoing.session

import com.acelta.net.game.Session
import com.acelta.util.nums.byte

class SessionSend(val session: Session) {

	fun loginResponse(id: Int) = session + id.byte

}