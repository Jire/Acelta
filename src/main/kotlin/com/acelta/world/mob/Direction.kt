package com.acelta.world.mob

import com.acelta.world.Position

enum class Direction(val value: Int) {

	NONE(-1), NORTH_WEST(0), NORTH(1), NORTH_EAST(2), WEST(3), EAST(4), SOUTH_WEST(5), SOUTH(6), SOUTH_EAST(7);

	companion object {

		fun between(currentX: Int, currentY: Int, nextX: Int, nextY: Int): Direction {
			val deltaX = nextX - currentX
			val deltaY = nextY - currentY

			if (deltaY == 1) {
				if (deltaX == 1) return NORTH_EAST
				else if (deltaX == 0) return NORTH
				else if (deltaX == -1) return NORTH_WEST
			} else if (deltaY == -1) {
				if (deltaX == 1) return SOUTH_EAST
				else if (deltaX == 0) return SOUTH
				else if (deltaX == -1) return SOUTH_WEST
			} else if (deltaY == 0) {
				if (deltaX == 1) return EAST
				else if (deltaX == 0) return NONE
				else if (deltaX == -1) return WEST
			}

			return NONE
		}

		fun between(currentPosition: Position, nextPosition: Position)
				= between(currentPosition.x, currentPosition.y, nextPosition.x, nextPosition.y)

	}

}