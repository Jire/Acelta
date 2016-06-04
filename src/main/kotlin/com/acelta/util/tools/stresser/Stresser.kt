package com.acelta.util.tools.stresser

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import java.net.InetSocketAddress

const val CONNECTIONS = 2000

const val HOST = "localhost"
const val PORT = 43594

inline fun stresser(crossinline supplyByteBuf: ByteBufAllocator.(repeatN: Int) -> ByteBuf) {
	val addr = InetSocketAddress(HOST, PORT)
	val group = NioEventLoopGroup(Runtime.getRuntime().availableProcessors())
	repeat(CONNECTIONS) {
		Bootstrap()
				.group(group)
				.channel(NioSocketChannel::class.java)
				.handler(object : ChannelInboundHandlerAdapter() {
					override fun channelActive(ctx: ChannelHandlerContext) {
						ctx.writeAndFlush(ctx.alloc().supplyByteBuf(it), ctx.voidPromise())
					}
				})
				.connect(addr)
	}
}