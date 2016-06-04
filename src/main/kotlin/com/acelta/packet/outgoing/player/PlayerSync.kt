package com.acelta.packet.outgoing.player

import com.acelta.packet.Packeteer
import com.acelta.packet.outgoing.varByte
import com.acelta.packet.outgoing.varShort
import com.acelta.util.long
import com.acelta.util.nums.byte
import com.acelta.util.nums.int
import com.acelta.util.nums.short
import com.acelta.world.mob.player.Player
import com.acelta.world.region.localX
import com.acelta.world.region.localY

inline fun PlayerSend.sync() = varShort(session, 47) { player.sync(this) }

inline fun Player.sync(out: Packeteer) {
	out.bitAccess()
			.bits(2, 0 /* current level */)
			.bits(7, position.localX).bits(7, position.localY)
			.bit(updateRequired)

	val count = updateRequired.int // players to update (including self)
	out.bits(8, count)

	if (updateRequired) out.bit(updateRequired /* don't skip update */).bits(2, 0 /* movement type */)

	out.bits(11, 2047).finishBitAccess()

	if (updateRequired) {
		val mask = 0x1
		out + mask.byte
		if (mask and 0x1 == 0x1) varByte(out) {
			this + 0.byte // gender
			this + 0.byte // head icons

			this + (0x200 + 593).short // helmet
			this + (0x200 + 583).short // amulet
			this + (0x200 + 582).short // cape
			this + 0.byte // this + (0x200 + 1).short // weapon
			this + (0x200 + 629).short // chest
			this + 0.byte // this + (0x200 + 1).short // shield
			this + (0x200 + 629).short // plate
			this + (0x200 + 629).short // chest arms
			this + (0x200 + 617).short // legs
			this + (0x200 + 593).short // helmet
			this + (0x200 + 597).short // gloves
			this + (0x200 + 598).short // boots

			this + 0x100.short // beard

			this + 0 + 0.byte // colours

			this + 468.short // stand animation
			this + 468.short // turn animation
			this + 468.short // walk animation
			this + 468.short // turn around animation
			this + 468.short // turn right animation
			this + 468.short // turn left animation

			this + details.username.long
			this + 0.byte // total level
		}
	}
}