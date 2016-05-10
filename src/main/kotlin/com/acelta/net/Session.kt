package com.acelta.net

import com.acelta.packet.Packeteer
import io.netty.buffer.PooledByteBufAllocator
import io.netty.channel.socket.SocketChannel

class Session(val channel: SocketChannel) {

	val packeteer = Packeteer(PooledByteBufAllocator.DEFAULT.buffer())

	fun flush() = channel.writeAndFlush(packeteer.content())

}