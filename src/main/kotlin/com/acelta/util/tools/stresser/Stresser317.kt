@file:JvmName("Stresser317")

package com.acelta.util.tools.stresser

import com.acelta.packet.ByteBufPacketeer
import com.acelta.util.nums.byte
import com.acelta.util.nums.short
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import java.net.InetSocketAddress

fun main(args: Array<String>) = stresser {
	val buf = ByteBufPacketeer(directBuffer())
	buf + 14.byte + 0.byte + 16.byte + 0.byte + 255.byte + 317.short + true
	for (i in 1..29) buf + 0.short
	buf + "acelta$it" + "password"
	buf.buf
}