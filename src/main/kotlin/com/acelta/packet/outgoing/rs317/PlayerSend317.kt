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

fun PlayerSend.update() = (ses + 81.byte).bitAccess().bits(11, 2047).byteAccess() + 0.byte