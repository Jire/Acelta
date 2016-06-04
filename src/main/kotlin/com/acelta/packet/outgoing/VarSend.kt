package com.acelta.packet.outgoing

import com.acelta.packet.ByteBufPacketeer
import com.acelta.packet.ByteBufPacketeer.Companion.reusable
import com.acelta.packet.Packeteer
import com.acelta.util.nums.byte
import com.acelta.util.nums.short

inline fun varByte(out: Packeteer, crossinline body: ByteBufPacketeer.() -> Any) = reusable {
	body()
	out + readable.byte + this
}

inline fun varByte(out: Packeteer, id: Int, crossinline body: ByteBufPacketeer.() -> Any) = reusable {
	body()
	out + id.byte + readable.byte + this
}

inline fun varShort(out: Packeteer, id: Int, crossinline body: ByteBufPacketeer.() -> Any) = reusable {
	body()
	out + id.byte + readable.short + this
}