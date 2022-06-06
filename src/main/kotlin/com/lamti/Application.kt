package com.lamti

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.lamti.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        configureTemplating()
        configureMonitoring()
        configureHTTP()
//        configureSecurity()
    }.start(wait = true)
}
