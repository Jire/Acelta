package com.acelta.packet.outgoing.rs317

import com.acelta.packet.outgoing.PlayerSend
import com.acelta.util.nums.a
import com.acelta.util.nums.byte
import com.acelta.util.nums.me
import com.acelta.util.nums.short

fun PlayerSend.setInterface(id: Int, sidebarID: Int) = ses + 71.byte + sidebarID.short + id.byte.a

fun PlayerSend.setSkill(id: Int, xp: Int, level: Int) = ses + 134.byte + id.byte + xp.me + level.byte

fun PlayerSend.mapRegion() = ses + 73.byte + (it.position.regionX + 6).short.a + (it.position.regionY + 6).short

fun PlayerSend.msg(message: String) = ses + 253.byte + message

fun PlayerSend.update(mapChanging: Boolean, teleporting: Boolean, updateRequired: Boolean) = with(ses) {
	this + 81.byte

	bitAccess()

	bit(updateRequired || mapChanging || teleporting)
	if (mapChanging || teleporting) {
		bits(2, 3 /* 3 = region change */).bits(2, it.position.z).bit(teleporting).bit(updateRequired)
		bits(7, it.position.localY).bits(7, it.position.localX)
	} else if (updateRequired) bits(2, 0 /* 0 = no movement, 1 = walk, 2 = run */)

	bits(8, 0 /* player count */)
	// update other players here
	bits(11, 2047 /* max player count */)

	byteAccess()

	if (updateRequired) this + 0.byte
}