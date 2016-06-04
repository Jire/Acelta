package com.acelta.util

import java.security.SecureRandom

object FastRandom {

	private var x = SecureRandom().nextLong()

	operator fun get(max: Int): Int {
		x = x xor (x shr 12)
		x = x xor (x shl 25)
		x = x xor (x shr 27)
		x *= 2685821657736338717L

		val factor = Math.abs(x) / Long.MAX_VALUE.toDouble()
		return (max * factor).toInt()
	}

}