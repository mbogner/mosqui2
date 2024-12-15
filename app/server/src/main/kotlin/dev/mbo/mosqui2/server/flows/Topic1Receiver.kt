package dev.mbo.mosqui2.server.flows

import dev.mbo.logging.logger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.mqtt.core.Mqttv5ClientManager
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter
import org.springframework.messaging.MessageChannel

@Configuration
class Topic1Receiver {

    private val log = logger()

    @Bean
    @Qualifier("topic1Channel")
    fun topic1Channel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    fun topic1InboundFlow(
        mqttClientManager: Mqttv5ClientManager,
        @Qualifier("topic1Channel") topic1Channel: MessageChannel
    ): IntegrationFlow {
        return IntegrationFlow.from(
            Mqttv5PahoMessageDrivenChannelAdapter(mqttClientManager, "topic1").apply {
                outputChannel = topic1Channel
            }
        ).get()
    }

    @Bean
    fun topic1Processor(@Qualifier("topic1Channel") topic1Channel: MessageChannel): IntegrationFlow {
        return IntegrationFlow.from(topic1Channel)
            .handle { payload: ByteArray, _ ->
                log.info("Received message from MQTT topic1: {}", String(payload))
            }
            .get()
    }

}