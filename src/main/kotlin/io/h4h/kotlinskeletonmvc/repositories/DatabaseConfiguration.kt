package io.h4h.kotlinskeletonmvc.repositories


import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import io.h4h.kotlinskeletonmvc.utils.SecretManager
import org.litote.kmongo.KMongo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.Scope


// =========================================================================
// MongoDb Configuration
// =========================================================================

@Configuration
@Profile("!test")
class DatabaseConfiguration {


    @Value("\${gcp-project-id}")
    private val gcpProjectId: String? = null


    @Value("\${gcp-mongo-secret-name}")
    private val mongoSecretName: String? = null


    var logger: Logger = LoggerFactory.getLogger(DatabaseConfiguration::class.java)


    /**
     * Initializes MongoDb client.
     * Reads connection string from GCP Secret Manager
     * */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public fun mongoClient(): MongoClient {
        logger.info("starting MongoDb initialization ...")

        // get configuration values
        val projectId = gcpProjectId
            ?: throw AssertionError("Could not retrieve \"gcp-project-id\" property.")
        val secretName = mongoSecretName
            ?: throw AssertionError("Could not retrieve \"gcp-mongo-secret-name\" property.")

        // get database credentials from Secret Manager
        val mongoUriSecret = SecretManager.getSecret(projectId, secretName)

        // MongoDb connection settings
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(mongoUriSecret))
            .build()

        // initialize MongoDb client
        return KMongo.createClient(settings)
    }





}