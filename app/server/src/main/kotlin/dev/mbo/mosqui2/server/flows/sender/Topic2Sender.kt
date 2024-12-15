package dev.mbo.mosqui2.server.flows.sender

import dev.mbo.mosqui2.server.flows.shared.MqttHeaders
import dev.mbo.mosqui2.server.flows.shared.TopicsAndChannels
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.MessageChannel
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Topic2Sender(
    @Qualifier(TopicsAndChannels.OUT_CHANNEL) private val outChannel: MessageChannel
) {

    @Scheduled(fixedDelay = 5000)
    fun sendMessageToTopic() {
        val mqttMessage = MessageBuilder.withPayload("test message")
            .setHeader(MqttHeaders.TARGET_TOPIC, TopicsAndChannels.TOPIC2)
            .build()
        outChannel.send(mqttMessage)
    }

}