package com.acelta.plugin.plugins

import com.acelta.packet.incoming.rs317.guest.Handshake
import com.acelta.packet.outgoing.rs317.handshakeResponse
import com.acelta.plugin.Plugin

object HandshakePlugin : Plugin({
	Handshake { nameHash -> send.handshakeResponse(0, 0); flush() }
})