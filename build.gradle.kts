plugins {
    java
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("com.gradleup.shadow") version "8.3.0"
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "io.github.staticfx"
version = "1.1.3"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://repo.codemc.io/repository/maven-public/") {
        name = "CodeMC"
    }
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    shadow("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/StaticFX/KIA")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = "io.github.staticfx"
            artifactId = "kia"
            version = rootProject.version.toString()

            pom {
                name.set("KIA")
                description.set("Kotlin inventory API for MC")
                url.set("https://github.com/StaticFX/KIA")

                licenses {
                    license {
                        name.set("GNU GENERAL PUBLIC LICENSE")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.de.html")
                    }
                }
                developers {
                    developer {
                        id.set("staticfx")
                        name.set("StaticFX")
                        email.set("devin-fritz@gmx.de")
                    }
                }
            }
        }
    }
}

tasks.register("printVersion") {
    doLast {
        println(rootProject.version.toString())
    }
}

tasks {
    shadowJar {
        minimize()
        relocate("de.tr7zw.changeme.nbtapi", "de.staticred.kia.nbtapi")
    }

    build {
        dependsOn(shadowJar)
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

sourceSets {
    named("main") {
        kotlin.srcDir("src/main/kotlin")
    }
}

kotlin {
    jvmToolchain(21)
}
