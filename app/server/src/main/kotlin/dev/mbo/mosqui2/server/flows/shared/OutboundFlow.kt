package dev.mbo.mosqui2.server.flows.shared

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.mqtt.core.Mqttv5ClientManager
import org.springframework.integration.mqtt.outbound.Mqttv5PahoMessageHandler
import org.springframework.messaging.MessageChannel

@Configuration
class OutboundFlow {

    @Bean
    @Qualifier(TopicsAndChannels.OUT_CHANNEL)
    fun outChannel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    fun mqttOutFlow(
        mqttClientManager: Mqttv5ClientManager,
        @Qualifier(TopicsAndChannels.OUT_CHANNEL) outChannel: MessageChannel
    ): IntegrationFlow {
        return IntegrationFlow.from(outChannel)
            .handle(Mqttv5PahoMessageHandler(mqttClientManager)) // we could set default topic - without gives exc
            .get()
    }
}