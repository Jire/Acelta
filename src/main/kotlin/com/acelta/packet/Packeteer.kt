package com.acelta.packet

import com.acelta.util.nums.byte
import com.acelta.util.nums.usin

interface Packeteer {

	var readIndex: Int

	operator fun get(index: Int): Byte

	fun skip(bytes: Int)

	val readable: Int

	val byte: Byte
	val short: Short
	val int: Int
	val long: Long

	val boolean: Boolean
		get() = byte > 0

	val medium: Int
		get() = (short.usin shl 8) or byte.usin
	val smart: Int
		get() = if (this[readIndex].usin > Byte.MAX_VALUE) short.usin + Short.MIN_VALUE else byte.usin

	val string: String

	var writeIndex: Int

	operator fun set(index: Int, value: Int)

	operator fun plus(values: ByteArray): Packeteer

	operator fun plus(value: Byte): Packeteer
	operator fun plus(value: Short): Packeteer
	operator fun plus(value: Int): Packeteer
	operator fun plus(value: Long): Packeteer

	operator fun plus(value: Boolean) = apply { plus(value.byte) }

	operator fun plus(value: String) = plus(value.toByteArray())

}