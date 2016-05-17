package com.acelta.packet

open class SplitPacketeer<T : Packeteer> : Packeteer {

	@Volatile open var read: T? = null
	@Volatile open lateinit var write: T
		protected set

	override var readIndex: Int
		get() = read!!.readIndex
		set(value) {
			read!!.readIndex = value
		}

	override fun get(index: Int) = read!![index]

	override fun skip(bytes: Int) = read!!.skip(bytes)

	override val readable: Int
		get() = read!!.readable
	override val byte: Byte
		get() = read!!.byte
	override val short: Short
		get() = read!!.short
	override val int: Int
		get() = read!!.int
	override val long: Long
		get() = read!!.long
	override val string: String
		get() = read!!.string

	override var writeIndex: Int
		get() = write.writeIndex
		set(value) {
			write.writeIndex = value
		}

	override fun set(index: Int, value: Int) {
		write[index] = value
	}

	override fun plus(values: ByteArray) = write + values

	override fun plus(value: Byte) = write + value
	override fun plus(value: Short) = write + value
	override fun plus(value: Int) = write + value
	override fun plus(value: Long) = write + value

	override fun plus(value: String) = write + value

}