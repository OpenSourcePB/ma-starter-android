package az.pashabank.domain.model.customer

enum class ETransactionCategory(name: String) {
    GROCERY("grocery"), FUEL("fuel"), HEALTH("health"), UNKNOWN("unknown");

    companion object {

        fun getCategory(name: String): ETransactionCategory {
            return when (name) {
                "grocery" -> GROCERY
                "fuel" -> FUEL
                "health" -> HEALTH
                else -> UNKNOWN
            }
        }
    }
}