package com.acelta.world.region

import com.acelta.world.Position

val Position.centerX: Int
	get() = x shr 3
val Position.centerY: Int
	get() = y shr 3

val Position.regionX: Int
	get() = centerX - 6
val Position.regionY: Int
	get() = centerY - 6

val Position.localX: Int
	get() = x - 8 * regionX
val Position.localY: Int
	get() = y - 8 * regionY