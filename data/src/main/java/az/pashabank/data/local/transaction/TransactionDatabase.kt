package az.pashabank.data.local.transaction

import androidx.room.Database
import androidx.room.RoomDatabase
import az.pashabank.data.local.transaction.model.TransactionLocalDto

@Database(
    entities = [TransactionLocalDto::class],
    version = 1,
    exportSchema = false
)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}