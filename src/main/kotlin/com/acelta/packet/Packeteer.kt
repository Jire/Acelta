package com.acelta.packet

import com.acelta.util.StringCache
import io.netty.buffer.ByteBuf
import io.netty.buffer.DefaultByteBufHolder

open class Packeteer(data: ByteBuf) : DefaultByteBufHolder(data) {

	fun skip(bytes: Int) = apply { content().skipBytes(bytes) }

	fun readable() = content().readableBytes()

	fun byte() = content().readByte()

	fun short() = content().readShort()

	fun int() = content().readInt()

	fun long() = content().readLong()

	fun boolean() = content().readBoolean()

	fun char() = content().readChar()

	fun medium() = (short().usin() shl 8) or byte().usin()

	fun smart() = if (content().getByte(content().readerIndex()).usin() > Byte.MAX_VALUE)
		short().usin() + Short.MIN_VALUE else byte().usin()

	private val chars by lazy(LazyThreadSafetyMode.NONE) { CharArray(256) }

	fun string(): String {
		var index = 0
		while (readable() > 0) {
			val char = byte().usin().toChar()
			if ('\n' == char) break
			chars[index++] = char
		}
		return StringCache[chars, index]
	}

	operator fun plus(value: Byte) = apply { content().writeByte(value.toInt()) }
	operator fun plus(value: Short) = apply { content().writeShort(value.toInt()) }
	operator fun plus(value: Int) = apply { content().writeInt(value) }
	operator fun plus(value: Long) = apply { content().writeLong(value) }
	operator fun plus(value: Float) = apply { content().writeFloat(value) }
	operator fun plus(value: Double) = apply { content().writeDouble(value) }
	operator fun plus(value: Boolean) = apply { content().writeBoolean(value) }
	operator fun plus(value: String) = apply { content().writeBytes(value.toByteArray()) }
	operator fun plus(value: Char) = apply { content().writeChar(value.toInt()) }

	fun clear() = apply { content().clear() }

}