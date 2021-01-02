plugins {
    kotlin("jvm") version "1.4.21"
    `maven-publish`
    signing
    maven
}

group = "trident"
version = "1.0-SNAPSHOT"
val artifactName = "nbt"
repositories {
    mavenCentral()

}
java {
    withJavadocJar()
    withSourcesJar()
    targetCompatibility = org.gradle.api.JavaVersion.VERSION_11
    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11

}
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = artifactName
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(artifactName)
            }
        }
    }
    repositories {
        maven {

            val releasesRepoUrl = uri("https://repo.potatocorp.dev/storages/maven/trident")
            val snapshotsRepoUrl = uri("https://repo.potatocorp.dev/storages/maven/trident")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials(PasswordCredentials::class)
        }
        mavenLocal()
    }
}


tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
dependencies {
    implementation(kotlin("stdlib"))
}