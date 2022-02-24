package digital.pashabank.data.local.customer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import digital.pashabank.data.local.customer.model.card.CardLocalDto
import digital.pashabank.data.local.customer.model.customer.CustomerLocalDto
import digital.pashabank.data.local.customer.model.transaction.TransactionLocalDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomers(list: List<CustomerLocalDto>)

    @Query("SELECT * from customer_table")
    fun observeCustomers(): Flow<List<CustomerLocalDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(list: List<CardLocalDto>)

    @Query("SELECT * from card_table WHERE customer_id = :customerId")
    fun observeCards(customerId: String): Flow<List<CardLocalDto>>

    @Query("DELETE FROM card_table WHERE customer_id=:customerId")
    fun clearCards(customerId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(list: List<TransactionLocalDto>)

    @Query("SELECT * from transaction_table WHERE card_id = :cardId ORDER BY card_id, datetime(created_at) DESC")
    fun observeTransactions(cardId: String): Flow<List<TransactionLocalDto>>

    @Query("DELETE FROM transaction_table WHERE card_id=:cardId ")
    fun clearTransactions(cardId: String)

}