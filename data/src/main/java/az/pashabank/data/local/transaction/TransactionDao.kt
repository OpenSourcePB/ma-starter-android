package az.pashabank.data.local.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import az.pashabank.data.local.transaction.model.TransactionLocalDto
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(list: List<TransactionLocalDto>)

    @Query("SELECT * from transaction_table WHERE card_id = :cardId ORDER BY card_id, datetime(created_at) DESC")
    fun observeTransactions(cardId: String): Flow<List<TransactionLocalDto>>

    @Query("DELETE FROM transaction_table WHERE card_id=:cardId ")
    fun clearTransactions(cardId: String)
}