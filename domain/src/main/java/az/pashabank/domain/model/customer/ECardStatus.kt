package az.pashabank.domain.model.customer

enum class ECardStatus {
    ACTIVE, BLOCKED, EXPIRED;

    companion object {

        fun getCardStatus(name: String): ECardStatus {
            return when (name) {
                "active" -> ACTIVE
                "blocked" -> BLOCKED
                "expired" -> EXPIRED
                else -> ACTIVE
            }
        }
    }
}