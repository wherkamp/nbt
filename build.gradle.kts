plugins {
    kotlin("jvm") version "1.4.21"
    maven
}

group = "trident"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}