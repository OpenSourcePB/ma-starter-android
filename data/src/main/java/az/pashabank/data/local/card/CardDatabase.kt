package az.pashabank.data.local.card

import androidx.room.Database
import androidx.room.RoomDatabase
import az.pashabank.data.local.card.model.CardLocalDto

@Database(
    entities = [CardLocalDto::class],
    version = 1,
    exportSchema = false
)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}