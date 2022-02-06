plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("org.jetbrains.kotlin.kapt") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.10"
    id("io.micronaut.library") version "3.1.1"
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("io.micronaut.application") version "3.1.1"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.4.10"
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("jacoco")
}

version = "0.1"
group = "dev.martiniano"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

micronaut {
    // runtime("lambda")
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("dev.martiniano.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-runtime:3.3.0")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime:3.1.0")
    implementation("jakarta.annotation:jakarta.annotation-api:2.0.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.10")
    implementation("io.micronaut:micronaut-validation:3.3.0")

    implementation("io.micronaut.aws:micronaut-function-aws:3.1.1")
    implementation("io.micronaut.aws:micronaut-function-aws-api-proxy:3.1.1")
    implementation("io.micronaut.security:micronaut-security-annotations:3.3.0")
    implementation("io.micronaut.security:micronaut-security-jwt:3.3.0")
    implementation("io.micronaut.mongodb:micronaut-mongo-sync:4.0.0")
    implementation("io.micronaut.reactor:micronaut-reactor:2.1.1")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client:2.1.1")
    implementation("jakarta.annotation:jakarta.annotation-api:2.0.0")

    implementation("org.jasypt:jasypt:1.9.3")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")

    testImplementation("io.micronaut:micronaut-http-client:3.3.0")
    testImplementation("org.testcontainers:junit-jupiter:1.16.3")
    testImplementation("org.testcontainers:mongodb:1.16.3")
    testImplementation("org.testcontainers:testcontainers:1.16.3")
    testImplementation("io.mockk:mockk:1.12.2")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")
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

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

val excludePackages: Iterable<String> = listOf(
    "dev/martiniano/application/Application**",
    "dev/martiniano/domain/entity/*",
)

jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }

    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude(excludePackages)
        }
    )
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test, tasks.jacocoTestReport)
    violationRules {
        rule {
            limit {
                minimum = "0.1".toBigDecimal()
                counter = "LINE"
            }
        }
        rule {
            limit {
                minimum = "0.0".toBigDecimal()
                counter = "BRANCH"
            }
        }
    }

    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude(excludePackages)
        }
    )
}

tasks.check {
    dependsOn("test", "jacocoTestReport", "jacocoTestCoverageVerification", "ktlintCheck", "detekt")
}

detekt {
    source = files("src")
    config = files("detekt-config.yml")
}
