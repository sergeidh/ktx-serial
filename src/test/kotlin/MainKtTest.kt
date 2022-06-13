import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

internal class MainKtTest {

    private val json = Json {
//        ignoreUnknownKeys = true
    }

    @Test
    fun test() {

        listOf(
            """        {"metric":"M3"}    """,
            """        {"metric":"M4"}    """,
        )
            .forEach {
                json.decodeFromString<MetricDTO>(it)
            }
    }
}