package by.ld1995.coinbasetest

import by.ld1995.coinbasetest.models.response.Snapshot
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SnapshotTest {
    private val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    @Test
    internal fun name() {
        val response = "{\n" +
                "   \"type\":\"snapshot\",\n" +
                "   \"product_id\":\"BTC-USD\",\n" +
                "   \"asks\":[\n" +
                "      [\n" +
                "         \"43925.05\",\n" +
                "         \"0.01426399\"\n" +
                "      ],\n" +
                "      [\n" +
                "         \"43925.30\",\n" +
                "         \"0.05692160\"\n" +
                "      ],\n" +
                "      [\n" +
                "         \"43927.45\",\n" +
                "         \"0.22665765\"\n" +
                "      ]\n" +
                "   ],\n" +
                "   \"bids\":[\n" +
                "      [\n" +
                "         \"43924.56\",\n" +
                "         \"0.00100000\"\n" +
                "      ],\n" +
                "      [\n" +
                "         \"43924.55\",\n" +
                "         \"0.02852595\"\n" +
                "      ]\n" +
                "   ]\n" +
                "}"

        val value = mapper.readValue(response, Snapshot::class.java)
        Assertions.assertFalse(value.asks.isEmpty())
        Assertions.assertFalse(value.bids.isEmpty())
    }
}