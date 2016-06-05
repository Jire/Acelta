@file:JvmName("Acelta")

package com.acelta

import com.acelta.net.game.GameServer
import com.acelta.net.http.HTTPServer
import com.acelta.plugin.PluginManager
import com.acelta.task.Tasks
import io.netty.channel.ServerChannel
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

const val THREADS = 1
const val CYCLE_MS = 600L

const val DATA_PATH = "data/"

const val CACHE_PATH = "${DATA_PATH}cache/"

val epoll = Epoll.isAvailable()
val group = if (epoll) EpollEventLoopGroup(THREADS) else NioEventLoopGroup(THREADS)
val channelType = (if (epoll) EpollServerSocketChannel::class.java
else NioServerSocketChannel::class.java) as Class<ServerChannel>

fun main(vararg args: String) {
	print("Binding HTTP server... ")
	HTTPServer.bind()
	println("done.")
	print("Binding game server... ")
	GameServer.bind()
	println("done.")

	print("Loading plugins... ")
	PluginManager.loadPlugins()
	println("done.")

	print("Scheduling tasks... ")
	Tasks.schedule()
	println("done.")

	println()
	println("Acelta is ready.")
	println()
}