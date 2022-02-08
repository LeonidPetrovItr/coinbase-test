package by.ld1995.coinbasetest.models.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.math.BigDecimal

data class Snapshot(
    val type: String,
    @JsonProperty("product_id") val productId: String,
    val asks: ArrayList<SnapshotItem>,
    val bids: ArrayList<SnapshotItem>
)

@JsonDeserialize(using = UpdateItemDeserializer::class)
data class SnapshotItem(val price: BigDecimal, val size: BigDecimal)

class SnapshotItemDeserializer : StdDeserializer<SnapshotItem>(SnapshotItem::class.java) {

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext?): SnapshotItem {
        val element: JsonNode = parser.codec.readTree(parser)
        return SnapshotItem(
            element.get(PRICE).asText().toBigDecimal(),
            element.get(SIZE).asText().toBigDecimal()
        )
    }

    companion object {
        const val PRICE = 1
        const val SIZE = 2
    }
}
