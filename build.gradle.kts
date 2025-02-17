import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
    kotlin("jvm") version "1.6.10"

    `maven-publish`
}

group = "dev.ruffrick"
version = "0.1"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")

    // https://mvnrepository.com/artifact/net.dv8tion/JDA
    compileOnly("net.dv8tion:JDA:5.0.0-alpha.3")

    // https://github.com/ruffrick/jda-kotlinx
    api("com.github.ruffrick:jda-kotlinx:d1b7c18")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val sourcesJar = task<Jar>("sourcesJar") {
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

tasks {
    build {
        dependsOn(sourcesJar)
        dependsOn(jar)
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String

            from(components["kotlin"])
            artifact(sourcesJar)
        }
    }
}
