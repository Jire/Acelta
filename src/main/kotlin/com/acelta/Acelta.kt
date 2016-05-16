@file:JvmName("Acelta")

package com.acelta

import com.acelta.net.Server
import com.acelta.task.Tasks

const val CYCLE_MS = 600L

fun main(args: Array<String>) {
	Server.bind()
	Tasks // ensure initialization
	println("Acelta has started.")
}