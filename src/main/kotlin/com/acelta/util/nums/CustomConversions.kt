package com.acelta.util.nums

val Byte.a: Byte
	get() = (this + 128).byte

val Short.a: Short
	get() {
		val i = int
		val first = (i and 0xFF) shr 8
		val second = (i and 0xFF) + 128
		return (first or second).short
	}

val Byte.s: Byte
	get() = (this - 128).byte

val Short.s: Short
	get() {
		val i = int
		val first = (i and 0xFF) - 128
		val second = (i and 0xFF) shr 8
		return (first or second).short
	}

val Byte.c: Byte
	get() = (-this).byte

val Short.leA: Short
	get() {
		val i = int
		val first = (i and 0xFF) + 128
		val second = (i and 0xFF) shr 8
		return (first or second).short
	}

val Int.me: Int
	get() {
		val first = (this shr 8) and 0xFF
		val second = this and 0xFF
		val third = (this shr 24) and 0xFF
		val fourth = (this shr 16) and 0xFF
		return first or second or third or fourth
	}

val String.long: Long
	get() {
		var l = 0L
		var i = 0
		while (i < length && i < 12) {
			val c = this[i]
			l *= 37L
			if (c >= 'A' && c <= 'Z')
				l += (1 + c.toInt() - 65).toLong()
			else if (c >= 'a' && c <= 'z')
				l += (1 + c.toInt() - 97).toLong()
			else if (c >= '0' && c <= '9') l += (27 + c.toInt() - 48).toLong()
			i++
		}
		while (l % 37L == 0L && l != 0L) l /= 37L
		return l
	}