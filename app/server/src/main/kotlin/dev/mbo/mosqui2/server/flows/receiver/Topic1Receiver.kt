package dev.mbo.mosqui2.server.flows.receiver

import dev.mbo.logging.logger
import dev.mbo.mosqui2.server.flows.shared.TopicsAndChannels
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
    @Qualifier(TopicsAndChannels.TOPIC1_CHANNEL)
    fun topic1Channel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    fun topic1InboundFlow(
        mqttClientManager: Mqttv5ClientManager, @Qualifier(TopicsAndChannels.TOPIC1_CHANNEL) topic1Channel: MessageChannel
    ): IntegrationFlow {
        return IntegrationFlow.from(
            Mqttv5PahoMessageDrivenChannelAdapter(mqttClientManager, TopicsAndChannels.TOPIC1).apply {
                outputChannel = topic1Channel
            }).get()
    }

    @Bean
    fun topic1Processor(@Qualifier(TopicsAndChannels.TOPIC1_CHANNEL) topic1Channel: MessageChannel): IntegrationFlow {
        return IntegrationFlow.from(topic1Channel).handle { payload: ByteArray, _ ->
            log.info("Received message from MQTT topic1: {}", String(payload))
        }.get()
    }

}