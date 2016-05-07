package com.acelta.packet

import com.acelta.util.StringCache
import com.acelta.util.delegator
import io.netty.buffer.ByteBuf
import io.netty.buffer.DefaultByteBufHolder

class Packeteer(data: ByteBuf) : DefaultByteBufHolder(data) {

	val readable by delegator<Packeteer, Int> { content().readableBytes() }

	val byte by delegator<Packeteer, Byte> { content().readByte() }

	val short by delegator<Packeteer, Short> { content().readShort() }

	val int by delegator<Packeteer, Int> { content().readInt() }

	val long by delegator<Packeteer, Long> { content().readLong() }

	val float by delegator<Packeteer, Float> { content().readFloat() }

	val double by delegator<Packeteer, Double> { content().readDouble() }

	val boolean by delegator<Packeteer, Boolean> { content().readBoolean() }

	val char by delegator<Packeteer, Char> { content().readChar() }

	val medium by delegator<Packeteer, Int> { (short.usin shl 8) or byte.usin }

	val smart by delegator<Packeteer, Int> {
		val peek = content().getByte(content().readerIndex()).usin
		if (peek > Byte.MAX_VALUE) short.usin + Short.MIN_VALUE else byte.usin
	}

	private val chars = CharArray(256)

	val string by delegator<Packeteer, String> {
		var index = 0
		while (readable > 0) {
			val char = byte.usin.toChar()
			if ('\n' == char) break
			chars[index++] = char
		}
		StringCache[chars, index]
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

}