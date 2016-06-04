package com.acelta.util

val String.long: Long
	get() {
		var l = 0L
		var i = 0
		while (i < length && i < 12) {
			val c = this[i]
			l *= 37L
			if (c >= 'A' && c <= 'Z')
				l += (1 + c.toInt() - 65).toLong()
			else if (c >= 'a' && c <= 'z')
				l += (1 + c.toInt() - 97).toLong()
			else if (c >= '0' && c <= '9') l += (27 + c.toInt() - 48).toLong()
			i++
		}
		while (l % 37L == 0L && l != 0L) l /= 37L
		return l
	}