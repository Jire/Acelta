package com.acelta.world.mob.player

import com.acelta.world.mob.Mob
import com.acelta.world.Position
import com.acelta.net.game.Session
import com.acelta.packet.outgoing.player.PlayerSend
import com.acelta.packet.outgoing.player.sync

class Player(id: Int, position: Position, val session: Session, val details: PlayerDetails) : Mob(id, position) {

	val send = PlayerSend(this)

	var updateRequired = true

	override fun tick() {
		movement.tick()

		send.sync()
		updateRequired = false
		movement.regionChanging = false

		session.flush()
	}

}