package com.acelta.task

import com.acelta.CYCLE_MS
import com.acelta.group
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import java.util.concurrent.TimeUnit

object Tasks {

	private val tasks = ObjectArrayList<Task>()

	fun schedule() = group.scheduleAtFixedRate({
		val start = System.nanoTime()
		try {
			tick()
		} catch (t: Throwable) {
			t.printStackTrace()
		}
		val elapsed = System.nanoTime() - start
		println("Elapsed tick: ${(elapsed / 1000000.0)}ms")
	}, CYCLE_MS, CYCLE_MS, TimeUnit.MILLISECONDS)

	fun execute(runnable: Runnable) = group.execute(runnable)

	fun tick() {
		val iterator = tasks.iterator()
		while (iterator.hasNext()) {
			val next = iterator.next()
			if (next.finish()) iterator.remove()
		}
	}

	operator fun plusAssign(task: Task) {
		tasks.add(task)
	}

}

operator fun Task.unaryPlus() {
	Tasks += this
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