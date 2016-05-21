package com.acelta.game

import com.acelta.game.world.Position
import com.acelta.net.Session
import com.acelta.packet.outgoing.PlayerSend
import com.acelta.packet.outgoing.rs317.mapRegion
import com.acelta.packet.outgoing.rs317.update

class Player(id: Int, position: Position, val session: Session) : Entity(id, position) {

	val send = PlayerSend(this)

	var mapRegionChanging = false
	var teleporting = false
	var updateRequired = false

	override fun tick() {
		if (mapRegionChanging) send.mapRegion()
		send.update(mapRegionChanging, teleporting, updateRequired)

		mapRegionChanging = false
		teleporting = false
		updateRequired = false

		session.flush()
	}

}