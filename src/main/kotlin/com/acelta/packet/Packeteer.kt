package com.acelta.packet

import com.acelta.util.nums.byte
import com.acelta.util.nums.int
import com.acelta.util.nums.usin

interface Packeteer {

	enum class AccessMode { BYTE, BIT }

	companion object {
		private val BIT_MASKS = arrayOf(0, 0x1, 0x3, 0x7,
				0xf, 0x1f, 0x3f, 0x7f,
				0xff, 0x1ff, 0x3ff, 0x7ff,
				0xfff, 0x1fff, 0x3fff, 0x7fff,
				0xffff, 0x1ffff, 0x3ffff, 0x7ffff,
				0xfffff, 0x1fffff, 0x3fffff, 0x7fffff,
				0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff,
				0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff,
				-1)
	}

	var readIndex: Int

	operator fun get(index: Int): Byte

	fun skip(bytes: Int)

	val readable: Int

	val byte: Byte
	val short: Short
	val int: Int
	val long: Long

	val boolean: Boolean
		get() {
			ensureAccessMode(AccessMode.BYTE)
			return byte > 0
		}

	val medium: Int
		get() {
			ensureAccessMode(AccessMode.BYTE)
			return (short.usin shl 8) or byte.usin
		}
	val smart: Int
		get() {
			ensureAccessMode(AccessMode.BYTE)
			return if (this[readIndex].usin > Byte.MAX_VALUE)
				short.usin + Short.MIN_VALUE else byte.usin
		}

	val string: String

	var writeIndex: Int

	fun clear(): Packeteer

	fun ensureWritable(bytes: Int): Packeteer

	operator fun set(index: Int, value: Int)

	operator fun plus(values: ByteArray): Packeteer

	operator fun plus(value: Byte): Packeteer
	operator fun plus(value: Short): Packeteer
	operator fun plus(value: Int): Packeteer
	operator fun plus(value: Long): Packeteer

	operator fun plus(value: Boolean) = apply { plus(value.byte) }

	operator fun plus(value: String) = apply {
		ensureAccessMode(AccessMode.BYTE)

		plus(value.toByteArray())
		plus('\n'.toByte())
	}

	var accessMode: AccessMode

	fun ensureAccessMode(accessMode: AccessMode) {
		if (this.accessMode != accessMode)
			throw IllegalStateException("Unexpected access mode (expected=$accessMode, actual=${this.accessMode})")
	}

	var bitIndex: Int

	fun bitAccess() = apply {
		bitIndex = writeIndex * 8
		accessMode = AccessMode.BIT
	}

	fun byteAccess() = apply {
		writeIndex = (bitIndex + 7) / 8
		accessMode = AccessMode.BYTE
	}

	fun bits(numBits: Int, value: Int) = apply {
		ensureAccessMode(AccessMode.BIT)

		var numBits = numBits
		var bytePos = bitIndex shr 3
		var bitOffset = 8 - (bitIndex and 7)
		bitIndex += numBits

		var requiredSpace = bytePos - writeIndex + 1
		requiredSpace += (numBits + 7) / 8
		ensureWritable(requiredSpace)

		while (numBits > bitOffset) {
			var tmp = get(bytePos).toInt()
			tmp = tmp and BIT_MASKS[bitOffset].inv()
			tmp = tmp or (value shr numBits - bitOffset and BIT_MASKS[bitOffset])
			set(bytePos++, tmp)
			numBits -= bitOffset
			bitOffset = 8
		}
		var tmp = get(bytePos).toInt()
		if (numBits == bitOffset) {
			tmp = tmp and BIT_MASKS[bitOffset].inv()
			tmp = tmp or (value and BIT_MASKS[bitOffset])
			set(bytePos, tmp)
		} else {
			tmp = tmp and (BIT_MASKS[numBits] shl bitOffset - numBits).inv()
			tmp = tmp or (value and BIT_MASKS[numBits] shl bitOffset - numBits)
			set(bytePos, tmp)
		}
	}

	infix fun bit(value: Int) = bits(1, value)

	infix fun bit(value: Boolean) = bits(1, value.int)

}