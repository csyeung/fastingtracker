package healthcare.app.fastingtracker.domain.usecase

import healthcare.app.fastingtracker.Platform
import healthcare.app.fastingtracker.domain.model.FastingRecord
import healthcare.app.fastingtracker.domain.repository.LocalDataStoreRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.DurationUnit

class Greeting(private val platform: Platform, private val dataStore: LocalDataStoreRepository) {
    companion object {
        private const val DEFAULT_FASTING_PERIOD: Int = 16
    }

    val updateFastingRecord: Flow<FastingRecord> =
        flow {
            coroutineScope {
                dataStore.startTrackingTime.collect {
                    if (it.isNotEmpty()) {
                        // Format: 2015-12-31T12:30:00Z
                        // To Resume: Instant.parse("2015-12-31T12:30:00Z")
                        val startTime = Instant.parse(it)
                        val endTime = getEndTime(startTime)
                        val recent = Clock.System.now()
                        val remainingTime = endTime - recent
                        val elapsedTime =
                            DEFAULT_FASTING_PERIOD * 3600 - remainingTime.toInt(DurationUnit.SECONDS)

                        emit(
                            FastingRecord(
                                startTime = getFormattedTimeString(startTime),
                                endTime = getFormattedTimeString(endTime),
                                elapsedTime = formatElapsedTime(elapsedTime),
                                remainingTime = formatDuration(remainingTime)
                            )
                        )
                    }
                }
            }
        }

    private fun getFormattedTimeString(recent: Instant): String {
        val localDateTime = recent.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.date} ${formatNumberString(localDateTime.hour)}:${formatNumberString(localDateTime.minute)}:${formatNumberString(localDateTime.second)}"
    }

    private fun formatNumberString(number: Int): String {
        return (if (number < 10) "0" else "") + number
    }

    private fun formatElapsedTime(number: Int): String {
        val hour = number / 3600
        val minute = (number - (3600 * hour)) / 60
        val second = (number - (3600 * hour) - (60 * minute))

        return "${formatNumberString(hour)}:${formatNumberString(minute)}:${formatNumberString(second)}"
    }

    private fun formatDuration(duration: Duration): String {
        return if (duration.isNegative()) {
            "(${formatElapsedTime(duration.toInt(DurationUnit.SECONDS).unaryMinus())})"
        } else {
            formatElapsedTime(duration.toInt(DurationUnit.SECONDS))
        }
    }

    private fun getEndTime(recent: Instant): Instant {
        return recent.plus(DEFAULT_FASTING_PERIOD, DateTimeUnit.HOUR, TimeZone.currentSystemDefault())
    }

    fun greet(): String {
        return platform.currentTime
    }

    fun startFasting() {
        val recent = Clock.System.now()

        // Save current time to user default
        val instantToSave = recent.toString()
        dataStore.saveStartTrackingTime(instantToSave)
    }

    fun stopFasting() {
        dataStore.clearStartTrackingTime()
    }

    suspend fun hasFastingRecord(): Boolean {
        return dataStore.hasStartTrackingTime()
    }
}