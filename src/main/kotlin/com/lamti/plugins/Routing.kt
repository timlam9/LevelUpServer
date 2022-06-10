package com.lamti.plugins

import com.lamti.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val USER_ID = "user_id"
private const val ROUTE_MAIN = "/"
private const val ROUTE_NOTES = "/notes"
private const val ROUTE_POST_NOTE = "/postNote"
private const val WELCOME_TO_LEVEL_UP = "Welcome, to LevelUp API!\n\nRoutes:\n1. /notes\n2. /postNote"
const val EMPTY = ""

fun Application.configureRouting(repository: Repository = Repository()) {
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
            val response = repository.getNotes(userID)
            call.respond(response)
        }
        post(ROUTE_POST_NOTE) {
            val userID = call.request.queryParameters[USER_ID] ?: EMPTY
            val note = call.receive<Note>()
            val response = repository.addNote(userID, note)
            call.respond(response)
        }
        put(ROUTE_POST_NOTE) {
            val userID = call.request.queryParameters[USER_ID] ?: EMPTY
            val note = call.receive<Note>()
            val response = repository.updateNote(userID, note)
            call.respond(response)
        }
        delete(ROUTE_POST_NOTE) {
            val userID = call.request.queryParameters[USER_ID] ?: EMPTY
            val note = call.receive<Note>()
            val response = repository.deleteNote(userID, note)
            call.respond(response)
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()