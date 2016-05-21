@file:JvmName("Acelta")

package com.acelta

import com.acelta.net.Server
import com.acelta.plugin.Plugin
import com.acelta.task.Tasks

fun main(args: Array<String>) {
	Server.bind()
	Tasks // initialize tasks
	Plugin // initialize plugins
	println("Acelta has started.\n")
}