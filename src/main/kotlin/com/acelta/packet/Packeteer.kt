package com.acelta.packet

import com.acelta.util.StringCache
import com.acelta.util.delegator
import io.netty.buffer.ByteBuf
import io.netty.buffer.DefaultByteBufHolder

class Packeteer(data: ByteBuf) : DefaultByteBufHolder(data) {

	val byte by delegator<Packeteer, Byte> { data.readByte() }

	val short by delegator<Packeteer, Short> { data.readShort() }

	val int by delegator<Packeteer, Int> { data.readInt() }

	val long by delegator<Packeteer, Long> { data.readLong() }

	val float by delegator<Packeteer, Float> { data.readFloat() }

	val double by delegator<Packeteer, Double> { data.readDouble() }

	val boolean by delegator<Packeteer, Boolean> { data.readBoolean() }

	private val chars = CharArray(256)

	val string by delegator<Packeteer, String> {
		var index = 0
		while (data.readableBytes() > 0) {
			val char = data.readUnsignedByte().toChar()
			if ('\n' == char) break
			chars[index++] = char
		}
		StringCache[chars, index]
	}

	inline operator fun <reified T : Any> plus(value: T) = apply {
		with (content()) {
			val type = T::class.java
			when (type) {
				java.lang.Byte::class.java -> writeByte(value as Int)
				java.lang.Short::class.java -> writeShort(value as Int)
				java.lang.Integer::class.java -> writeInt(value as Int)
				java.lang.Long::class.java -> writeLong(value as Long)
				java.lang.Float::class.java -> writeFloat(value as Float)
				java.lang.Double::class.java -> writeDouble(value as Double)
				java.lang.Boolean::class.java -> writeBoolean(value as Boolean)
				else -> throw IllegalArgumentException("Could not write the reified type \"$type\"")
			}
		}
	}

}