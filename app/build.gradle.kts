import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("io.ktor.plugin") version "2.2.2"
    application
}

application {
    mainClass.set("gosysoppgave.AppKt")
}

val aapLibsVersion = "3.5.40"
val ktorVersion = "2.2.2"

dependencies {
    implementation("com.github.navikt.aap-libs:ktor-auth-azuread:$aapLibsVersion")
    implementation("com.github.navikt.aap-libs:kafka:$aapLibsVersion")
    implementation("com.github.navikt.aap-libs:ktor-utils:$aapLibsVersion")
    implementation("com.github.navikt.aap-libs:kafka-avroserde:$aapLibsVersion")

    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-metrics-micrometer:$ktorVersion")

    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")

    implementation("io.micrometer:micrometer-registry-prometheus:1.10.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.1")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.5")
    runtimeOnly("net.logstash.logback:logstash-logback-encoder:7.2")

    testImplementation(kotlin("test"))
    testImplementation("com.github.navikt.aap-libs:kafka-test:$aapLibsVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
}

repositories {
    mavenCentral()
    maven("https://github-package-registry-mirror.gc.nav.no/cached/maven-release")
    maven("https://packages.confluent.io/maven/")
}

configurations.all {
    resolutionStrategy {
        force("org.apache.kafka:kafka-clients:7.3.1-ce")
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "19"
    }
    withType<Test> {
        useJUnitPlatform()
    }
}

kotlin.sourceSets["main"].kotlin.srcDirs("main")
kotlin.sourceSets["test"].kotlin.srcDirs("test")
sourceSets["main"].resources.srcDirs("main")
sourceSets["test"].resources.srcDirs("test")