package com.acelta.packet.incoming

import com.acelta.net.Session
import it.unimi.dsi.fastutil.objects.ObjectArrayList

abstract class Packet<LISTENER>(val id: Int) {

	protected /* visible for inline */ val listeners = ObjectArrayList<LISTENER>()

	fun attach(listener: LISTENER) = listeners.add(listener)

	protected inline fun dispatch(body: LISTENER.() -> Any) {
		if (listeners.size > 0) for (i in 0..listeners.size - 1) listeners[i].body()
	}

	abstract fun Session.receive()

	operator fun invoke(session: Session) = session.receive()

}