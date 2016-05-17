package com.acelta.util

fun log(message: String, level: Int = 1) {
	val dashes = (level + 1) * (level - 1)
	for (i in 1..dashes) print("-")
	if (dashes > 0) print(" ")
	print("$message\n")
}