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