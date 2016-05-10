package com.acelta.packet.incoming

import com.acelta.packet.Packeteer
import it.unimi.dsi.fastutil.objects.ObjectArrayList

abstract class Packet<LISTENER>(val id: Int) {

	protected /* visible for inline */ val listeners = ObjectArrayList<LISTENER>()

	fun attach(listener: LISTENER) = listeners.add(listener)

	protected inline fun dispatch(body: LISTENER.() -> Any) = listeners.forEach { body(it) }

	abstract fun Packeteer.receive()

	operator fun invoke(packeteer: Packeteer) = packeteer.receive()

}