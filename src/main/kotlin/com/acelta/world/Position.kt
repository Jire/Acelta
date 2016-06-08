package com.acelta.world

class Position {

	companion object {
		const val DEFAULT_X = 3222
		const val DEFAULT_Y = 3222
		const val DEFAULT_Z = 0
	}

	var x: Int = DEFAULT_X
		set(value) {
			field = value
			regionX = value shr 6
			chunkX = (regionX shl 3) + 6
			baseX = (chunkX - 6) shl 3
			localX = value - baseX
		}
	var y: Int = DEFAULT_Y
		set(value) {
			field = value
			regionY = value shr 6
			chunkY = (regionY shl 3) + 6
			baseY = (chunkY - 6) shl 3
			localY = value - baseY
		}
	var z: Int = DEFAULT_Z

	var centerX: Int = 0
	var centerY: Int = 0

	var regionX: Int = 0
	var regionY: Int = 0

	var chunkX: Int = 0
	var chunkY: Int = 0

	var baseX: Int = 0
	var baseY: Int = 0

	var localX: Int = 0
	var localY: Int = 0

	init {
		this.x = x
		this.y = y
	}

}