package az.pashabank.data.remote

import az.pashabank.data.remote.model.transaction.TransactionRemoteDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TransactionApi {
    @GET("pb/v1/customers/{customerId}/cards/{cardId}/transactions")
    suspend fun getTransactions(
        @Path("customerId") customerId: String,
        @Path("cardId") cardId: String
    ): List<TransactionRemoteDto>
}