plugins {
    java
    jacoco
    id("org.sonarqube") version "6.1.0.5360"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.qa"
version = "0.0.1-SNAPSHOT"

val mapstructVersion = "1.6.3"
val lombokVersion = "1.18.32"
val lombokMapStructBindingVersion = "0.2.0"
val jjwtVersion = "0.12.6"
val flywayVersion = "11.7.2"
val postgresqlVersion = "42.7.3"
val openFeignVersion = "4.2.1"
val commonsIoVersion = "2.19.0"
val commonsUploadVersion = "1.5"
val commonsLang3Version = "3.14.0"
val h2Version = "2.3.232"
val assertjVersion = "3.27.3"
val mockitoVersion = "5.17.0"
val validationApiVersion = "3.1.1"
var swaggerVersion = "2.8.6"
val junitBomVersion = "5.12.2"

val jacocoIncludes = listOf(
    "com.qa.bugkeeper.service.*",
    "com.qa.bugkeeper.controller.*",
    "com.qa.bugkeeper.security.*"
)

val sonarIncludes = listOf(
    "**/service/**",
    "**/controller/**",
    "**/security/**"
)

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"
            includes = jacocoIncludes
            limit {
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

sonarqube {
    properties {
        property("sonar.projectKey", "kopendozz_BugKeeper")
        property("sonar.organization", "kopendozz")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.inclusions", sonarIncludes.joinToString(","))
        property("sonar.issue.ignore.multicriteria.dependencyGroup.ruleKey", "kotlin:S6629") //ignores Dependencies should be grouped by destination
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Core
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Security - JWT
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    // Configuration Properties support
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // MapStruct
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // Database + Migration
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:$flywayVersion")

    // Spring Cloud OpenFeign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$openFeignVersion")

    // Commons Utilities
    implementation("commons-io:commons-io:$commonsIoVersion")
    implementation("commons-fileupload:commons-fileupload:$commonsUploadVersion")
    implementation("org.apache.commons:commons-lang3:$commonsLang3Version")

    // Dev Tools
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapStructBindingVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // Validation
    implementation("jakarta.validation:jakarta.validation-api:$validationApiVersion")

    // Swagger API Docs
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("com.h2database:h2:$h2Version")
    testImplementation(platform("org.junit:junit-bom:$junitBomVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
