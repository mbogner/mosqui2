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
class Topic2Receiver {

    private val log = logger()

    @Bean
    @Qualifier(TopicsAndChannels.TOPIC2_CHANNEL)
    fun topic2Channel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    fun topic2InboundFlow(
        mqttClientManager: Mqttv5ClientManager,
        @Qualifier(TopicsAndChannels.TOPIC2_CHANNEL) topic2Channel: MessageChannel
    ): IntegrationFlow {
        return IntegrationFlow.from(
            Mqttv5PahoMessageDrivenChannelAdapter(mqttClientManager, TopicsAndChannels.TOPIC2).apply {
                outputChannel = topic2Channel
            }
        ).get()
    }

    @Bean
    fun topic2Processor(@Qualifier(TopicsAndChannels.TOPIC2_CHANNEL) topic2Channel: MessageChannel): IntegrationFlow {
        return IntegrationFlow.from(topic2Channel)
            .handle { payload: ByteArray, _ ->
                log.info("Received message from MQTT topic2: {}", String(payload))
            }
            .get()
    }

}