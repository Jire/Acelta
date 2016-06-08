package com.acelta.packet.outgoing.player

import com.acelta.world.mob.player.Player
import com.acelta.net.game.Session
import com.acelta.packet.outgoing.varByte
import com.acelta.packet.outgoing.varShort
import com.acelta.util.nums.byte
import com.acelta.util.nums.short

class PlayerSend(val player: Player, val session: Session = player.session) {

	fun index() = session + 27.byte + player.index.short

	fun sector() = varShort(session, 232) {
		val chunkX = player.position.chunkX
		val chunkY = player.position.chunkY

		this + chunkX.short + chunkY.short

		var x = (chunkX - 6) / 8
		while (x <= (chunkX + 6) / 8) {
			var y = (chunkY - 6) / 8
			while (y <= (chunkY + 6) / 8) {
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

	fun skill(skillID: Int, experience: Int, level: Int) = session + 98.byte + skillID.byte + experience + level.byte

	fun multizone(inMultizone: Boolean) = session + 217.byte + inMultizone.byte

	fun nullAnims() = session + 5.byte

	fun resetChat() = session + 75.byte

}