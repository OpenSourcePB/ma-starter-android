package az.pashabank.data.local.transaction.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "transaction_table",
    primaryKeys = ["id"]
)
data class TransactionLocalDto(
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "card_id") val cardId: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "amount") val amount: String,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "created_at") val createdAt: String
)
