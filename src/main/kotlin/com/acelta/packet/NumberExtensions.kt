package com.acelta.packet

import java.lang.Byte.toUnsignedInt
import java.lang.Integer.toUnsignedLong
import java.lang.Short.toUnsignedInt

val Short.le: Short
	get() {
		val i = int
		val first = i and 0xFF
		val second = i shr 8
		return (first or second).short
	}

val Short.leA: Short
	get() {
		val i = int
		val first = i + 128
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

val Byte.usin: Int
	get() = toUnsignedInt(this)

val Short.usin: Int
	get() = toUnsignedInt(this)

val Int.usin: Long
	get() = toUnsignedLong(this)

val Byte.int: Int
	get() = toInt()

val Short.byte: Byte
	get() = toByte()

val Short.int: Int
	get() = toInt()

val Int.byte: Byte
	get() = toByte()

val Int.short: Short
	get() = toShort()

val Int.long: Long
	get() = toLong()

val Boolean.int: Int
	get() = if (this) 1 else 0

val Boolean.byte: Byte
	get() = int.byte