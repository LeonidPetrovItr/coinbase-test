package by.ld1995.coinbasetest.services

import by.ld1995.coinbasetest.models.events.WSDisconnectedEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.client.WebSocketConnectionManager
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import javax.annotation.PostConstruct


@Component
class WSClient(
    @Value("\${ws.coinbase}") private val coinbaseWSUrl: String,
    private val orderBookHandler: OrderBookHandler,
    private val standardWebSocketClient: StandardWebSocketClient,
)  : ApplicationListener<WSDisconnectedEvent> {
    private var connection = WebSocketConnectionManager(standardWebSocketClient, orderBookHandler, coinbaseWSUrl)

    @PostConstruct
    private final fun startConnection() {
        connection.start()
    }

    fun openConnection() {
        connection = WebSocketConnectionManager(standardWebSocketClient, orderBookHandler, coinbaseWSUrl)
        connection.start()
    }

    override fun onApplicationEvent(event: WSDisconnectedEvent) {
        openConnection()
    }
}