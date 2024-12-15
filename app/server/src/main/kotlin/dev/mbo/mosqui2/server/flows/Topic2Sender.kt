package dev.mbo.mosqui2.server.flows

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.MessageChannel
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Topic2Sender(
    @Qualifier("outChannel") private val outChannel: MessageChannel
) {

    @Scheduled(fixedDelay = 5000)
    fun sendMessageToTopic() {
        val mqttMessage = MessageBuilder.withPayload("test message")
            .setHeader("mqtt_topic", "topic2")
            .build()
        outChannel.send(mqttMessage)
    }

}