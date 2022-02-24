package digital.pashabank.data.remote

import digital.pashabank.data.remote.model.card.CardRemoteDto
import digital.pashabank.data.remote.model.customer.CustomerRemoteDto
import digital.pashabank.data.remote.model.transaction.TransactionRemoteDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CustomerApi {

    @GET("pb/v1/customers")
    suspend fun getCustomers(): List<CustomerRemoteDto>

    @GET("pb/v1/customers/{customerId}/cards")
    suspend fun getCards(
        @Path("customerId") customerId: String
    ): List<CardRemoteDto>

    @GET("pb/v1/customers/{customerId}/cards/{cardId}/transactions")
    suspend fun getTransactions(
        @Path("customerId") customerId: String,
        @Path("cardId") cardId: String
    ): List<TransactionRemoteDto>

}