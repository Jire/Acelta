package com.acelta.packet.incoming.listeners

import com.acelta.packet.incoming.PacketListener

interface LoginListener : PacketListener {

	fun on(version: Int, release: Int, highDetail: Boolean, uid: Int, username: String, password: String)

}