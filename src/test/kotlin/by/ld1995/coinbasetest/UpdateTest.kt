package by.ld1995.coinbasetest

import by.ld1995.coinbasetest.models.response.Update
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UpdateTest {

    private val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Test
    internal fun name() {
        val response = "{\n" +
                "   \"type\":\"l2update\",\n" +
                "   \"product_id\":\"BTC-USD\",\n" +
                "   \"changes\":[\n" +
                "      [\n" +
                "         \"buy\",\n" +
                "         \"43924.56\",\n" +
                "         \"0.00400000\"\n" +
                "      ],\n" +
                "      [\n" +
                "         \"sell\",\n" +
                "         \"43924.56\",\n" +
                "         \"0.00400000\"\n" +
                "      ]\n" +
                "   ],\n" +
                "   \"time\":\"2022-02-08T11:20:07.353846Z\"\n" +
                "}"
        val value = mapper.readValue(response, Update::class.java)
        assertFalse(value.changes.isEmpty())
    }
}