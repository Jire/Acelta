package com.acelta.packet.outgoing.player

import com.acelta.world.mob.player.Player
import com.acelta.net.game.Session
import com.acelta.packet.outgoing.varByte
import com.acelta.packet.outgoing.varShort
import com.acelta.util.nums.byte
import com.acelta.util.nums.short
import com.acelta.world.region.centerX
import com.acelta.world.region.centerY

class PlayerSend(val player: Player, val session: Session = player.session) {

	fun index() = session + 27.byte + player.id.short

	fun sector() = varShort(session, 232) {
		val centerX = player.position.centerX
		val centerY = player.position.centerY

		this + centerX.short + centerY.short

		var x = (centerX - 6) / 8
		while (x <= (centerX + 6) / 8) {
			var y = (centerY - 6) / 8
			while (y <= (centerY + 6) / 8) {
				this + x.byte + y.byte + 1 /* land CRC */ + 1 /* loc CRC */
				y++
			}
			x++
		}
	}

	fun msg(message: String) = varByte(session, 156) { this + message }

	fun logout() = session + 126.byte

	fun systemUpdate(seconds: Int, minutes: Int = 0) {
		val totalSeconds = seconds + (minutes * 60)
		val ticks = totalSeconds * (1 / .6)
		session + 53.byte + ticks.toShort()
	}

}