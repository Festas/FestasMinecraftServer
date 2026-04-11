plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))

    // Paper API
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")

    // Optional Hooks (soft dependencies)
    compileOnly("net.Indyuce:MMOCore-API:1.12.1-SNAPSHOT") {
        isTransitive = false
    }
    compileOnly("io.lumine:Mythic-Dist:5.7.1-SNAPSHOT") {
        isTransitive = false
    }
    compileOnly("me.clip:placeholderapi:2.11.5") {
        isTransitive = false
    }

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
