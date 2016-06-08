package com.acelta.world.mob.player

import com.acelta.world.mob.Mob
import com.acelta.world.Position
import com.acelta.net.game.Session
import com.acelta.packet.outgoing.player.PlayerSend
import com.acelta.packet.outgoing.player.sync
import it.unimi.dsi.fastutil.objects.ObjectArrayList

class Player(id: Int, position: Position, val session: Session, val username: String) : Mob(id, position) {

	val send = PlayerSend(this)

	var updateRequired = false

	val screen = ObjectArrayList<Player>(255)

	override fun tick() {
		movement.tick()

		send.sync()
		updateRequired = false
		movement.regionChanging = false

		session.flush()
	}

}