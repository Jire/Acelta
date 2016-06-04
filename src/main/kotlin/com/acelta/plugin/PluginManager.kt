package com.acelta.plugin

import org.reflections.Reflections

object PluginManager {

	fun loadPlugins(`package`: String = "${javaClass.`package`.name}.plugins") {
		val ref = Reflections(`package`)
		for (plugin in ref.getSubTypesOf(Plugin::class.java)) {
			val obj = plugin.kotlin.objectInstance!!
			obj.body()
		}
	}

}