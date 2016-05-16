package com.acelta.util.nums

val Short.le: Short
	get() {
		val i = int
		val first = i and 0xFF
		val second = i shr 8
		return (first or second).short
	}

val Int.le: Int
	get() {
		val first = this shr 24
		val second = this shr 16
		val third = this shr 8
		val fourth = this and 0xFF
		return first or second or third or fourth
	}