package com.acelta.game

import com.acelta.game.world.Position

abstract class Entity(val id: Int, val position: Position) {

	abstract fun tick()

}