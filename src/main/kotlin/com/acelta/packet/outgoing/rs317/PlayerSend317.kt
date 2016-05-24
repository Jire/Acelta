package com.acelta.packet.outgoing.rs317

import com.acelta.packet.ByteBufPacketeer
import com.acelta.packet.outgoing.PlayerSend
import com.acelta.util.nums.*
import io.netty.buffer.PooledByteBufAllocator
import io.netty.util.concurrent.FastThreadLocal

fun PlayerSend.setInterface(id: Int, sidebarID: Int) = ses + 71.byte + sidebarID.short + id.byte.a

fun PlayerSend.setSkill(id: Int, xp: Int, level: Int) = ses + 134.byte + id.byte + xp.me + level.byte

fun PlayerSend.mapRegion() = ses + 73.byte + (it.position.regionX + 6).short.a + (it.position.regionY + 6).short

fun PlayerSend.msg(message: String) = ses + 253.byte + (message.toByteArray().size + 1).byte + message

private object PacketeerCache : FastThreadLocal<Array<ByteBufPacketeer>>() {
	override fun initialValue(): Array<ByteBufPacketeer> {
		return Array(4) { ByteBufPacketeer(PooledByteBufAllocator.DEFAULT.directBuffer()) }
	}
}

fun PlayerSend.update(mapChanging: Boolean, teleporting: Boolean, updateRequired: Boolean) = with(ses) {
	val packeteers = PacketeerCache.get()

	val packet = packeteers[0]
	with(packet) {
		bitAccess().bit(updateRequired || mapChanging || teleporting)
		if (mapChanging || teleporting) {
			bits(2, 3 /* 3 = region change */).bits(2, it.position.z).bit(teleporting).bit(updateRequired)
			bits(7, it.position.localY).bits(7, it.position.localX)
		} else if (updateRequired) bits(2, 0 /* 0 = no movement, 1 = walk, 2 = run */)

		val updateBlock = packeteers[1]
		with(updateBlock) {
			if (updateRequired) {
				var mask = 0
				mask = mask or 0x10
				if (mask >= 0x100) {
					mask = mask or 0x40
					this + (mask and 0xFF).byte + (mask shr 8).byte
				} else this + mask.byte

				val playerProps = packeteers[2]
				with(playerProps) {
					this + 0.byte // gender
					this + 0.byte // skull icon

					this + (512 + 1149).short // helmet
					this + (512 + 1478).short // amulet
					this + (512 + 1052).short // cape
					this + (512 + 7158).short // weapon
					this + (512 + 3140).short // chest
					this + (512 + 1187).short // shield
					this + (512 + 3140).short // plate
					// this + (512 + 10).short // chest arms
					this + (512 + 4087).short // legs
					this + (512 + 1149).short // helmet
					this + (512 + 1580).short // gloves
					this + (512 + 88).short // boots

					this + 256.short // beard

					this + 0 + 0.byte // colors

					this + 0x328.short // stand
					this + 0x337.short // stand turn
					this + 0x333.short // walk
					this + 0x334.short // turn 180
					this + 0x335.short // turn 90 cw
					this + 0x336.short // turn 90 ccw
					this + 0x338.short // run

					this + player.details.username.long
					this + 100.byte // combat level
					this + 0.short // total level (sometimes used by client instead of combat level)
				}

				data.writeByte(-(playerProps.readable and 0xFF))
				data.writeBytes(playerProps.data)
				playerProps.clear()
			}
		}

		bits(8, 0 /* player count */)
		if (updateBlock.readable > 0) {
			bits(11, 2047).byteAccess()
			data.writeBytes(updateBlock.data)
			updateBlock.clear()
		} else byteAccess()
	}

	this + 81.byte + packet.readable.short
	write.data.writeBytes(packet.data)
	packet.clear()
}