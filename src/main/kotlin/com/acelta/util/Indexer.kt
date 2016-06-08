package com.acelta.util

class Indexer<T>(val capacity: Int) : MutableIterable<T> {

	private val array = arrayOfNulls<Any>(capacity)
	private val reusableIterator = Iterator()

	var size = 0
	var highest = 0

	operator fun get(index: Int) = array[index] as? T

	operator fun set(index: Int, element: T?): T? {
		val last = array[index]
		array[index] = element
		if (null === last && null !== element) {
			size++
			if (highest < index) highest = index
		} else if (null !== last && null === element) {
			size--
			if (highest == index) highest--
		}
		return last as? T
	}

	fun nextIndex(): Int {
		if (size == 0) return 1
		else if (size == capacity)
			throw IllegalStateException("There is no next index because the indexer is filled to capacity!")
		for (i in 0..size - 1) if (null === array[i]) return i
		throw AssertionError()
	}

	private inner class Iterator : MutableIterator<T> {

		internal var cursor = 0

		override fun hasNext() = size > 0 && cursor <= highest

		override tailrec fun next(): T = get(cursor++) ?: next()

		override fun remove() {
			set(cursor, null)
		}

	}

	override fun iterator(): MutableIterator<T> {
		reusableIterator.cursor = 0
		return reusableIterator
	}

}