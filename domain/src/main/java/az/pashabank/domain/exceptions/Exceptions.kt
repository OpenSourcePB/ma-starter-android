package az.pashabank.domain.exceptions

import java.io.IOException

abstract class HandledException(
    cause: Throwable? = null,
    message: String? = null
) : IOException(message, cause)

class UnknownError(cause: Throwable?) : HandledException(cause)

class NetworkError(cause: Throwable?) : HandledException(cause)

sealed class ServerError(
    open val serverCode: String,
    open val serverMessage: String
) : HandledException() {

    data class NotAuthorized(
        override val serverCode: String,
        override val serverMessage: String
    ) : ServerError(serverCode, serverMessage)

    data class Unexpected(
        override val serverCode: String,
        override val serverMessage: String
    ) : ServerError(serverCode, serverMessage)

    data class ServerIsDown(
        override val serverCode: String,
        override val serverMessage: String
    ) : ServerError(serverCode, serverMessage)
}

