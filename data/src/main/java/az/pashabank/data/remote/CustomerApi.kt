package az.pashabank.data.remote

import az.pashabank.data.remote.model.customer.CustomerRemoteDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CustomerApi {
    @GET("pb/v1/customers/{customerId}")
    suspend fun getCustomer(@Path("customerId") customerId: String): CustomerRemoteDto
}