package digital.pashabank.data.local.customer

import androidx.room.Database
import androidx.room.RoomDatabase
import digital.pashabank.data.local.customer.model.card.CardLocalDto
import digital.pashabank.data.local.customer.model.customer.CustomerLocalDto
import digital.pashabank.data.local.customer.model.transaction.TransactionLocalDto

@Database(
    entities = [CustomerLocalDto::class, CardLocalDto::class, TransactionLocalDto::class],
    version = 1,
    exportSchema = false
)
abstract class CustomerDatabase: RoomDatabase() {
    abstract fun customerDao(): CustomerDao
}