package by.ld1995.coinbasetest.services

import by.ld1995.coinbasetest.models.Item
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class OrderBookStorage {
    private val asks = TreeMap<BigDecimal, BigDecimal>()
    private val bids = TreeMap<BigDecimal, BigDecimal>(Collections.reverseOrder())

    fun addAsk(price: BigDecimal, size: BigDecimal) {
        asks[price] = size
    }

    fun addBid(price: BigDecimal, size: BigDecimal) {
        bids[price] = size
    }

    fun getBestForSell(): Item {
        val entry = asks.firstEntry()
        return Item(entry.key, entry.value)
    }

    fun getBestForBuy(): Item {
        val entry = bids.firstEntry()
        return Item(entry.key, entry.value)
    }
}