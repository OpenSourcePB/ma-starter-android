package az.pashabank.data.local.card.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "card_table",
    primaryKeys = ["id"]
)
data class CardLocalDto(
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "customer_id")  val customerId: String,
    @ColumnInfo(name = "currency")  val currency: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "balance") val balance: String,
    @ColumnInfo(name = "pan") val pan: String,
    @ColumnInfo(name = "created_at") val createdAt: String
)
