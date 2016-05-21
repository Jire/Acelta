package com.acelta.packet.outgoing

import com.acelta.net.Session
import com.acelta.util.Offsetter

class SessionSend(override val it: Session) : Offsetter<Session>