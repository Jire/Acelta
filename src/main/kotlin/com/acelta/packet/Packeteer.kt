package com.acelta.packet

import com.acelta.util.StringCache
import com.acelta.util.delegator
import io.netty.buffer.ByteBuf
import io.netty.buffer.DefaultByteBufHolder

class Packeteer(data: ByteBuf) : DefaultByteBufHolder(data) {

	val byte by delegator<Packeteer, Byte> { content().readByte() }

	val short by delegator<Packeteer, Short> { content().readShort() }

	val int by delegator<Packeteer, Int> { content().readInt() }

	val long by delegator<Packeteer, Long> { content().readLong() }

	val float by delegator<Packeteer, Float> { content().readFloat() }

	val double by delegator<Packeteer, Double> { content().readDouble() }

	val boolean by delegator<Packeteer, Boolean> { content().readBoolean() }

	private val chars = CharArray(256)

	val string by delegator<Packeteer, String> {
		var index = 0
		while (data.readableBytes() > 0) {
			val char = content().readUnsignedByte().toChar()
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

}