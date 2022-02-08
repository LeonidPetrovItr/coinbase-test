package by.ld1995.coinbasetest.controllers

import by.ld1995.coinbasetest.models.Item
import by.ld1995.coinbasetest.models.OrderBook
import by.ld1995.coinbasetest.services.OrderBookStorage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/order-book"])
class OrderBookController(
    private val orderBookStorage: OrderBookStorage
) {

    @GetMapping("/best/sell")
    fun getBestForSell(): Item = orderBookStorage.getBestForSell()

    @GetMapping("/best/buy")
    fun getBestForBuy(): Item = orderBookStorage.getBestForBuy()
}