package com.acelta.task

import java.util.*

object Tasks {

	private val tasks = ArrayDeque<Task>()

	fun tick() {
		val it = tasks.iterator()
		while (it.hasNext()) if (it.next().finish()) it.remove()
	}

	operator fun plusAssign(task: Task) {
		tasks.add(task)
	}

	inline fun delayed(ticks: Int = 1, crossinline runnable: () -> Unit) = object : TickTask(ticks) {
		override fun run() {
			runnable.invoke()
			stop()
		}
	}

	inline fun repeating(ticks: Int = 1, crossinline task: () -> Boolean) = object : TickTask(ticks) {
		override fun run() {
			if (task.invoke())
				stop()
		}
	}

	inline fun continuous(ticks: Int = 1, crossinline runnable: () -> Unit) = repeating(ticks) {
		runnable.invoke(); false
	}

}