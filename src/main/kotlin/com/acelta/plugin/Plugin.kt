package com.acelta.plugin

import org.reflections.Reflections

abstract class Plugin(val body: () -> Any) {

	companion object {

		init {
			val ref = Reflections("${javaClass.`package`.name}.plugins")
			for (plugin in ref.getSubTypesOf(Plugin::class.java)) {
				val obj = plugin.kotlin.objectInstance!!
				obj.body()
			}
		}

	}

}