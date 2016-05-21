package com.acelta.packet.outgoing

import com.acelta.game.Player
import com.acelta.net.Session
import com.acelta.util.Offsetter
import com.acelta.util.nums.a
import com.acelta.util.nums.byte
import com.acelta.util.nums.me
import com.acelta.util.nums.short

class PlayerSend(override val it: Player, private val ses: Session = it.session) : Offsetter<Player> {

	fun setInterface(id: Int, sidebarID: Int) = ses + 71.byte + sidebarID.short + id.byte.a

	fun setSkill(id: Int, xp: Int, level: Int) = ses + 134.byte + id.byte + xp.me + level.byte

	fun mapRegion() = ses + 73.byte + (it.position.regionX + 6).short.a + (it.position.regionY + 6).short

	fun msg(message: String) = ses + 253.byte + message

	fun update() = (ses + 81.byte).bitAccess().bits(11, 2047).byteAccess() + 0.byte

}