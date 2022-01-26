plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("org.jetbrains.kotlin.kapt") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.10"
    id("io.micronaut.library") version "3.1.1"
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("io.micronaut.application") version "3.1.1"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.4.10"
}

version = "0.1"
group = "dev.martiniano"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

micronaut {
    //runtime("lambda")
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("dev.martiniano.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.micronaut:micronaut-validation")

    implementation("io.micronaut.aws:micronaut-function-aws")
    implementation("io.micronaut.aws:micronaut-function-aws-api-proxy")

    implementation("io.micronaut.mongodb:micronaut-mongo-sync")
    implementation("jakarta.annotation:jakarta.annotation-api")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:testcontainers")

}

application {
    mainClass.set("dev.martiniano.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

noArg {
    annotation("dev.martiniano.domain.annotation.NoArg")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
tasks.named("assemble") {
    dependsOn(":shadowJar")
}