package dev.mbo.mosqui2.server.config

import org.eclipse.paho.mqttv5.client.MqttConnectionOptionsBuilder
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.mqtt.core.Mqttv5ClientManager
import java.nio.charset.StandardCharsets

@Configuration
class BrokerConfiguration(
    private val mqttConfiguration: MqttConfiguration,
) {

    @Bean
    fun mqttClientFactory(): Mqttv5ClientManager {
        val options = MqttConnectionOptionsBuilder()
            .serverURIs(mqttConfiguration.brokerUrls.toTypedArray())
            .connectionTimeout(mqttConfiguration.connectionTimeout)
            .keepAliveInterval(mqttConfiguration.keepAliveInterval)
            .automaticReconnect(mqttConfiguration.automaticReconnect)
            .sessionExpiryInterval(mqttConfiguration.sessionExpiryInterval)

        // use credentials if provided
        mqttConfiguration.username
            ?.takeIf { it.isNotBlank() }
            ?.let(options::username)
        mqttConfiguration.password
            ?.takeIf { it.isNotBlank() }
            ?.let { options.password(it.toByteArray(StandardCharsets.UTF_8)) }

        return Mqttv5ClientManager(options.build(), mqttConfiguration.clientId).apply {
            setPersistence(MemoryPersistence())
        }
    }
}