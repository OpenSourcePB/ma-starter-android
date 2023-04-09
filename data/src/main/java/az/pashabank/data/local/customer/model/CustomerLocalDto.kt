package az.pashabank.data.local.customer.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "customer_table",
    primaryKeys = ["id"]
)
data class CustomerLocalDto(
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "created_at") val createdAt: String
)
