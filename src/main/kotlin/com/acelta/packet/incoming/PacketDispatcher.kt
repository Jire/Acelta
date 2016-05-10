package com.acelta.packet.incoming

import com.acelta.packet.Packeteer
import it.unimi.dsi.fastutil.objects.ObjectArrayList

abstract class PacketDispatcher<T : PacketListener> {

	protected /* visible for inline */ val listeners = ObjectArrayList<T>()

	fun attach(listener: T) = listeners.add(listener)

	inline fun dispatch(body: T.() -> Any) = listeners.forEach { it.body() }

	abstract fun receive(data: Packeteer): Any

}