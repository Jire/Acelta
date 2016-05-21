package com.acelta.task

import it.unimi.dsi.fastutil.objects.ObjectArrayList
import java.util.*
import java.util.concurrent.Executors.newSingleThreadScheduledExecutor
import java.util.concurrent.TimeUnit.MILLISECONDS

object Tasks {

	const val CYCLE_MS = 600L

	private val tasks = Collections.synchronizedList(ObjectArrayList<Task>())
	private val executor = newSingleThreadScheduledExecutor()

	init { executor.scheduleAtFixedRate({ tick() }, CYCLE_MS, CYCLE_MS, MILLISECONDS) }

	fun execute(body: () -> Any) = executor.submit(body)

	fun tick() {
		val it = tasks.iterator()
		while (it.hasNext()) if (it.next().finish()) it.remove()
	}

	operator fun plusAssign(task: Task) {
		tasks.add(task)
	}

	inline fun delayed(ticks: Int = 1, crossinline body: () -> Any) = object : TickTask(ticks) {
		override fun run() {
			body()
			stop()
		}
	}

	inline fun repeating(ticks: Int = 1, crossinline body: () -> Boolean) = object : TickTask(ticks) {
		override fun run() {
			if (body()) stop()
		}
	}

	inline fun continuous(ticks: Int = 1, crossinline body: () -> Any) = repeating(ticks) { body(); false }

}

operator fun Task.unaryPlus() {
	Tasks += this
}