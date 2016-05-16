package com.acelta.packet.outgoing

import com.acelta.game.Player
import com.acelta.packet.a
import com.acelta.packet.byte
import com.acelta.packet.short

fun Player.mapRegion() = session + 73.byte + (position.regionX + 6).short.a + (position.regionY + 6).short

fun Player.msg(message: String) = session + 253.byte + message