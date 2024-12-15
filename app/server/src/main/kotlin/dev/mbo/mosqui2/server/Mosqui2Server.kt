package dev.mbo.mosqui2.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class Mosqui2Server

fun main(args: Array<String>) {
    runApplication<Mosqui2Server>(*args)
}
