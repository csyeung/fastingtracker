package healthcare.app.fastingtracker.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HealthRecord(
    @SerialName("recordDate")
    val recordDate: String,

    @SerialName("bodyTemp")
    val bodyTemp: Double,

    @SerialName("bodyWeight")
    val bodyWeight: Double,

    @SerialName("pulseRate")
    val pulseRate: Int,

    @SerialName("sleepTime")
    val sleepTime: Double
)
