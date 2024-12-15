package dev.mbo.mosqui2.server.config.model

import dev.mbo.logging.logger
import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.UUID

@Configuration
@ConfigurationProperties(prefix = "mqtt")
data class MqttConfiguration(
    var brokerUrls: List<String> = listOf("tcp://localhost:1883"),
    var clientId: String = UUID.randomUUID().toString().substring(0, 8),
    var username: String? = null,
    var password: String? = null,
    var connectionTimeout: Int = 60, // seconds
    var automaticReconnect: Boolean = true,
    var keepAliveInterval: Int = 30, // seconds
    var sessionExpiryInterval: Long = 60L // seconds
) {

    private val log = logger()

    @PostConstruct
    private fun init() {
        log.debug("mqtt config: {}", this)
    }

}