package com.acelta.packet

import com.acelta.util.StringCache
import com.acelta.util.nums.int
import com.acelta.util.nums.usin
import io.netty.buffer.ByteBuf
import io.netty.buffer.DefaultByteBufHolder

open class Packeteer(data: ByteBuf) : DefaultByteBufHolder(data) {

	fun skip(bytes: Int) = apply { content().skipBytes(bytes) }

	val readable: Int
		get() = content().readableBytes()

	val byte: Byte
		get() = content().readByte()

	val short: Short
		get() = content().readShort()

	val int: Int
		get() = content().readInt()

	val long: Long
		get() = content().readLong()

	val boolean: Boolean
		get() = content().readBoolean()

	val char: Char
		get() = content().readChar()

	val medium: Int
		get() = (short.usin shl 8) or byte.usin

	val smart: Int
		get() = if (content().getByte(content().readerIndex()).usin > Byte.MAX_VALUE)
			short.usin + Short.MIN_VALUE else byte.usin

	private val chars by lazy(LazyThreadSafetyMode.NONE) { CharArray(256) }

	val string: String
		get() {
			var index = 0
			while (readable > 0) {
				val char = byte.usin.toChar()
				if ('\n' == char) break
				chars[index++] = char
			}
			return StringCache[chars, index]
		}

	operator fun plus(value: Byte) = apply { content().writeByte(value.int) }
	operator fun plus(value: Short) = apply { content().writeShort(value.int) }
	operator fun plus(value: Int) = apply { content().writeInt(value) }
	operator fun plus(value: Long) = apply { content().writeLong(value) }
	operator fun plus(value: Float) = apply { content().writeFloat(value) }
	operator fun plus(value: Double) = apply { content().writeDouble(value) }
	operator fun plus(value: Boolean) = apply { content().writeByte(if (value) 1 else 0) }
	operator fun plus(value: String) = apply { content().writeBytes(value.toByteArray()) }
	operator fun plus(value: Char) = apply { content().writeChar(value.toInt()) }

	fun clear() = apply { content().clear() }

}