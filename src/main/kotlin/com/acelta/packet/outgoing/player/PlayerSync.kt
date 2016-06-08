@file:JvmName("PlayerSync")

package com.acelta.packet.outgoing.player

import com.acelta.packet.Packeteer
import com.acelta.packet.outgoing.varByte
import com.acelta.packet.outgoing.varShort
import com.acelta.util.long
import com.acelta.util.nums.byte
import com.acelta.util.nums.short
import com.acelta.world.World.players
import com.acelta.world.mob.player.Player

fun PlayerSend.sync() = varShort(session, 47) { player.syncAll(this) }

fun Player.syncAll(out: Packeteer) {
	out.bitAccess()
			.bits(2, position.z)
			.bits(7, position.localX).bits(7, position.localY)
			.bit(updateRequired)
			.bits(8, players.size - 1)
			.bits(11, 2047).finishBitAccess()
	if (updateRequired) syncAppearance(out)
}

fun Player.syncAppearance(out: Packeteer) {
	val mask = 0x1
	out + mask.byte
	if (mask and 0x1 == 0x1) varByte(out) {
		this + 0.byte // gender
		this + 0.byte // head icons

		this + (0x200 + 593).short // helmet
		this + (0x200 + 583).short // amulet
		this + (0x200 + 582).short // cape
		this + 0.byte // weapon
		this + (0x200 + 629).short // chest
		this + 0.byte // shield
		this + (0x200 + 629).short // plate
		this + (0x200 + 629).short // chest arms
		this + (0x200 + 617).short // legs
		this + (0x200 + 593).short // helmet
		this + (0x200 + 597).short // gloves
		this + (0x200 + 598).short // boots

		this + 0x100.short // beard

		this + 0 + 0.byte // colours

		this + 2.short // stand animation
		this + 0.short // turn animation
		this + 0.short // walk animation
		this + 0.short // turn around animation
		this + 0.short // turn right animation
		this + 0.short // turn left animation

		this + username.long
		this + 126.byte // combat level
	}
}