package com.acelta.packet.incoming

import it.unimi.dsi.fastutil.objects.ObjectArrayList

abstract class ListenableIncomingPacket<LISTENER>(vararg ids: Int) : IncomingPacket(*ids) {

	protected /* visible for inline */ val listeners = ObjectArrayList<LISTENER>()

	fun attach(listener: LISTENER) = listeners.add(listener)

	protected inline fun dispatch(body: LISTENER.() -> Any) {
		if (listeners.size > 0) for (i in 0..listeners.size - 1) listeners[i].body()
	}

}