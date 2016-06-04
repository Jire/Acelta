package com.acelta.world.mob

import com.acelta.world.mob.player.Player
import com.acelta.world.region.regionX
import com.acelta.world.region.regionY
import it.unimi.dsi.fastutil.ints.IntArrayList
import java.lang.Math.abs
import java.lang.Math.max

// based on ScapeEmulator's `WalkingQueue`

class Movement(val mob: Mob) {

	private val xList = IntArrayList(64)
	private val yList = IntArrayList(64)

	var firstDirection = Direction.NONE
	var secondDirection = Direction.NONE
	var running = false
	val moving: Boolean
		get() = firstDirection != Direction.NONE

	var teleporting = false
	var regionChanging = false

	fun reset() {
		xList.clear()
		yList.clear()

		running = false
	}

	fun addFirstStep(nextX: Int, nextY: Int) {
		reset()
		addStep(nextX, nextY)
	}

	fun addStep(nextX: Int, nextY: Int) {
		val currentX = if (xList.isEmpty) mob.position.x else xList.topInt()
		val currentY = if (yList.isEmpty) mob.position.y else yList.topInt()
		addStep(currentX, currentY, nextX, nextY)
	}

	private fun addStep(currentX: Int, currentY: Int, nextX: Int, nextY: Int) {
		var deltaX = nextX - currentX
		var deltaY = nextY - currentY

		repeat(max(abs(deltaX), abs(deltaY))) {
			if (deltaX < 0) deltaX++
			else if (deltaX > 0) deltaX--

			if (deltaY < 0) deltaY++
			else if (deltaY > 0) deltaY--

			val x = nextX - deltaX
			val y = nextY - deltaY

			xList.add(x)
			yList.add(y)
		}
	}

	fun tick() = with(mob.position) {
		firstDirection = Direction.NONE
		secondDirection = Direction.NONE

		if (xList.isNotEmpty()) {
			var nextX = xList.popInt()
			var nextY = yList.popInt()

			firstDirection = Direction.between(x, y, nextX, nextY)
			x = nextX
			y = nextY

			if (running && xList.isNotEmpty()) {
				nextX = xList.popInt()
				nextY = yList.popInt()

				secondDirection = Direction.between(x, y, nextX, nextY)
				x = nextX
				y = nextY
			}

			val diffX = x - regionX /* should really be last region X */ * 8
			val diffY = y - regionY /* should really be last region Y */ * 8
			if (diffX < 16 || diffX >= 88 || diffY < 16 || diffY >= 88) {
				regionChanging = true
				// TODO: set last region X and Y
			}
		}

		if (regionChanging && mob is Player) mob.send.sector()
	}

}