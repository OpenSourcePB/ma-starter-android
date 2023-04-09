package az.pashabank.data.remote

import az.pashabank.data.remote.model.card.CardRemoteDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CardApi {
    @GET("pb/v1/customers/{customerId}/cards")
    suspend fun getCards(
        @Path("customerId") customerId: String
    ): List<CardRemoteDto>
}