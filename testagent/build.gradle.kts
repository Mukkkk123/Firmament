plugins {
	java
	id("notfirmament.common")
	id("com.gradleup.shadow")
}
dependencies {
	implementation(libs.asm)
}
tasks.withType<Jar> {
	val agentMain = "moe.nea.notfirmament.testagent.AgentMain"
	manifest.attributes(
		"Agent-Class" to agentMain,
		"Premain-Class" to agentMain,
	)
}
