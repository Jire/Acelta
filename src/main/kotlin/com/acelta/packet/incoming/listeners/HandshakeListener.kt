package com.acelta.packet.incoming.listeners

import com.acelta.packet.incoming.PacketListener

interface HandshakeListener : PacketListener {

	fun on(nameHash: Int)

}