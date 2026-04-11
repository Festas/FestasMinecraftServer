plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))

    // Velocity API
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")

    // SLF4J implementation shaded for runtime
    implementation("org.slf4j:slf4j-simple:2.0.9")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        relocate("com.zaxxer.hikari", "com.festas.guilds.libs.hikari")
        relocate("redis.clients.jedis", "com.festas.guilds.libs.jedis")
        relocate("org.slf4j", "com.festas.guilds.libs.slf4j")
    }
    build {
        dependsOn(shadowJar)
    }
}
