package az.pashabank.data.local.card

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import az.pashabank.data.local.card.model.CardLocalDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(list: List<CardLocalDto>)

    @Query("SELECT * from card_table WHERE customer_id = :customerId")
    fun observeCards(customerId: String): Flow<List<CardLocalDto>>

    @Query("DELETE FROM card_table WHERE customer_id=:customerId")
    fun clearCards(customerId: String)
}