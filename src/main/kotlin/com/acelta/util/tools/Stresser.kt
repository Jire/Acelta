@file:JvmName("Stresser")

package com.acelta.util.tools

import com.acelta.packet.ByteBufPacketeer
import com.acelta.util.nums.byte
import com.acelta.util.nums.short
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import java.net.InetSocketAddress

const val CONNECTIONS = 2000

const val HOST = "localhost"
const val PORT = 43594

fun main(args: Array<String>) {
	val addr = InetSocketAddress(HOST, PORT)
	val group = NioEventLoopGroup(Runtime.getRuntime().availableProcessors())
	repeat(CONNECTIONS) {
		Bootstrap()
			.group(group)
			.channel(NioSocketChannel::class.java)
			.handler(object : ChannelInboundHandlerAdapter() {
				override fun channelActive(ctx: ChannelHandlerContext) {
					val buf = ByteBufPacketeer(ctx.alloc().directBuffer())
					buf + 14.byte + 0.byte + 16.byte + 0.byte + 255.byte + 317.short + true
					for (i in 1..29) buf + 0.short
					buf + "acelta$it" + "password"
					ctx.writeAndFlush(buf.data, ctx.voidPromise())
				}
			})
			.connect(addr)
	}
}