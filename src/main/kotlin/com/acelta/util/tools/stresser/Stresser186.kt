@file:JvmName("Stresser186")

package com.acelta.util.tools.stresser

import com.acelta.packet.ByteBufPacketeer
import com.acelta.util.nums.byte

fun main(args: Array<String>) = stresser {
	val data = ByteBufPacketeer(directBuffer())
	data + 16.byte // login opcode
	data + 0.byte // login length
	data + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 // CRCs
	data + 0.byte /* RSA length */ + 0.byte /* RSA opcode */
	data + 0 + 0 + 0 + 0 // ISAAC session keys
	data + 1337 // UID
	data + "acelta$it" /* username */ + "password" /* password */
	data.buf
}