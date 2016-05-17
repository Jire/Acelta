package com.acelta.packet.incoming.game

import com.acelta.game.Player
import com.acelta.net.Session
import com.acelta.packet.incoming.Packet

interface PingListener { fun Player.on() }

object Ping : Packet<PingListener>(0) {
	override fun Session.receive() = dispatch { player.on() }
}