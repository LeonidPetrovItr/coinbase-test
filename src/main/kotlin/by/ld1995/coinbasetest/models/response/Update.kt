package by.ld1995.coinbasetest.models.response

import by.ld1995.coinbasetest.models.OrderSide
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.math.BigDecimal
import java.time.Instant

data class Update(
    val type: String,
    @JsonProperty("product_id") val productId: String,
    val changes: List<UpdateItem>,
    val time: Instant
)

@JsonDeserialize(using = UpdateItemDeserializer::class)
data class UpdateItem(val side: OrderSide, val price: BigDecimal, val size: BigDecimal)

class UpdateItemDeserializer : StdDeserializer<UpdateItem>(UpdateItem::class.java) {

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext?): UpdateItem {
        val element: JsonNode = parser.codec.readTree(parser)
        return UpdateItem(
            OrderSide.getByLabel(element.get(SIDE).asText())!!,
            element.get(PRICE).asText().toBigDecimal(),
            element.get(SIZE).asText().toBigDecimal()
        )
    }

    companion object {
        const val SIDE = 0
        const val PRICE = 1
        const val SIZE = 2
    }
}