package com.tgaines.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.config.server.config.ConfigServerProperties
import org.springframework.cloud.config.server.environment.AwsS3EnvironmentProperties
import org.springframework.cloud.config.server.environment.AwsS3EnvironmentRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.util.StringUtils
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI


@SpringBootApplication
@EnableConfigServer
class ConfigApplication

fun main(args: Array<String>) {
	runApplication<ConfigApplication>(*args)
}

@Configuration
@Profile("awss3")
class Configuration {
	@Bean
	fun s3EnvironmentRepository(environmentProperties: AwsS3EnvironmentProperties, server: ConfigServerProperties): AwsS3EnvironmentRepository {
		val clientBuilder = S3Client.builder()

		if (StringUtils.hasText(environmentProperties.region)) {
			clientBuilder.region(Region.of(environmentProperties.region))
		}

		clientBuilder.endpointOverride(URI.create("http://localhost:9000")).forcePathStyle(true)

		val client = clientBuilder.build()
		val repository = AwsS3EnvironmentRepository(
			client,
			environmentProperties.bucket, server
		)

		repository.order = environmentProperties.order
		return repository
	}
}
