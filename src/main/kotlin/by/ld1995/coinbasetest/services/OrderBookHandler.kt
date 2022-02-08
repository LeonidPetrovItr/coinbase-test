package by.ld1995.coinbasetest.services

import by.ld1995.coinbasetest.models.OrderSide
import by.ld1995.coinbasetest.models.events.WSDisconnectedEvent
import by.ld1995.coinbasetest.models.response.UpdateItem
import org.apache.logging.log4j.util.Strings
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.web.socket.*
import java.math.BigDecimal

@Component
class OrderBookHandler(
    private val eventPublisher: ApplicationEventPublisher,
    private val messageHandler: MessageHandler,
    private val orderBookStorage: OrderBookStorage
) : WebSocketHandler {

    private var snapshotBuffer = StringBuffer()

    private val logger = LoggerFactory.getLogger(OrderBookHandler::class.java)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info("Connected")
        session.sendMessage(TextMessage("{\"type\":\"subscribe\",\"channels\":[{\"name\":\"level2\",\"product_ids\":[\"BTC-USD\"]},{\"name\":\"heartbeat\",\"product_ids\":[\"BTC-USD\"]}]}"))
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        val payload = message.payload as String
        if (messageHandler.isValidJson(payload)) {
            if (messageHandler.isUpdate(payload)) {
                val map: Map<OrderSide, List<UpdateItem>> = messageHandler.getUpdate(payload)
                    .changes
                    .filter { BigDecimal.ZERO.setScale(8) > it.size }
                    .groupBy { it.side }
                map.getOrDefault(OrderSide.ASK, emptyList()).forEach { orderBookStorage.addAsk(it.price, it.size) }
                map.getOrDefault(OrderSide.BID, emptyList()).forEach { orderBookStorage.addBid(it.price, it.size) }
            }
        } else {
            if (Strings.isNotEmpty(snapshotBuffer) && payload.endsWith("}")) {
                val snapshotJson = snapshotBuffer.append(payload).toString()
                snapshotBuffer = StringBuffer()
                val snapshot = messageHandler.getSnapshot(snapshotJson)
                snapshot.asks
                    .forEach { orderBookStorage.addAsk(it.price, it.size) }
                snapshot.bids
                    .forEach { orderBookStorage.addBid(it.price, it.size) }
            } else {
                snapshotBuffer.append(payload)
            }
        }
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        logger.info("Error")
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        logger.info("Connection closed")
        eventPublisher.publishEvent(WSDisconnectedEvent(closeStatus.code))
    }

    override fun supportsPartialMessages(): Boolean = true
}
