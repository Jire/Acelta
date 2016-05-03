package com.acelta.util

import java.util.concurrent.ThreadLocalRandom

object Random {

	private var x = ThreadLocal.withInitial { ThreadLocalRandom.current().nextLong(Long.MAX_VALUE) }

	operator fun get(max: Int): Int {
		if (max <= 0) throw IllegalArgumentException("max must be positive")

		var x = x.get()
		x = x xor (x shr 12)
		x = x xor (x shl 25)
		x = x xor (x shr 27)
		x *= 2685821657736338717L
		this.x.set(x)

		val factor = Math.abs(x) / Long.MAX_VALUE.toDouble()
		return (max * factor).toInt()
	}

}