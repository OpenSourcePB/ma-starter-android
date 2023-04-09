package az.pashabank.domain.model.customer

import java.math.BigDecimal

data class Card(
    val id: String,
    val customerId: String,
    val currency: Currency,
    val status: ECardStatus,
    val type: ECardType,
    val balance: BigDecimal,
    val pan: String,
    val createdAt: String
) {
    fun balanceText(): String {
        return "$balance $currency"
    }

    fun lastPanDigits(): CharSequence {
        return "*"+pan.split("-").last()
    }
}
