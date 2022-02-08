package by.ld1995.coinbasetest.models.response

enum class ResponseType(val label: String) {
//    SUBSCRIPTIONS("subscriptions"),
    SNAPSHOT("snapshot"),
//    ERROR("error"),
    UPDATE("l2update");

    companion object {
        private val map = values().associateBy(ResponseType::label)
        fun getByLabel(label: String) = map[label]
    }
}