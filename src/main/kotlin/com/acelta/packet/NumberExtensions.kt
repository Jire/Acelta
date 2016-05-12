package com.acelta.packet

import java.lang.Byte.toUnsignedInt
import java.lang.Integer.toUnsignedLong
import java.lang.Short.toUnsignedInt

fun Short.le(): Short {
	val i = toInt()
	val first = i and 0xFF
	val second = i shr 8
	return (first or second).toShort()
}

fun Int.le(): Int {
	val first = this shr 24
	val second = this shr 16
	val third = this shr 8
	val fourth = this and 0xFF
	return first or second or third or fourth
}

fun Byte.a() = (this + 128).toByte()

fun Short.a(): Short {
	val i = toInt()
	val first = i shr 8
	val second = i + 128
	return (first or second).toShort()
}

fun Byte.s() = (this - 128).toByte()

fun Short.s(): Short {
	val i = toInt()
	val first = i - 128
	val second = i shr 8
	return (first or second).toShort()
}

fun Byte.c() = (-this).toByte()

fun Byte.usin() = toUnsignedInt(this)

fun Short.usin() = toUnsignedInt(this)

fun Int.usin() = toUnsignedLong(this)

fun Boolean.toInt() = if (this) 1 else 0

fun Boolean.toByte() = toInt().toByte()