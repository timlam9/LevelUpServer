package com.lamti.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val USER_ID = "user_id"
private const val ROUTE_MAIN = "/"
private const val ROUTE_NOTES = "/notes"
private const val ROUTE_POST_NOTE = "/postNote"
private const val WELCOME_TO_LEVEL_UP = "Welcome, to LevelUp API!\n\nRoutes:\n1. /notes\n2. /postNote"
const val EMPTY = ""

fun Application.configureRouting() {
    install(StatusPages) {
        exception<AuthenticationException> { call, _ ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { call, _ ->
            call.respond(HttpStatusCode.Forbidden)
        }
    }

    routing {
        get(ROUTE_MAIN) {
            call.respondText(WELCOME_TO_LEVEL_UP)
        }
        get(ROUTE_NOTES) {
            val userID = call.request.queryParameters[USER_ID] ?: EMPTY

            call.respond(
                if(userID == "user1") {
                    List(30) {
                        Note(
                            id = it + 1,
                            title = "Note ${it + 1}",
                            text = "Note ${it + 1} text",
                            completed = (it + 1).rem(2) == 0
                        )
                    }
                } else {
                    "No user found!"
                }
            )
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()