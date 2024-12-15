package dev.mbo.mosqui2.server.config

import dev.mbo.logging.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.messaging.Message

@Configuration
class ErrorHandlingConfiguration {

    private val log = logger()

    @Bean(name = [CHANNEL])
    fun errorChannel(): PublishSubscribeChannel {
        return PublishSubscribeChannel()
    }

    @Bean
    fun errorFlow(): IntegrationFlow {
        return IntegrationFlow.from(CHANNEL)
            .handle { message: Message<*>, _ ->
                val exception = message.headers["integrationMessageHandlingException"] as? Throwable
                log.error(
                    "Processing message failed: {}, exception: {}",
                    message.payload,
                    exception?.message,
                    exception
                )
            }
            .get()
    }

    companion object {
        private const val CHANNEL = "errorChannel"
    }
}