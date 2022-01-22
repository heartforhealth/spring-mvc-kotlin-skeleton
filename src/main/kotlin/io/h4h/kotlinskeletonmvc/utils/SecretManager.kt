package io.h4h.kotlinskeletonmvc.utils

import com.google.cloud.secretmanager.v1.SecretManagerServiceClient
import com.google.cloud.secretmanager.v1.SecretVersionName
import java.util.concurrent.TimeUnit


class SecretManager {


    companion object {

        /**
        Returns latest secret value from GCP Secret Manager
         */
        fun getSecret(projectId: String, secretId: String): String {
            val client = SecretManagerServiceClient.create()
            // construct reference to secret
            val secretVersionName = SecretVersionName.of(projectId, secretId, "latest")
            // Access the secret version.
            val response = client.accessSecretVersion(secretVersionName)

            // Make sure to call shutdown()/shutdownNow() and wait until awaitTermination() returns true.
            client.shutdownNow()
            client.awaitTermination(2, TimeUnit.SECONDS)

            // return the secret payload.
            return response
                .payload
                .data
                .toStringUtf8()
        }

    }
}