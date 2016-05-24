package com.acelta.packet

import com.acelta.packet.Packeteer.AccessMode

open class SplitPacketeer<T : Packeteer> : Packeteer {

	@Volatile open lateinit var read: T
	@Volatile open lateinit var write: T

	override var readIndex: Int
		get() = read.readIndex
		set(value) {
			read.readIndex = value
		}

	override fun get(index: Int) = read[index]

	override fun skip(bytes: Int) = read.skip(bytes)

	override val readable: Int
		get() = read.readable
	override val byte: Byte
		get() = read.byte
	override val short: Short
		get() = read.short
	override val int: Int
		get() = read.int
	override val long: Long
		get() = read.long
	override val string: String
		get() = read.string

	override var writeIndex: Int
		get() = write.writeIndex
		set(value) {
			write.writeIndex = value
		}

	override fun clear() = write.clear()

	override fun ensureWritable(bytes: Int) = write.ensureWritable(bytes)

	override fun set(index: Int, value: Int) {
		write[index] = value
	}

	override fun plus(values: ByteArray) = write + values

	override fun plus(value: Byte) = write + value
	override fun plus(value: Short) = write + value
	override fun plus(value: Int) = write + value
	override fun plus(value: Long) = write + value

	override fun plus(value: String) = write + value

	override var bitIndex: Int = 0
		get() = write.bitIndex

	override var accessMode: AccessMode
		get() = write.accessMode
		set(value) {
			write.accessMode = value
		}

	override fun bitAccess() = write.bitAccess()

	override fun byteAccess() = write.byteAccess()

	override fun ensureAccessMode(accessMode: AccessMode) = write.ensureAccessMode(accessMode)

	override fun bits(bits: Int, value: Int) = write.bits(bits, value)

}