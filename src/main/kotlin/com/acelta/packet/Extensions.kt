package com.acelta.packet

import com.acelta.util.delegator
import java.lang.Byte.toUnsignedInt
import java.lang.Integer.toUnsignedLong
import java.lang.Short.toUnsignedInt

val Short.le by delegator<Short, Short> {
	var i = toInt()
	val first = i and 0xFF
	val second = i shr 8
	(first or second).toShort()
}

val Int.le by delegator<Int, Int> {
	val first = this shr 24
	val second = this shr 16
	val third = this shr 8
	val fourth = this and 0xFF
	first or second or third or fourth
}

val Byte.a by delegator<Byte, Byte> { (this + 128).toByte() }

val Short.a by delegator<Short, Short> {
	val i = toInt()
	val first = i + 128
	val second = i shr 8
	(first or second).toShort()
}

val Byte.s by delegator<Byte, Byte> { (this - 128).toByte() }

val Short.s by delegator<Short, Short> {
	val i = toInt()
	val first = i - 128
	val second = i shr 8
	(first or second).toShort()
}

val Byte.c by delegator<Byte, Byte> { (-this).toByte() }

val Byte.usin by delegator<Byte, Int> { toUnsignedInt(this) }

val Short.usin by delegator<Short, Int> { toUnsignedInt(this) }

val Int.usin by delegator<Int, Long> { toUnsignedLong(this) }