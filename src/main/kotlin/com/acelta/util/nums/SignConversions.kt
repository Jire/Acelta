package com.acelta.util.nums

import java.lang.Byte.toUnsignedInt
import java.lang.Integer.toUnsignedLong
import java.lang.Short.toUnsignedInt

val Byte.usin: Int
	get() = toUnsignedInt(this)

val Short.usin: Int
	get() = toUnsignedInt(this)

val Int.usin: Long
	get() = toUnsignedLong(this)