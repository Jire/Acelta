package com.acelta.packet

import it.unimi.dsi.fastutil.objects.ObjectArrayList

abstract class PacketDispatcher<T : PacketListener> {

	protected val listeners = ObjectArrayList<T>()

	fun attach(listener: T) = listeners.add(listener)

	abstract fun dispatch(data: Packeteer): Any

}