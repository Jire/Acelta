package com.acelta.util.nums

val Byte.a: Byte
	get() = (this + 128).byte

val Short.a: Short
	get() {
		val i = int
		val first = i shr 8
		val second = i + 128
		return (first or second).short
	}

val Byte.s: Byte
	get() = (this - 128).byte

val Short.s: Short
	get() {
		val i = int
		val first = i - 128
		val second = i shr 8
		return (first or second).short
	}

val Byte.c: Byte
	get() = (-this).byte

val Short.leA: Short
	get() {
		val i = int
		val first = i + 128
		val second = i shr 8
		return (first or second).short
	}