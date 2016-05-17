@file:JvmName("Acelta")

package com.acelta

import com.acelta.net.Server
import com.acelta.task.Tasks
import com.acelta.util.log

fun main(args: Array<String>) {
	Server.bind()
	Tasks // ensure initialization
	log("Acelta has started.\n")
}