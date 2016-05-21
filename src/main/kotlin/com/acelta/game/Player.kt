package com.acelta.game

import com.acelta.game.world.Position
import com.acelta.net.Session
import com.acelta.packet.outgoing.PlayerSend

class Player(id: Int, position: Position, val session: Session) : Entity(id, position) {

	val send = PlayerSend(this)

}