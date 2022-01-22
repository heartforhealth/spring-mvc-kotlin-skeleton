import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "io.h4h"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

extra["springCloudGcpVersion"] = "3.0.0"
extra["springCloudVersion"] = "2021.0.0"

dependencies {

	// Spring stuff
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Kotlin stuff
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


	// Google Cloud - use the SDKs directly instead of the starters
	// https://mvnrepository.com/artifact/com.google.cloud/google-cloud-pubsub
	implementation("com.google.cloud:google-cloud-pubsub:1.115.1")
	// https://mvnrepository.com/artifact/com.google.cloud/google-cloud-secretmanager
	implementation("com.google.cloud:google-cloud-secretmanager:2.0.7")


	// MongoDb - sync driver, this would be the choice for Spring MVC
	implementation("org.litote.kmongo:kmongo:4.4.0")


	// HL7 FHIR R4 - the default data model, used almost anywhere
	implementation("ca.uhn.hapi.fhir:hapi-fhir-structures-r4:5.6.2")


	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:${property("springCloudGcpVersion")}")
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
