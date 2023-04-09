package az.pashabank.data.remote.error

import kotlinx.serialization.Serializable

@Serializable
class ServerProblemDescription(val code: String = "", val message: String = "")