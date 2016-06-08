package com.acelta.world

import com.acelta.net.game.Session
import com.acelta.packet.incoming.PlayerPacketConductor
import com.acelta.task.repeating
import com.acelta.task.unaryPlus
import com.acelta.util.Indexer
import com.acelta.world.mob.player.Player

object World {

	val players = Indexer<Player>(2000)

	fun register(session: Session, reconnecting: Boolean, username: String, password: String) = with(session) {
		send.loginResponse(2)
		flush()

		conductor = PlayerPacketConductor

		val index = players.nextIndex()
		val position = Position()
		player = Player(index, position, this, username)
		player.updateRequired = true
		player.movement.regionChanging = true

		players[index] = player

		if (!reconnecting) with(player.send) {
			index()
			msg("Welcome to Acelta.")
		}

		+repeating {
			player.tick()
			!player.session.channel.isOpen
		}
	}

}