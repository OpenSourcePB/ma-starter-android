package az.pashabank.data.local.customer

import androidx.room.Database
import androidx.room.RoomDatabase
import az.pashabank.data.local.customer.model.CustomerLocalDto

@Database(
    entities = [CustomerLocalDto::class],
    version = 1,
    exportSchema = false
)
abstract class CustomerDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
}