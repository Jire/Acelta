package com.acelta.packet.outgoing

import com.acelta.game.Player
import com.acelta.net.Session
import com.acelta.util.Offsetter

class PlayerSend(override val it: Player, val ses: Session = it.session) : Offsetter<Player>