package by.ld1995.coinbasetest.models.events

import org.springframework.context.ApplicationEvent

data class WSDisconnectedEvent(val code: Int) : ApplicationEvent(code)