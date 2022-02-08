package by.ld1995.coinbasetest.services

import by.ld1995.coinbasetest.models.events.WSDisconnectedEvent
import by.ld1995.coinbasetest.models.response.Snapshot
import by.ld1995.coinbasetest.models.response.Update
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.web.socket.*

@Component
class OrderBookHandler(
    private val eventPublisher: ApplicationEventPublisher,
    private val messageHandler: MessageHandler,
) : WebSocketHandler {

    private val logger = LoggerFactory.getLogger(OrderBookHandler::class.java)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info("Connected")
        session.sendMessage(TextMessage("{\"type\":\"subscribe\",\"channels\":[{\"name\":\"level2\",\"product_ids\":[\"BTC-USD\"]},{\"name\":\"heartbeat\",\"product_ids\":[\"BTC-USD\"]}]}"))
    }


    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        logger.info("Message ${message.payload}")
        val payload = message.payload as String
        if (messageHandler.isSupportedType(payload)) {
            if (messageHandler.isSnapshot(payload)) {
                // collect to one string
                val snapshot: Snapshot = messageHandler.getSnapshot(payload)
            }
            if (messageHandler.isUpdate(payload)) {
                val update: Update = messageHandler.getUpdate(payload)
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
