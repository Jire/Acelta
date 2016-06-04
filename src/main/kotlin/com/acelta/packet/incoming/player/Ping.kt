package com.acelta.packet.incoming.player

import com.acelta.world.mob.player.Player
import com.acelta.net.game.Session
import com.acelta.packet.incoming.ListenableIncomingPacket

interface PingListener { fun Player.on() }

object Ping : ListenableIncomingPacket<PingListener>(223) {
	override fun Session.receive(id: Int) = dispatch { player.on() }
}