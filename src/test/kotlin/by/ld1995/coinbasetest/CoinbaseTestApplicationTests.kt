package by.ld1995.coinbasetest

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CoinbaseTestApplicationTests {

    @Test
    fun contextLoads() {
        // Request
        "        {\n" +
                "            \"type\": \"subscribe\",\n" +
                "            \"product_ids\": [\n" +
                "                \"BTC-USD\"\n" +
                "            ],\n" +
                "            \"channels\": [\n" +
                "                \"level2\",\n" +
                "                \"heartbeat\",\n" +
                "                {\n" +
                "                    \"name\": \"snapshot\",\n" +
                "                    \"product_ids\": [\n" +
                "                        \"BTC-USD\"\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }"
        // First
        "{\n" +
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
        //Second
        "{\n" +
                "   \"type\":\"l2update\",\n" +
                "   \"product_id\":\"BTC-USD\",\n" +
                "   \"changes\":[\n" +
                "      [\n" +
                "         \"buy\",\n" +
                "         \"43924.56\",\n" +
                "         \"0.00400000\"\n" +
                "      ]\n" +
                "   ],\n" +
                "   \"time\":\"2022-02-08T11:20:07.353846Z\"\n" +
                "}"
    }

}
