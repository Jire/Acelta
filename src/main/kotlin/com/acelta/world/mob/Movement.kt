package com.acelta.world.mob

import com.acelta.world.mob.player.Player
import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue
import java.lang.Math.abs
import java.lang.Math.max

class Movement(val mob: Mob) {

	private val xList = IntArrayFIFOQueue(64)
	private val yList = IntArrayFIFOQueue(64)

	var direction = Direction.NONE
	val moving: Boolean
		get() = direction != Direction.NONE
	var running = false

	var teleporting = false
	var regionChanging = false

	fun reset() {
		xList.clear()
		yList.clear()
	}

	fun addFirstStep(nextX: Int, nextY: Int) {
		reset()
		addStep(nextX, nextY)
	}

	fun addStep(nextX: Int, nextY: Int) {
		val currentX = if (xList.isEmpty) mob.position.x else xList.firstInt()
		val currentY = if (yList.isEmpty) mob.position.y else yList.firstInt()
		addStep(currentX, currentY, nextX, nextY)
	}

	private fun addStep(currentX: Int, currentY: Int, nextX: Int, nextY: Int) {
		var deltaX = nextX - currentX
		var deltaY = nextY - currentY

		val max = max(abs(deltaX), abs(deltaY))
		repeat(max) {
			if (deltaX < 0) deltaX++
			else if (deltaX > 0) deltaX--

			if (deltaY < 0) deltaY++
			else if (deltaY > 0) deltaY--

			val x = nextX - deltaX
			val y = nextY - deltaY

			xList.enqueueFirst(x)
			yList.enqueueFirst(y)
		}
	}

	fun tick() = with(mob.position) {
		if (xList.isEmpty) direction = Direction.NONE
		else {
			var nextX = xList.dequeueLastInt()
			var nextY = yList.dequeueLastInt()
			direction = Direction.between(x, y, nextX, nextY)
			x = nextX
			y = nextY

			if (running && !xList.isEmpty) {
				nextX = xList.dequeueLastInt()
				nextY = yList.dequeueLastInt()
				direction = Direction.between(x, y, nextX, nextY)
				x = nextX
				y = nextY
			}

			val diffX = x - (centerX /* should really be last center X */ * 8)
			val diffY = y - (centerY /* should really be last center Y */ * 8)
			if (diffX < 16 || diffX >= 88 || diffY < 16 || diffY >= 88) {
				//regionChanging = true
				// TODO: set last region X and Y
			}
		}

		if (regionChanging && mob is Player) mob.send.sector()
	}

}