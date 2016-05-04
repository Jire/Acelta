package com.acelta.util

import java.util.*

object StringCache {

	private val map = HashMap<Int, String>(1024) // TODO MUST REPLACE WITH CUSTOM MAP TO PREVENT ENTRY CREATION

	operator fun get(bytes: CharArray, max: Int = bytes.size - 1, hash: Int = bytes.hashCode()): String {
		if (map.containsKey(hash)) return map[hash]!!
		val string = String(bytes, 0, max).intern()
		map[hash] = string
		return string
	}

}