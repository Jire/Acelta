package com.acelta.packet

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

	private val sb = StringBuilder()

	val string by delegator<Packeteer, String> {
		sb.delete(0, sb.length - 1)
		while (data.readableBytes() > 0) {
			val char = data.readUnsignedByte()
			if ('\n'.toShort() == char) break
			sb.append(char)
		}
		sb.toString() // TODO find way to make string without garbage
	}

	inline operator fun <reified T : Any> plus(value: T) = apply { TODO() }

}