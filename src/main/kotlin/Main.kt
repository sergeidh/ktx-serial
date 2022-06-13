import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

enum class Metric { M1, M2, M3, UNKNOWN }


/**
 * Cheap copypasta!
 *
 * https://github.com/Kotlin/kotlinx.serialization/issues/31#issuecomment-488572539
 */
@Serializer(forClass = Metric::class)
@OptIn(InternalSerializationApi::class)
object MetricSerializer : KSerializer<Metric> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("123", SerialKind.ENUM)

    override fun deserialize(decoder: Decoder): Metric {
        val str = decoder.decodeString().uppercase()
        return try {
            Metric.valueOf(str)
        } catch (ex: IllegalArgumentException) {
            Metric.UNKNOWN
        }
    }

    override fun serialize(encoder: Encoder, value: Metric) {
        encoder.encodeString(value.name)
    }
}

@Serializable
data class MetricDTO(@Serializable(MetricSerializer::class) val metric: Metric) {
    init {
        println("metric is $metric")
    }
}

fun main() {

    val data = Json.decodeFromString<MetricDTO>(
        """
        {"metric":"M3"}
    """
    )
    println(data.metric.name)
}
