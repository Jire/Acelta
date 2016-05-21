package com.acelta.packet

import com.acelta.packet.Packeteer.AccessMode
import com.acelta.util.StringCache
import com.acelta.util.nums.int
import com.acelta.util.nums.usin
import io.netty.buffer.ByteBuf
import io.netty.buffer.DefaultByteBufHolder

class ByteBufPacketeer(data: ByteBuf) : Packeteer, DefaultByteBufHolder(data) {

	override var readIndex: Int
		get() = content().readerIndex()
		set(value) {
			content().setIndex(value, writeIndex)
		}

	override fun get(index: Int) = content().getByte(index)

	override fun skip(bytes: Int) {
		ensureAccessMode(AccessMode.BYTE)
		content().skipBytes(bytes)
	}

	override val readable: Int
		get() = content().readableBytes()

	override val byte: Byte
		get() {
			ensureAccessMode(AccessMode.BYTE)
			return content().readByte()
		}
	override val short: Short
		get() {
			ensureAccessMode(AccessMode.BYTE)
			return content().readShort()
		}
	override val int: Int
		get() {
			ensureAccessMode(AccessMode.BYTE)
			return content().readInt()
		}
	override val long: Long
		get() {
			ensureAccessMode(AccessMode.BYTE)
			return content().readLong()
		}

	private val chars by lazy(LazyThreadSafetyMode.NONE) { CharArray(256) }
	override val string: String
		get() {
			ensureAccessMode(AccessMode.BYTE)

			var index = 0
			while (readable > 0) {
				val char = byte.usin.toChar()
				if ('\n' == char) break
				chars[index++] = char
			}
			return StringCache[chars, index]
		}

	override var writeIndex: Int
		get() = content().writerIndex()
		set(value) {
			content().setIndex(readIndex, value)
		}

	override fun ensureWritable(bytes: Int) = apply { content().ensureWritable(bytes) }

	override fun set(index: Int, value: Int) {
		content().setByte(index, value)
	}

	override fun plus(values: ByteArray) = apply {
		ensureAccessMode(AccessMode.BYTE)
		content().writeBytes(values)
	}

	override fun plus(value: Byte) = apply {
		ensureAccessMode(AccessMode.BYTE)
		content().writeByte(value.int)
	}

	override fun plus(value: Short) = apply {
		ensureAccessMode(AccessMode.BYTE)
		content().writeShort(value.int)
	}

	override fun plus(value: Int) = apply {
		ensureAccessMode(AccessMode.BYTE)
		content().writeInt(value)
	}

	override fun plus(value: Long) = apply {
		ensureAccessMode(AccessMode.BYTE)
		content().writeLong(value)
	}

	override var accessMode = AccessMode.BYTE

	override var bitIndex = 0

}