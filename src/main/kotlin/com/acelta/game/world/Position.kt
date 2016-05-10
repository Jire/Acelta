package com.acelta.game.world

data class Position(var x: Int = DEFAULT_X, var y: Int = DEFAULT_Y, var z: Int = DEFAULT_Z) {

	companion object {
		const val DEFAULT_X = 3222
		const val DEFAULT_Y = 3222
		const val DEFAULT_Z = 0
	}

}