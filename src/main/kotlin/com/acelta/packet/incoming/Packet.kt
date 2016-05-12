package com.acelta.packet.incoming

import com.acelta.packet.Packeteer
import it.unimi.dsi.fastutil.objects.ObjectArrayList

abstract class Packet<LISTENER>(val id: Int) {

	protected /* visible for inline */ val listeners = ObjectArrayList<LISTENER>()

	fun attach(listener: LISTENER) = listeners.add(listener)

	protected inline fun dispatch(body: LISTENER.() -> Any) {
		for (i in 0..listeners.size - 1) listeners[i].body()
	}

	abstract fun Packeteer.receive()

	operator fun invoke(packeteer: Packeteer) = packeteer.receive()

}