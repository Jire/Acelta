@file:JvmName("Acelta")

package com.acelta

import com.acelta.net.Server
import com.acelta.task.Tasks
import java.util.concurrent.Executors.newSingleThreadScheduledExecutor
import java.util.concurrent.TimeUnit

const val CYCLE_MS = 600L

fun main(args: Array<String>) {
	Server.bind()
	newSingleThreadScheduledExecutor().scheduleAtFixedRate({
		Tasks.tick()
	}, CYCLE_MS, CYCLE_MS, TimeUnit.MILLISECONDS)
	println("Acelta has started.")
}