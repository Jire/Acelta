package com.acelta.packet.incoming

import com.acelta.packet.Packeteer
import it.unimi.dsi.fastutil.objects.ObjectArrayList

abstract class Packet<T>(val id: Int, val receive: Packeteer.(Packet<T>) -> Any) {

	protected /* visible for inline */ val listeners = ObjectArrayList<T>()

	fun attach(listener: T) = listeners.add(listener)

	protected inline fun dispatch(body: T.() -> Any) = listeners.forEach { it.body() }

}