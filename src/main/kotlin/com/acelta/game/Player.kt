package com.acelta.game

import com.acelta.game.world.Position
import com.acelta.net.Session

class Player(id: Int, position: Position, val session: Session) : Entity(id, position)