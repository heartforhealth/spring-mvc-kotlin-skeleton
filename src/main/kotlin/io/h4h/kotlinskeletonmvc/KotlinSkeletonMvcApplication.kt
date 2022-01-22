package io.h4h.kotlinskeletonmvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [MongoAutoConfiguration::class, MongoReactiveAutoConfiguration::class])
class KotlinSkeletonMvcApplication


fun main(args: Array<String>) {
	runApplication<KotlinSkeletonMvcApplication>(*args)
}
