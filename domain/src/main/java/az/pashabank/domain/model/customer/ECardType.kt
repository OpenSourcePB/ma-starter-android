package az.pashabank.domain.model.customer

enum class ECardType {
    VISA, MASTER, UNION_PAY;

    companion object {

        fun getCardType(name: String): ECardType {
            return when (name) {
                "visa" -> VISA
                "master" -> MASTER
                "union_pay" -> UNION_PAY
                else -> VISA
            }
        }
    }
}