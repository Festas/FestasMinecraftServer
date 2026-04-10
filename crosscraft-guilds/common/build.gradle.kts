plugins {
    `java-library`
}

dependencies {
    api(project(":api"))
    api("com.zaxxer:HikariCP:5.1.0")
    api("redis.clients:jedis:5.1.0")
    implementation("org.slf4j:slf4j-api:2.0.9")
}
