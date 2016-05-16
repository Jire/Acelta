package com.acelta.util

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

object StringCache {

	private val map = Int2ObjectOpenHashMap<String>(8192)

	operator fun get(chars: CharArray, max: Int = chars.size - 1, hash: Int = chars.hashCode()): String {
		if (map.containsKey(hash)) return map[hash]!!
		val string = String(chars, 0, max).intern()
		map.put(hash, string)
		return string
	}

}