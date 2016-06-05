package com.acelta.packet.incoming.player

import com.acelta.net.game.Session
import com.acelta.packet.incoming.IncomingPacket
import com.acelta.util.nums.usin

object Movement : IncomingPacket(147) {

	override fun Session.receive(id: Int) {
		val size = byte.usin
		val steps = (size - 2) / 2

		val baseX = short.usin
		val baseY = short.usin

		player.movement.addFirstStep(baseX, baseY)
		repeat(steps) {
			val xOffset = byte.usin
			val yOffset = byte.usin
			player.movement.addStep(baseX + xOffset, baseY + yOffset)
		}
	}

}