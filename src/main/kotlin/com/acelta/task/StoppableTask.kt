package com.acelta.task

abstract class StoppableTask : Task {

	var running = true
		private set

	protected abstract fun run()

	protected fun stop() {
		running = false
	}

	override fun finish(): Boolean {
		run()
		return !running
	}

}