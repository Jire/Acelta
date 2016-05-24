package com.acelta.packet.outgoing.rs317

import com.acelta.game.Player
import com.acelta.packet.ByteBufPacketeer
import com.acelta.packet.Packeteer
import com.acelta.packet.outgoing.PlayerSend
import com.acelta.util.nums.byte
import com.acelta.util.nums.c
import com.acelta.util.nums.long
import com.acelta.util.nums.short

fun PlayerSend.sync(mapChanging: Boolean, teleporting: Boolean, updateRequired: Boolean) = with(ses) {
	val reusables = ByteBufPacketeer.Reusables.get()

	val packet = reusables[0]
	with(packet) {
		it.syncMovement(this, mapChanging, teleporting, updateRequired)

		val updateBlock = reusables[1]
		it.sync(updateBlock, updateRequired, reusables[2])

		bits(8, 0 /* amount of players to update */)
		if (updateBlock.readable > 0) {
			bits(11, 2047).byteAccess() + updateBlock
		} else byteAccess()
		updateBlock.clear()
	}

	this + 81.byte + packet.readable.short + packet
	packet.clear()
}

private fun Player.syncMovement(data: ByteBufPacketeer, mapChanging: Boolean,
                                teleporting: Boolean, updateRequired: Boolean) {
	data.bitAccess().bit(updateRequired || mapChanging || teleporting)
	if (mapChanging || teleporting) {
		data.bits(2, 3 /* 3 = region change */)
				.bits(2, position.z)
				.bit(teleporting)
				.bit(updateRequired)
				.bits(7, position.localY).bits(7, position.localX)
	} else if (updateRequired) data.bits(2, 0 /* 0 = no movement, 1 = walk, 2 = run */)
}

private fun Player.sync(data: ByteBufPacketeer, updateRequired: Boolean, appearanceBlock: ByteBufPacketeer) {
	if (!updateRequired) return

	var mask = 0
	mask = mask or 0x10
	if (mask >= 0x100) {
		mask = mask or 0x40
		data + mask.byte + (mask shr 8).byte
	} else data + mask.byte

	syncAppearance(appearanceBlock)

	data + appearanceBlock.readable.byte.c + appearanceBlock
	appearanceBlock.clear()
}

private fun Player.syncAppearance(data: Packeteer) {
	data + 0.byte // gender
	data + 0.byte // skull icon

	data + (0x200 + 1149).short // helmet
	data + (0x200 + 1478).short // amulet
	data + (0x200 + 1052).short // cape
	data + (0x200 + 7158).short // weapon
	data + (0x200 + 3140).short // chest
	data + (0x200 + 1187).short // shield
	data + (0x200 + 3140).short // plate
	// data + (0x200 + 10).short // chest arms
	data + (0x200 + 4087).short // legs
	data + (0x200 + 1149).short // helmet
	data + (0x200 + 1580).short // gloves
	data + (0x200 + 88).short // boots

	data + 0x100.short // beard

	data + 0 + 0.byte // colors

	data + 0x328.short // stand
	data + 0x337.short // stand turn
	data + 0x333.short // walk
	data + 0x334.short // turn 180
	data + 0x335.short // turn 90 cw
	data + 0x336.short // turn 90 ccw
	data + 0x338.short // run

	data + details.username.long
	data + 100.byte // combat level
	data + 0.short // total level (sometimes used by client instead of combat level)
}