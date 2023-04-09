package az.pashabank.data.local.customer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import az.pashabank.data.local.customer.model.CustomerLocalDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomers(list: List<CustomerLocalDto>)

    @Query("SELECT * from customer_table")
    fun observeCustomers(): Flow<List<CustomerLocalDto>>
}