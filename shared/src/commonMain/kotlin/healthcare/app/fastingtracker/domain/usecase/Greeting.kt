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
                                startTime = startTime.toString(),
                                endTime = getEndTimeString(endTime),
                                elapsedTime = elapsedTime.toString(),
                                remainingTime = remainingTime.toString()
                            )
                        )
                    }
                }
            }
        }

    private fun getEndTime(recent: Instant): Instant {
        val timeZone = TimeZone.currentSystemDefault()
        return recent.plus(DEFAULT_FASTING_PERIOD, DateTimeUnit.HOUR, timeZone)
    }

    private fun getEndTimeString(endTime: Instant): String {
        val timeZone = TimeZone.currentSystemDefault()
        return endTime.toLocalDateTime(timeZone).toString()
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