package by.ld1995.coinbasetest.services

import by.ld1995.coinbasetest.models.response.ResponseType
import by.ld1995.coinbasetest.models.response.Snapshot
import by.ld1995.coinbasetest.models.response.Update
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.springframework.boot.json.JsonParserFactory
import org.springframework.stereotype.Service


@Service
class MessageHandler(
    private val objectMapper: ObjectMapper
) {

    fun isValidJson(payload: String): Boolean = payload.startsWith("}") && payload.endsWith("}")

    fun isSupportedType(payload: String): Boolean {
        val map: Map<String, String> = getData(payload)
        if (map.containsKey(TYPE)) {
            ResponseType.getByLabel(map.getValue(TYPE))
            return true
        }
        return false
    }

    fun isUpdate(payload: String): Boolean {
        val map: Map<String, String> = getData(payload)
        if (map.containsKey(TYPE)) {
            if (ResponseType.UPDATE == ResponseType.getByLabel(map.getValue(TYPE)))
                return true
        }
        return false
    }

    fun getSnapshot(payload: String): Snapshot = objectMapper.readValue(payload, Snapshot::class.java)

    fun getUpdate(payload: String): Update = objectMapper.readValue(payload, Update::class.java)

    private fun getData(payload: String): Map<String, String> {
        val springParser = JsonParserFactory.getJsonParser()
        return springParser.parseMap(payload) as Map<String, String>
    }

    companion object {
        const val TYPE = "type"
    }
}