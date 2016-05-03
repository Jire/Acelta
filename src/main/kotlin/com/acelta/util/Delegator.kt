package com.acelta.util

import kotlin.reflect.KProperty

class Delegator<THIS, VALUE>(private val get: THIS.() -> VALUE, private val set: (THIS.(VALUE) -> Any)? = null) {

	operator fun getValue(thisRef: THIS, property: KProperty<*>) = thisRef.get()

	operator fun setValue(thisRef: THIS, property: KProperty<*>, value: VALUE) = (set!!)(thisRef, value)

}

fun <THIS, VALUE> delegator(get: THIS.() -> VALUE) = delegator(get, null)

fun <THIS, VALUE> delegator(get: THIS.() -> VALUE, set: (THIS.(VALUE) -> Any)?) = Delegator(get, set)