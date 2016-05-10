package com.acelta.packet.incoming

import com.acelta.packet.Packeteer
import it.unimi.dsi.fastutil.objects.ObjectArrayList

abstract class Packet<LISTENER>(val id: Int, val receive: Packeteer.(Packet<LISTENER>) -> Any) {

	protected /* visible for inline */ val listeners = ObjectArrayList<LISTENER>()

	fun attach(listener: LISTENER) = listeners.add(listener)

	protected inline fun dispatch(body: LISTENER.() -> Any) = listeners.forEach { it.body() }

}