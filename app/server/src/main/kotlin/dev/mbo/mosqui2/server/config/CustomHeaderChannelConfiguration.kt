package dev.mbo.mosqui2.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DefaultHeaderChannelRegistry

@Configuration
class CustomHeaderChannelConfiguration {

    @Bean
    fun integrationHeaderChannelRegistry(): DefaultHeaderChannelRegistry {
        return DefaultHeaderChannelRegistry()
    }

}