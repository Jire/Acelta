package com.acelta.util.nums

val Byte.int: Int
	get() = toInt()

val Byte.boolean: Boolean
	get() = this > 0

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

val Int.boolean: Boolean
	get() = this > 0

val Boolean.int: Int
	get() = if (this) 1 else 0

val Boolean.byte: Byte
	get() = int.byte