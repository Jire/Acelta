package com.acelta.world.mob

import com.acelta.world.Entity
import com.acelta.world.mob.Movement
import com.acelta.world.Position

abstract class Mob(val index: Int, val position: Position) : Entity() {

	val movement = Movement(this)

	abstract fun tick()

}