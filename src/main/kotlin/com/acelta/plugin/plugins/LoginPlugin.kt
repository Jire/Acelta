package com.acelta.plugin.plugins

import com.acelta.game.Player
import com.acelta.game.world.Position
import com.acelta.packet.PacketConductor
import com.acelta.packet.PacketConductor.Game
import com.acelta.packet.incoming.rs317.guest.Login
import com.acelta.packet.outgoing.rs317.loginResponse
import com.acelta.packet.outgoing.rs317.playerDetails
import com.acelta.plugin.Plugin
import com.acelta.task.Tasks.continuous
import com.acelta.task.unaryPlus

object LoginPlugin : Plugin({
	PacketConductor.Guest[18] = Login // reconnecting

	Login { ver, rel, hd, uid, user, pass ->
		val index = 1

		with(send) {
			loginResponse(2, 2, false)
			playerDetails(true, index)
		}
		flush()

		player = Player(index, Position(), this)
		conductor.set(Game)

		try {
			+continuous {
				try {
					println("Wantsum?")
					player.tick()
				} catch (t: Throwable) {
					t.printStackTrace()
				}
			}
		} catch (t: Throwable) {
			t.printStackTrace()
		}

		/*with(player.send) {
			for (i in 0..20) setSkill(i, 1, 1)

			setInterface(0, 2423)
			setInterface(1, 3917)
			setInterface(2, 638)
			setInterface(3, 3213)
			setInterface(4, 1644)
			setInterface(5, 5608)
			setInterface(6, 1151)
			setInterface(8, 5065)
			setInterface(9, 5715)
			setInterface(10, 2449)
			setInterface(11, 4445)
			setInterface(12, 147)
			setInterface(13, 2699)

			msg("Welcome to Acelta.")
		}*/

		println("LOGIN (user: $user, pass: $pass)")
	}
})