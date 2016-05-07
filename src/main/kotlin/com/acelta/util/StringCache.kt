package com.acelta.util

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

object StringCache {

	private val map = Int2ObjectOpenHashMap<String>(8192)

	operator fun get(bytes: CharArray, max: Int = bytes.size - 1, hash: Int = bytes.hashCode()): String {
		if (map.containsKey(hash)) return map[hash]!!
		val string = String(bytes, 0, max).intern()
		map.put(hash, string)
		return string
	}

}