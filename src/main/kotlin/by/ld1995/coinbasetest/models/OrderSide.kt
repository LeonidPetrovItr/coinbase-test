package by.ld1995.coinbasetest.models

enum class OrderSide(val label: String) {
    ASK("sell"), BID("buy");

    companion object {
        private val map = values().associateBy(OrderSide::label)
        fun getByLabel(label: String) = map[label]
    }
}