package com.acelta.packet.incoming.player

import com.acelta.net.game.Session
import com.acelta.packet.incoming.IncomingPacket
import com.acelta.util.nums.usin

object Movement : IncomingPacket(147) {

	override fun Session.receive(id: Int) = with(player.movement) {
		val size = byte.usin
		if (readable < size) return

		val steps = (size - 2) / 2

		val baseX = short.usin
		val baseY = short.usin

		addFirstStep(baseX, baseY)
		repeat(steps - 1) {
			val xOffset = byte
			val yOffset = byte
			addStep(baseX + xOffset, baseY + yOffset)
		}
	}

}