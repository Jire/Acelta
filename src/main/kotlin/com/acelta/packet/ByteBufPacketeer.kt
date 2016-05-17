package com.acelta.packet

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
		content().skipBytes(bytes)
	}

	override val readable: Int
		get() = content().readableBytes()

	override val byte: Byte
		get() = content().readByte()
	override val short: Short
		get() = content().readShort()
	override val int: Int
		get() = content().readInt()
	override val long: Long
		get() = content().readLong()

	private val chars by lazy(LazyThreadSafetyMode.NONE) { CharArray(256) }
	override val string: String
		get() {
			var index = 0
			while (readable > 0) {
				val read = byte.usin
				if (10 == read) break
				chars[index++] = read.toChar()
			}
			return StringCache[chars, index]
		}

	override var writeIndex: Int
		get() = content().writerIndex()
		set(value) {
			content().setIndex(readIndex, value)
		}

	override fun set(index: Int, value: Int) {
		content().setByte(index, value)
	}

	override fun plus(values: ByteArray) = apply { content().writeBytes(values) }

	override fun plus(value: Byte) = apply { content().writeByte(value.int) }
	override fun plus(value: Short) = apply { content().writeShort(value.int) }
	override fun plus(value: Int) = apply { content().writeInt(value) }
	override fun plus(value: Long) = apply { content().writeLong(value) }

}