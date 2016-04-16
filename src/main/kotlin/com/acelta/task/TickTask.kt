package com.acelta.task

abstract class TickTask(val ticks: Int = 1) : StoppableTask() {

	private var count = ticks

	override fun finish(): Boolean {
		if (--count > 0) return !running
		count = ticks
		return super.finish()
	}

}