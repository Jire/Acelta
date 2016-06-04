package com.acelta.plugin.plugins

import com.acelta.packet.incoming.PlayerPacketConductor
import com.acelta.world.mob.player.Player
import com.acelta.world.mob.player.PlayerDetails
import com.acelta.world.Position
import com.acelta.packet.incoming.session.Login
import com.acelta.plugin.Plugin
import com.acelta.task.repeating
import com.acelta.task.unaryPlus

object LoginPlugin : Plugin({
	Login { uid, user, pass ->
		send.loginResponse(2)
		flush()

		conductor = PlayerPacketConductor

		val index = 1
		val position = Position()
		val details = PlayerDetails(uid, user)

		player = Player(index, position, this, details)

		player.movement.regionChanging = true
		player.updateRequired = true

		with(player.send) {
			index()
			msg("Welcome to Acelta.")
		}

		+repeating {
			player.tick()
			!player.session.channel.isOpen
		}

		println("LOGIN (user: $user, pass: $pass)")
	}
})