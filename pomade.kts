import org.jire.pomade.*

pomade("Acelta"["1.0-SNAPSHOT"] from "com.acelta") {
	compile {
		"kotlin-reflect"["RELEASE"] from "org.jetbrains.kotlin"

		"netty-all"["4.1.0.CR7"] from "io.netty"
		"fastutil"["7.0.12"] from "it.unimi.dsi"
		"reflections"["0.9.10"] from "org.reflections"
	}
}